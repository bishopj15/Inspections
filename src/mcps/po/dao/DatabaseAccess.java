package mcps.po.dao;

import javax.servlet.ServletContextEvent;

import org.apache.commons.dbcp2.ConnectionFactory;
import org.apache.commons.dbcp2.DriverManagerConnectionFactory;
import org.apache.commons.dbcp2.PoolableConnection;
import org.apache.commons.dbcp2.PoolableConnectionFactory;
import org.apache.commons.dbcp2.PoolingDriver;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;




public class DatabaseAccess {
	
	private static DatabaseAccess instance = null;
	
	private static int maxActive = 20;
	private static int maxIdle = 2;
	private static int maxWait = 10000;

	private static String dbURI = "";
	private static String pwd = "";
	private static String jdbcDriver = "";
	private static String uid = "";
	private static String dbPoolName = "inspectionpool";
	private static String poolingDriver = "jdbc:apache:commons:dbcp:";
	private boolean poolSetup = false;
	
	
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
				jdbcDriver = properties.getProperty("jdbc.driverClassName");
				dbURI = properties.getProperty("jdbc.url");
				uid = properties.getProperty("jdbc.username");
				pwd = properties.getProperty("jdbc.password");
			} catch(Exception e){
				System.out.println("unable to read/locate property file at "+ propertyFilePath);
			}
			if (!instance.initDatabaseAccess()) {
				instance = null;
				System.out.println("Error initializing DatabaseAccess and pool");
			}
		}
		return instance;
	}
	
	private boolean initDatabaseAccess(){
		try{
			Class.forName(jdbcDriver);
			
			//create ConnectionFactory pool will use to create Connections
			ConnectionFactory connectionFactory = new DriverManagerConnectionFactory(dbURI, null);
			
			//create PoolableConnectionFactory to wrap the real connections
			PoolableConnectionFactory poolableConnectionFactory = new PoolableConnectionFactory(connectionFactory, null);
			
			//create actual pool of connections
			ObjectPool<PoolableConnection> connectionPool = new GenericObjectPool<>(poolableConnectionFactory);
			
			//create pooling driver
			Class.forName("org.apache.commons.dbcp.PoolingDriver");
			PoolingDriver driver = (PoolingDriver) DriverManager.getDriver(poolingDriver);
			
			//register pool
			driver.registerPool(dbPoolName, connectionPool);
			
			//validate pool
			this.printDriverStats();
			Connection c = getConnection();
			this.printDriverStats();
			c.close();this.printDriverStats();
			this.poolSetup = true;
			
			
			return true;
		} catch(Exception e) {
			System.out.println("Error setting up database pool");
		}

		return false;
	}
	
	protected static synchronized DatabaseAccess getInstance(Properties properties){
		if (instance == null) {
			instance = new DatabaseAccess();
		}
		
		
		return instance;
	}
	
	public void printDriverStats() throws Exception {
        PoolingDriver driver = (PoolingDriver) DriverManager.getDriver(poolingDriver);
        ObjectPool<? extends Connection> connectionPool = driver.getConnectionPool(dbPoolName);
	
        System.out.println("NumActive: " + connectionPool.getNumActive());
        System.out.println("NumIdle: " + connectionPool.getNumIdle());
    }
	
	/**
	 * provide a connection to the database pool
	 * @return
	 */
	public Connection getConnection() {
		// get a connection from our pool
		try {
			Connection c = DriverManager.getConnection(poolingDriver
					+ dbPoolName);
			return c;
		} catch (Exception e) {
			return null;
		}
	}
	
	public void shutdownDriver() throws Exception {
        PoolingDriver driver = (PoolingDriver) DriverManager.getDriver(poolingDriver);
        driver.closePool(dbPoolName);
    }
}
