package org.eh.core.http;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.eh.core.common.Constants;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.spi.HttpServerProvider;

/**
 * 主服务类
 * @author guojing
 * @date 2014-3-3
 */
public class EHServer {
	private final static int port = 9999;

	@SuppressWarnings("restriction")
	public void startServer() throws IOException {
		System.out.println("starting EHServer......");
		HttpServerProvider provider = HttpServerProvider.provider();
		HttpServer httpserver = provider.createHttpServer(new InetSocketAddress(port), 100);
		httpserver.createContext("/", new EHHttpHandler());
		httpserver.setExecutor(null);
		httpserver.start();
		System.out.println("EHServer has started");
	}

	public static void main(String[] args) throws IOException {
		Constants.UrlClassMap.put("/test/list", "org.eh.web.controller.TestController");
		new EHServer().startServer();
	}
}
