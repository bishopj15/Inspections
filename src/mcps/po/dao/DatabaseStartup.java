package mcps.po.dao;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;



public class DatabaseStartup implements ServletContextListener{
	
	/**
	 * When the server context is initialized, this method gets called if this class
	 * was registered as a listener in web.xml
	 */
	@Override
	public void contextInitialized(ServletContextEvent arg0){
		DatabaseAccess instance = DatabaseAccess.getInstance(arg0);
		if(instance == null){
			
		}
		return;
	}
	
	/**
	 * shuts down the database when associated with a listener in web.xml
	 */
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		
	}
}
