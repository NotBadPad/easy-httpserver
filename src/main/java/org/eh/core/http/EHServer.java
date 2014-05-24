package org.eh.core.http;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Date;
import java.util.Timer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eh.core.annotation.AnnocationHandler;
import org.eh.core.common.Constants;
import org.eh.core.task.SessionCleanTask;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.spi.HttpServerProvider;

/**
 * 主服务类
 * @author guojing
 * @date 2014-3-3
 */
public class EHServer {
	private final Log log = LogFactory.getLog(EHServer.class);

	/**
	 * 初始化信息，并启动server
	 */
	public void startServer() throws IOException {
		log.info("Starting EHServer......");
		
		//设置路径
		Constants.CLASS_PATH = this.getClass().getResource("/").getPath();
		Constants.VIEW_BASE_PATH = "page";
		Constants.STATIC_RESOURCE_PATH = "static";
		
		//启动session过期清理定时器
		Timer timer = new Timer();
		SessionCleanTask sessionCleanTask = new SessionCleanTask();
		log.info("Initializing SessionCleanTask,the session_out_time is " + Constants.SESSION_TIMEOUT * 2
				+ " minute.");
		timer.schedule(sessionCleanTask, new Date(), Constants.SESSION_TIMEOUT * 60 * 2 * 1000);
		
		// 加载注解配置的controller
		AnnocationHandler annocationHandler = new AnnocationHandler();
		try {
			annocationHandler
					.paserControllerAnnocation("org.eh.core.web.controller");
		} catch (Exception e) {
			log.error("加载controller配置出错！", e);
			return;
		}
		
		//设置端口号
		int port = 8899;
		
		// 启动服务器
		HttpServerProvider provider = HttpServerProvider.provider();
		HttpServer httpserver = provider.createHttpServer(new InetSocketAddress(port), 100);
		httpserver.createContext("/", new EHHttpHandler());
		httpserver.setExecutor(null);
		httpserver.start();
		log.info("EHServer is started, listening at 8899.");
	}

	/**
	 * 项目main
	 */
	public static void main(String[] args) throws IOException {
		new EHServer().startServer();
	}
}
