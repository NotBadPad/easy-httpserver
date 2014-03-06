package org.eh.core.http;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eh.core.common.Constants;
import org.eh.core.model.ResultInfo;
import org.eh.core.util.FileUtil;
import org.eh.core.util.IOUtil;
import org.eh.core.util.StringUtil;
import org.eh.core.web.controller.Controller;
import org.eh.core.web.view.ViewHandler;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

/**
 * 处理Http请求
 * @author guojing
 * @date 2014-3-3
 */
@SuppressWarnings("restriction")
public class EHHttpHandler implements HttpHandler {

	public void handle(HttpExchange httpExchange) throws IOException {
		try {
			String path = httpExchange.getRequestURI().getPath();
			System.out.println("Request path:" + path);

			// 根据后缀判断是否是静态资源
			String suffix = path.substring(path.lastIndexOf("."), path.length());
			List<String> suffixs =new ArrayList<>(Arrays.asList(".css",".js",".jpg",".png",".gif"));
			if (suffixs.contains(suffix)) {
				byte[] bytes = FileUtil.readFileByBytes(this.getClass().getResource("/").getPath()
						+ "static" + path);
				responseStaticToClient(httpExchange, 200, bytes);
				return;
			}

			// 调用对应处理程序controller
			ResultInfo resultInfo = invokController(httpExchange);

			// 返回404
			if (resultInfo == null || StringUtil.isEmpty(resultInfo.getView())) {
				responseToClient(httpExchange, 200, "<h1>页面不存在<h1>");
				return;
			}

			String viewPath = resultInfo.getView();
			// 重定向
			if (viewPath.startsWith("redirect:")) {
				String redirectUrl = viewPath.replace("redirect:", "");
				responseToClient(httpExchange, 302, redirectUrl);
				return;
			} else { // 解析对应view并返回
				String content = invokViewHandler(resultInfo);
				if (content == null) {
					content = "";
				}
				responseToClient(httpExchange, 200, content);
				return;
			}

		} catch (Exception e) {
			httpExchange.close();
			e.printStackTrace();
		}
	}

	/**
	 * 响应请求
	 * @param httpExchange 请求-响应的封装
	 * @param code 返回状态码
	 * @param msg 返回信息
	 * @throws IOException
	 */
	private void responseToClient(HttpExchange httpExchange, Integer code, String msg)
			throws IOException {
		switch (code) {
		case 200: { // 成功
			byte[] bytes = msg.getBytes();
			httpExchange.sendResponseHeaders(code, bytes.length);
			OutputStream out = httpExchange.getResponseBody();
			out.write(bytes);
			out.flush();
			httpExchange.close();
		}
			break;
		case 302: { // 跳转
			Headers headers = httpExchange.getResponseHeaders();
			headers.add("Location", msg);
			httpExchange.sendResponseHeaders(code, 0);
			httpExchange.close();
		}
			break;
		case 404: { // 错误
			byte[] bytes = "".getBytes();
			httpExchange.sendResponseHeaders(code, bytes.length);
			OutputStream out = httpExchange.getResponseBody();
			out.write(bytes);
			out.flush();
			httpExchange.close();
		}
			break;
		default:
			break;
		}
	}
	
	/**
	 * 响应请求，返回静态资源
	 * @param httpExchange
	 * @param code
	 * @param bytes
	 * @throws IOException
	 */
	private void responseStaticToClient(HttpExchange httpExchange, Integer code, byte[] bytes)
			throws IOException {
		httpExchange.sendResponseHeaders(code, bytes.length);
		OutputStream out = httpExchange.getResponseBody();
		out.write(bytes);
		out.flush();
		httpExchange.close();
	}

	private ResultInfo invokController(HttpExchange httpExchange) throws ClassNotFoundException,
			InstantiationException, IllegalAccessException {
		String path = httpExchange.getRequestURI().getPath();
		
		String classPath = Constants.UrlClassMap.get(path.substring(0, path.lastIndexOf(".")));
		if (classPath == null || classPath.length() == 0) {
			return null;
		}
		Class controllerClass = Class.forName(classPath);
		Controller controller = (Controller) controllerClass.newInstance();

		return controller.process(analysisParms(httpExchange));
	}

	private String invokViewHandler(ResultInfo resultInfo) {
		ViewHandler viewHandler = new ViewHandler();
		return viewHandler.processView(resultInfo);
	}

	private Map<String, Object> analysisParms(HttpExchange httpExchange) {
		Map<String, Object> map = new HashMap<String, Object>();
		URI requestedUri = httpExchange.getRequestURI();
		String queryGet = requestedUri.getRawQuery();
		String queryPost = IOUtil.getRequestContent(httpExchange.getRequestBody());
		String query = "";
		if (!StringUtil.isEmpty(queryGet)) {
			query = queryGet;
		}
		if (!StringUtil.isEmpty(queryPost)) {
			query = query + "&" + queryPost;
		}

		for (String kv : query.split("&")) {
			String[] temp = kv.split("=");
			map.put(temp[0], URLDecoder.decode(temp[1]));
		}
		return map;
	}
}
