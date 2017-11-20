package ua.goit.java8.javadeveloper;

import java.io.*;
import java.util.Properties;

/**
 * Created by Taras on 12.11.2017.
 */

// клас налаштувань
public class Settings {

    private static final String CONFIG_FILE_PATH = "src/main/resources/config.properties";

    private String initDBSqlPath;   // шлях до файла зі скриптом ініціалізації бази
    private String populateDBSqlPath;   // шлях до файла зі скриптом заливки даних

    public Settings(){
        readSettings();
    }

    public String getInitDBSqlPath() {
        return initDBSqlPath;
    }

    public void setInitDBSqlPath(String initDBSqlPath) {
        this.initDBSqlPath = initDBSqlPath;
    }

    public String getPopulateDBSqlPath() {
        return populateDBSqlPath;
    }

    public void setPopulateDBSqlPath(String populateDBSqlPath) {
        this.populateDBSqlPath = populateDBSqlPath;
    }

    public void saveSettings(){
        Properties prop = new Properties();
        OutputStream output = null;

        try {

            output = new FileOutputStream(CONFIG_FILE_PATH);

            // set the properties value
            prop.setProperty("initDBSqlPath", "src/main/resources/initDB.sql");
            prop.setProperty("populateDBSqlPath", "src/main/resources/populateDB.sql");

            String comments = "    String initDBSqlPath: path to the file for the database initialization\n" +
                    "    String populateDBSqlPath: path to the file for the data population";

            // save properties to project root folder
            prop.store(output, comments);

        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public void readSettings(){
        Properties prop = new Properties();
        InputStream input = null;

        try {

            input = new FileInputStream(CONFIG_FILE_PATH);

            // load a properties file
            prop.load(input);

            // get the property value and print it out
            initDBSqlPath = (prop.getProperty("initDBSqlPath"));
            populateDBSqlPath = (prop.getProperty("populateDBSqlPath"));

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //public static void main(String[] args) {
        //saveSettings();
        //readSettings();
    //}
}
