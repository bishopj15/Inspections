package mcps.po.dao;

import javax.servlet.ServletContextEvent;

import java.util.Properties;




public class DatabaseAccess {
	
	private static DatabaseAccess instance = null;
	
	private static int maxActive = 10;
	private static int maxIdle = 2;
	private static int maxWait = 10000;

	private static String dbURI = "";
	private static String pwd = "";
	private static String jdbcDriver = "";
	private static String uid = "";
	
	
	private DatabaseAccess() {}

	/**
	 * We do not allow cloning our singleton
	 */
	@Override
	public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}

	/**
	 * this method is called by external callers and returns the instance,
	 * the instance has already been initialized by the method below
	 * @return
	 */
	public static synchronized DatabaseAccess getInstance() {
		return instance;
	}
	
	protected static synchronized DatabaseAccess getInstance(ServletContextEvent servletContext){
		if (instance == null) {
			instance = new DatabaseAccess();
		}
		
		String propertyFilePath = servletContext.getServletContext().getInitParameter("jdbcproperties");
		if(propertyFilePath != null){
			Properties properties = new Properties();
			try{
				properties.load(servletContext.getServletContext().getResourceAsStream(propertyFilePath));
				
			} catch(Exception e){
				
			}
			
		}
		
		
		
		
		return instance;
	}
	
	protected static synchronized DatabaseAccess getInstance(Properties properties){
		if (instance == null) {
			instance = new DatabaseAccess();
		}
		
		
		return instance;
	}
}
