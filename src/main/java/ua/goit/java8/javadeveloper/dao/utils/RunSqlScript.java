package ua.goit.java8.javadeveloper.dao.utils;

/**
 * Created by t.oleksiv on 08/11/2017.
 */

import com.ibatis.common.jdbc.ScriptRunner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 thanks @author Dhinakaran Pragasam
 */
public class RunSqlScript {

    public static void run(String aSQLScriptFilePath) throws ClassNotFoundException,
            SQLException {

        // Create MySql Connection
        Connection con = ConnectionUtil.getConnection();

        try {
            // Initialize object for ScripRunner
            ScriptRunner sr = new ScriptRunner(con, false, false);

            // Give the input file to Reader
            Reader reader = new BufferedReader(
                    new FileReader(aSQLScriptFilePath));

            // Execute script
            sr.runScript(reader);

        } catch (Exception e) {
            System.err.println("Failed to Execute " + aSQLScriptFilePath
                    + " The error is: " + e.getMessage());
        }
    }
}