package ua.goit.java8.javadeveloper.dao.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by t.oleksiv on 08/11/2017.
 * Використовуєм JDBC.
 * Цей клас використовується лише один раз на етапі перевірки існування бази на сервері
 * в момент запуску програми,
 * а також для створення бази у випадку якщо вона не існує
 */
public class ConnectionUtil {

    // Read hibernate properties stored in hibernate.cfg.xml
    private static String driver = HibernateUtil.getHibernateConnectionDriverClass();
    private static String url = HibernateUtil.getHibernateConnectionServerURL();
    private static String user = HibernateUtil.getHibernateConnectionUsername();
    private static String pass = HibernateUtil.getHibernateConnectionPassword();

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

}