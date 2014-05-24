package org.eh.core.task;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eh.core.common.Constants;
import org.eh.core.http.ApplicationContext;
import org.eh.core.http.HttpSession;

/**
 * 定时清理过期session
 * @author guojing
 * @date 2014-3-17
 */
public class SessionCleanTask extends TimerTask {
	private final Log log = LogFactory.getLog(SessionCleanTask.class);

	@Override
	public void run() {
		log.info("清理session......");
		ConcurrentMap<String, HttpSession> sessionMap = ApplicationContext.getApplicationContext()
				.getAllSession();
		
		Iterator<Map.Entry<String, HttpSession>> it = sessionMap.entrySet().iterator();
		while (it.hasNext()) {
			ConcurrentMap.Entry<String, HttpSession> entry= (Entry<String, HttpSession>) it.next();
			HttpSession httpSession= entry.getValue();
			
			Date nowDate = new Date();
			int diff = (int) ((nowDate.getTime() - httpSession.getLastVisitTime().getTime())/1000/60);
			
			if (diff > Constants.SESSION_TIMEOUT) {
				it.remove();
			}
		}

		log.info("清理session结束");
	}
}
