package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class MySqlUtil {


	private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DATABASE = "bdproyectoIntegrador";
    private static final String HOSTNAME = "localhost";
    private static final String PORT = "3306";
    private static final String URL = "jdbc:mysql://" + HOSTNAME + ":" + PORT + "/" + DATABASE + "?useSSL=false";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    
    static {
    	try {
    		Class.forName(DRIVER);
    	}catch(ClassNotFoundException e) {
    		e.printStackTrace();
    	}
    }
    
    public static Connection getConnection() throws SQLException {
    	return DriverManager.getConnection(URL,USERNAME,PASSWORD);
    }
	
}
