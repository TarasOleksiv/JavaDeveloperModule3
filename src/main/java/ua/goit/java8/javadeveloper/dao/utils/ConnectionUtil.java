package ua.goit.java8.javadeveloper.dao.utils;

import ua.goit.java8.javadeveloper.ConsoleApp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by t.oleksiv on 08/11/2017.
 */
public class ConnectionUtil {

    private static String driver = ConsoleApp.settings.getDriver();
    private static String url = ConsoleApp.settings.getUrl();
    private static String user = ConsoleApp.settings.getUser();
    private static String pass = ConsoleApp.settings.getPass();
    private static String database = ConsoleApp.settings.getDatabase();

    static {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, pass);
    }

    public static Connection getConnection(String database) throws SQLException {
        return DriverManager.getConnection(url + database, user, pass);
    }

    public static Connection getConnectionDB() throws SQLException {
        return getConnection(database);
    }
}