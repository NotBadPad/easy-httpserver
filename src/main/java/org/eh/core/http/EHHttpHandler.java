package org.eh.core.http;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eh.core.annotation.AnnocationHandler;
import org.eh.core.common.Constants;
import org.eh.core.common.ReturnType;
import org.eh.core.model.ResultInfo;
import org.eh.core.util.FileUploadContentAnalysis;
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
public class EHHttpHandler implements HttpHandler {
	private final Log log = LogFactory.getLog(EHHttpHandler.class);

	public void handle(HttpExchange httpExchange) throws IOException {
		try {
			String path = httpExchange.getRequestURI().getPath();
			log.info("Receive a request,Request path:" + path);

			// 设置sessionId
			String sessionId = ApplicationContext.getApplicationContext()
					.getSessionId(httpExchange);
			if (StringUtil.isEmpty(sessionId)) {
				sessionId = StringUtil.creatSession();
				ApplicationContext.getApplicationContext().addSession(sessionId);
			}

			httpExchange.getResponseHeaders().set("Set-Cookie",
					"EH_SESSION=" + sessionId + "; path=/");

			// 根据后缀判断是否是静态资源
			String suffix = path.substring(path.lastIndexOf("."), path.length());
			if (Constants.STATIC_SUFFIXS.contains(suffix)) {
				byte[] bytes = IOUtil.readFileByBytes(this.getClass().getResource("/").getPath()
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
			if (viewPath.startsWith(ReturnType.redirect.name())) {// redirect跳转
				String redirectUrl = viewPath.replace(ReturnType.redirect.name() + ":", "");
				responseToClient(httpExchange, 302, redirectUrl);
				return;
			} else if (viewPath.startsWith(ReturnType.json.name())) { // 返回json数据
				String jsonContent = viewPath.replace(ReturnType.json.name() + ":", "");
				responseToClient(httpExchange, 200, jsonContent);
				return;
			} else if (viewPath.startsWith(ReturnType.velocity.name())) { // 解析对应view并返回
				String content = invokViewHandler(resultInfo);
				if (content == null) {
					content = "";
				}
				responseToClient(httpExchange, 200, content);
				return;
			} else {
				responseToClient(httpExchange, 200, resultInfo.getView());
				return;
			}

		} catch (Exception e) {
			httpExchange.close();
			log.error("响应请求失败：", e);
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

	/**
	 * 调用对应Controller处理业务
	 */
	@SuppressWarnings("rawtypes")
	private ResultInfo invokController(HttpExchange httpExchange) throws ClassNotFoundException,
			InstantiationException, IllegalAccessException, IOException, NoSuchMethodException,
			SecurityException, IllegalArgumentException, InvocationTargetException {
		String path = httpExchange.getRequestURI().getPath();
		
		String classPath = Constants.UrlClassMap.get(path.substring(0, path.lastIndexOf("/") + 1));
		if (classPath == null || classPath.length() == 0) {
			return null;
		}
		Class controllerClass = Class.forName(classPath);
		Controller controller = (Controller) controllerClass.newInstance();

		String methodName = path.substring(path.lastIndexOf("/") + 1, path.lastIndexOf("."));
		//通过反射获取对应方法
		AnnocationHandler annocationHandler = new AnnocationHandler();
		Method method = annocationHandler.getMethod(controllerClass, methodName);
		
		Map<String, Object> map = null; // 参数
		// 判断表单类型，若是multipart/form-data，则是文件上传；否则做普通处理
		Headers headers = httpExchange.getRequestHeaders();
		// 获取ContentType
		String contentType = "";
		if(null != headers.get("Content-type")){
			contentType = headers.get("Content-type").toString().replace("[", "")
					.replace("]", "");
		}
		
		if (contentType.indexOf("multipart/form-data") != -1) {
			// 获取content长度
			int length = Integer.parseInt(headers.get("Content-length").toString().replace("[", "")
					.replace("]", ""));
			map = FileUploadContentAnalysis.parse(httpExchange.getRequestBody(), contentType,
					length);
		} else {
			map = analysisParms(httpExchange);
		}

		// 设置session
		HttpSession httpSession = ApplicationContext.getApplicationContext().getSession(
				httpExchange);
		map.put("session", httpSession);

		return (ResultInfo) method.invoke(controller, new Object[] { map });
	}

	/**
	 * 调用ViewHandler渲染视图
	 */
	private String invokViewHandler(ResultInfo resultInfo) throws IOException {
		ViewHandler viewHandler = new ViewHandler();
		// return viewHandler.processView(resultInfo);
		return viewHandler.processVelocityView(resultInfo);
	}

	/**
	 * 解析参数
	 */
	private Map<String, Object> analysisParms(HttpExchange httpExchange)
			throws UnsupportedEncodingException {
		Map<String, Object> map = new HashMap<String, Object>();

		URI requestedUri = httpExchange.getRequestURI();
		String queryGet = requestedUri.getRawQuery();
		String queryPost = IOUtil.getRequestContent(httpExchange.getRequestBody());
		String query = "";
		if (!StringUtil.isEmpty(queryGet)) {
			query = queryGet;
		}
		if (!StringUtil.isEmpty(queryPost)) {
			query = StringUtil.isEmpty(query) ? queryPost : (query + "&" + queryPost);
		}
		if (StringUtil.isEmpty(query)) {
			return map;
		}

		for (String kv : query.split("&")) {
			String[] temp = kv.split("=");
			map.put(temp[0], URLDecoder.decode(temp[1], "utf-8"));
		}
		return map;
	}
}
