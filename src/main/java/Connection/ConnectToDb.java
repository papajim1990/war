/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Connection;

/**
 *
 * @author user1
 */
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
 
public class ConnectToDb {
 
    private static Connection conn;
 
    public static Connection getConnection() {
        if( conn != null )
            return conn;
 System.out.println(Thread.currentThread().getContextClassLoader());
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream( "/db.properties" );
        Properties properties = new Properties();
        try {
            properties.load( inputStream );
            String driver = properties.getProperty( "driver" );
            String url = properties.getProperty( "url" );
            String user = properties.getProperty( "user" );
            String password = properties.getProperty( "password" );
            Class.forName( driver );
            
            conn = DriverManager.getConnection( url, user, password );
        } catch (IOException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
 
        return conn;
    }
 
    public static void closeConnection( Connection toBeClosed ) {
        if( toBeClosed == null )
            return;
        try {
            toBeClosed.close();
        } catch (SQLException e) {
        }
    }
}
