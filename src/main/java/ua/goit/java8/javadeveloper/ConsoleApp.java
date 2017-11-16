package ua.goit.java8.javadeveloper;

import ua.goit.java8.javadeveloper.dao.utils.ConnectionUtil;
import ua.goit.java8.javadeveloper.dao.utils.RunSqlScript;
import ua.goit.java8.javadeveloper.view.MainMenu;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by t.oleksiv on 08/11/2017.
 */
public class ConsoleApp {

    public static final Settings settings = new Settings();

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        if (!checkDB(settings.getDatabase())) initializeDB(settings.getDatabase());
        new MainMenu();
    }

    private static boolean checkDB(String database) throws SQLException {
        boolean existsDB = false;
        String sql = "SHOW databases;";
        Connection connectServer = ConnectionUtil.getConnection();
        Statement statement = connectServer.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet.next()) {
            if(resultSet.getString("Database").equals(database)){
                existsDB = true;
                break;
            }
        }

        try {
            resultSet.close();
            statement.close();
            connectServer.close();
        } finally {
            if(statement != null){
                statement.close();
            }
            if(connectServer != null){
                connectServer.close();
            }
        }

        System.out.println("База " + database + (existsDB?" - OK":" - відсутня"));
        return existsDB;
    }

    private static void initializeDB(String database) throws SQLException, ClassNotFoundException {
        System.out.println("**********************");
        System.out.println("Створюємо робочу базу " + database + " ...");
        System.out.println("**********************");
        RunSqlScript.run(settings.getInitDBSqlPath());
        System.out.println("**********************");
        System.out.println("Заливаємо дані у базу " + database + " ...");
        System.out.println("**********************");
        RunSqlScript.run(settings.getPopulateDBSqlPath());
        System.out.println("**********************");
        System.out.println("База " + database + " успішно створена.");
        System.out.println("**********************");
    }

}
