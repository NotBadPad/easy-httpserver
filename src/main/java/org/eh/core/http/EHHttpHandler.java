package org.eh.core.http;

import java.io.IOException;
import java.io.OutputStream;

import org.eh.core.common.Constants;

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
			// 调用对应处理程序controller
			invokController(httpExchange);
			// 调用ViewHandler处理页面

			responseToClient(httpExchange, 200, "<h1>aaaaaaa</h1>");
		} catch (Exception e) {
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
		}
			break;
		case 302: { // 跳转
			Headers headers = httpExchange.getResponseHeaders();
			headers.add("Location", msg);
			httpExchange.sendResponseHeaders(code, 0);
		}
			break;
		default:
			break;
		}
	}

	private void invokController(HttpExchange httpExchange) {
		String path = httpExchange.getRequestURI().getPath();
		// 将url路径转为包路径
		String packagePrefix = Constants.PACKAGE_PREFIX
				+ path.substring(0, path.lastIndexOf("/")).replace("/", ".");
		System.out.println(packagePrefix);
		// Class controllerClass = Class.forName("")
	}
}
