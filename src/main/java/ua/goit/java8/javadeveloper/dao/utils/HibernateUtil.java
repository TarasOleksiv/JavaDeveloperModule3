package ua.goit.java8.javadeveloper.dao.utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Created by t.oleksiv on 16/11/2017.
 */
public class HibernateUtil {

    private static final SessionFactory sessionFactory;
    private static final String hibernateConnectionDriverClass;
    private static final String hibernateConnectionServerURL;
    private static final String hibernateConnectionDBname;
    private static final String hibernateConnectionUsername;
    private static final String hibernateConnectionPassword;

    static {
        try {
            // Read connection properties to pass them to jdbc connection utility
            Configuration configuration = new Configuration().configure();
            hibernateConnectionDriverClass = configuration.getProperties().getProperty("hibernate.connection.driver_class");
            String hibernateConnectionURL = configuration.getProperties().getProperty("hibernate.connection.url");
            hibernateConnectionServerURL = getServerURL(hibernateConnectionURL);
            hibernateConnectionDBname = getDBname(hibernateConnectionURL);
            hibernateConnectionUsername = configuration.getProperties().getProperty("hibernate.connection.username");
            hibernateConnectionPassword = configuration.getProperties().getProperty("hibernate.connection.password");

            // Create the SessionFactory from hibernate.cfg.xml
            sessionFactory = configuration.buildSessionFactory();
            //sessionFactory = new Configuration().configure().buildSessionFactory();

        } catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    // get server url as a substring of the whole connection url (server + database)
    private static String getServerURL(String stringIn){
        String delims = "[/]";
        String[] line;
        line = stringIn.split(delims);
        String result = "";
        for(int i = 0; i < line.length-1; i++){
            result += line[i] + "/";
        }
        return result;
    }

    // get DB name as a substring of the whole connection url (server + database)
    private static String getDBname(String stringIn){
        String delims = "[/]";
        String[] line;
        line = stringIn.split(delims);
        return line[line.length-1];
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static String getHibernateConnectionServerURL(){
        return hibernateConnectionServerURL;
    }

    public static String getHibernateConnectionDriverClass(){
        return hibernateConnectionDriverClass;
    }

    public static String getHibernateConnectionUsername(){
        return hibernateConnectionUsername;
    }

    public static String getHibernateConnectionPassword(){
        return hibernateConnectionPassword;
    }

    public static String getHibernateConnectionDBname(){
        return hibernateConnectionDBname;
    }

}