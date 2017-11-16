package ua.goit.java8.javadeveloper.dao.jdbc;

import ua.goit.java8.javadeveloper.dao.CustomerDAO;
import ua.goit.java8.javadeveloper.dao.utils.ConnectionUtil;
import ua.goit.java8.javadeveloper.model.Customer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Taras on 11.11.2017.
 */
public class JdbcCustomerDAOImpl extends JdbcAbstractDAO implements CustomerDAO {

    @Override
    public Customer getById(Long aLong) {
        String sql = "SELECT * FROM customers WHERE id = ?";
        Customer customer = null;
        try {
            connection = ConnectionUtil.getConnectionDB();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1,aLong);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                Long customerId = resultSet.getLong("id");
                String name = resultSet.getString("name");

                customer = new Customer();
                customer.withId(customerId)
                        .withName(name);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(false);
        }

        return customer;
    }

    @Override
    public List<Customer> getAll() {

        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM customers ORDER BY name";

        try {
            connection = ConnectionUtil.getConnectionDB();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Long customerId = resultSet.getLong("id");
                String name = resultSet.getString("name");

                Customer customer = new Customer();
                customer.withId(customerId)
                        .withName(name);

                customers.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(false);
        }

        return customers;
    }

    @Override
    public void create(Customer customer) {
        String sql = "INSERT INTO customers (name) VALUES (?)";
        String sqlCustomerCheck = "SELECT * FROM customers WHERE name = ?"; // перевірка чи замовник з таким name існує

        try {
            connection = ConnectionUtil.getConnectionDB();
            preparedStatement = connection.prepareStatement(sqlCustomerCheck);
            preparedStatement.setString(1,customer.getName());
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()){
                try {
                    resultSet.close();
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1,customer.getName());
                resultSetUpdate = preparedStatement.executeUpdate();
                if (resultSetUpdate > 0){
                    System.out.println("Замовника створено успішно.");
                } else {
                    System.out.println("Неможливо створити замовника.");
                }

            } else {
                System.out.println("Замовник з такою назвою вже існує.");
            }


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(true);
        }

    }

    @Override
    public void update(Customer customer) {
        String sql = "UPDATE customers SET name = ? WHERE id = ?";
        try {
            connection = ConnectionUtil.getConnectionDB();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,customer.getName());
            preparedStatement.setLong(2,customer.getId());
            resultSetUpdate = preparedStatement.executeUpdate();
            System.out.println("**********************************");
            if (resultSetUpdate > 0){
                System.out.println("Замовника змінено успішно.");
            } else {
                System.out.println("Неможливо змінити замовника.");
            }
            System.out.println("**********************************");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(true);
        }
    }

    // каскадне видалення замовника зі всіх пов'язаних таблиць
    @Override
    public void delete(Customer customer) {
        String sqlCustomers = "DELETE FROM customers WHERE id = ?";
        String sqlProjects = "DELETE FROM projects WHERE customer_id = ?";

        try {
            connection = ConnectionUtil.getConnectionDB();

            // транзакція - каскадне видалення замовника зі всіх таблиць
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(sqlProjects);
            preparedStatement.setLong(1,customer.getId());
            preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement(sqlCustomers);
            preparedStatement.setLong(1,customer.getId());
            resultSetUpdate = preparedStatement.executeUpdate();
            connection.commit();
            // кінець транзакції

            connection.setAutoCommit(true);
            System.out.println("**********************************");
            if (resultSetUpdate > 0){
                System.out.println("Замовника видалено успішно.");
            } else {
                System.out.println("Неможливо видалити замовника.");
            }
            System.out.println("**********************************");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(true);
        }
    }
}
