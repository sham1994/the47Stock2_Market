package org.fortyseven.smsg.stock_market.contextlisteners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.fortyseven.smsg.stock_market.core.GameCore;

@WebListener
public class ApplicationContextListener implements ServletContextListener{

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		System.out.println("System Exitted.");	
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		GameCore.initialize();
	}
	
}
