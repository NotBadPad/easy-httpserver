package org.eh.core.http;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.eh.core.annotation.AnnocationHandler;
import org.eh.core.common.Constants;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.spi.HttpServerProvider;

/**
 * 主服务类
 * @author guojing
 * @date 2014-3-3
 */
public class EHServer {

	@SuppressWarnings("restriction")
	public void startServer() throws IOException {
		System.out.println("Starting EHServer......");
		System.out.println("Loading configuration......");

		// 加载配置文件
		String propPath = this.getClass().getResource("/").getPath() + Constants.PROPERTIES_NAME;
		Constants.loadFromProp(propPath);

		// 加载注解配置的controller
		if (Constants.OTHER_CONFIG_INFO.get(Constants.PROPERTIES_CONTROLLER_PACKAGE) != null) {
			AnnocationHandler annocationHandler = new AnnocationHandler();
			try {
				annocationHandler.paserControllerAnnocation(Constants.OTHER_CONFIG_INFO.get(
						Constants.PROPERTIES_CONTROLLER_PACKAGE).toString());
			} catch (Exception e) {
				System.err.println("加载controller配置出错！");
				e.printStackTrace();
				return;
			}
		}

		for (String key : Constants.UrlClassMap.keySet()) {
			System.out.println("Add url-class:" + key + "  " + Constants.UrlClassMap.get(key));
		}

		int port = 8899;
		//设置端口号
		String portValue = Constants.OTHER_CONFIG_INFO.get(Constants.PROPERTIES_HPPTSERVER_PORT);
		if (portValue != null) {
			try {
				port = Integer.parseInt(portValue);
			} catch (Exception e) {
				System.err.println("端口错误！");
				return;
			}
			
		}
		
		// 启动服务器
		HttpServerProvider provider = HttpServerProvider.provider();
		HttpServer httpserver = provider.createHttpServer(new InetSocketAddress(port), 100);
		httpserver.createContext("/", new EHHttpHandler());
		httpserver.setExecutor(null);
		httpserver.start();
		System.out.println("EHServer has started");
	}

	public static void main(String[] args) throws IOException {
		new EHServer().startServer();
	}
}
