package ua.goit.java8.javadeveloper.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Taras on 11.11.2017.
 */
abstract class JdbcAbstractDAO {
    protected Connection connection = null;
    protected PreparedStatement preparedStatement = null;
    protected ResultSet resultSet = null;
    protected int resultSetUpdate = 0;

    protected void closeAll(boolean isUpdate){
        try {
            if (!isUpdate) { resultSet.close();}
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
