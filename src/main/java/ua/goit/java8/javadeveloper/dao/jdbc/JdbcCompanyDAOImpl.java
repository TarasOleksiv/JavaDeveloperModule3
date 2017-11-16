package ua.goit.java8.javadeveloper.dao.jdbc;

import ua.goit.java8.javadeveloper.dao.CompanyDAO;
import ua.goit.java8.javadeveloper.dao.utils.ConnectionUtil;
import ua.goit.java8.javadeveloper.model.Company;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Taras on 11.11.2017.
 */
public class JdbcCompanyDAOImpl extends JdbcAbstractDAO implements CompanyDAO {

    @Override
    public Company getById(Long aLong) {
        String sql = "SELECT * FROM companies WHERE id = ?";
        Company company = null;
        try {
            connection = ConnectionUtil.getConnectionDB();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1,aLong);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                Long companyId = resultSet.getLong("id");
                String name = resultSet.getString("name");

                company = new Company();
                company.withId(companyId)
                        .withName(name);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(false);
        }

        return company;
    }

    @Override
    public List<Company> getAll() {

        List<Company> companies = new ArrayList<>();
        String sql = "SELECT * FROM companies ORDER BY name";

        try {
            connection = ConnectionUtil.getConnectionDB();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Long comanyId = resultSet.getLong("id");
                String name = resultSet.getString("name");

                Company company = new Company();
                company.withId(comanyId)
                        .withName(name);

                companies.add(company);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(false);
        }

        return companies;

    }

    @Override
    public void create(Company company) {
        String sql = "INSERT INTO companies (name) VALUES (?)";
        String sqlCompanyCheck = "SELECT * FROM companies WHERE name = ?"; // перевірка чи компанія з таким name існує

        try {
            connection = ConnectionUtil.getConnectionDB();
            preparedStatement = connection.prepareStatement(sqlCompanyCheck);
            preparedStatement.setString(1,company.getName());
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()){
                try {
                    resultSet.close();
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1,company.getName());
                resultSetUpdate = preparedStatement.executeUpdate();
                if (resultSetUpdate > 0){
                    System.out.println("Компанію створено успішно.");
                } else {
                    System.out.println("Неможливо створити компанію.");
                }

            } else {
                System.out.println("Компанія з такою назвою вже існує.");
            }


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(true);
        }

    }

    @Override
    public void update(Company company) {
        String sql = "UPDATE companies SET name = ? WHERE id = ?";
        try {
            connection = ConnectionUtil.getConnectionDB();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,company.getName());
            preparedStatement.setLong(2,company.getId());
            resultSetUpdate = preparedStatement.executeUpdate();
            System.out.println("**********************************");
            if (resultSetUpdate > 0){
                System.out.println("Компанію змінено успішно.");
            } else {
                System.out.println("Неможливо змінити компанію.");
            }
            System.out.println("**********************************");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(true);
        }
    }

    @Override
    // каскадне видалення компанії зі всіх пов'язаних таблиць
    public void delete(Company company) {
        String sqlCompanies = "DELETE FROM companies WHERE id = ?";
        String sqlDevelopers = "DELETE FROM developers WHERE company_id = ?";
        String sqlProjects = "DELETE FROM projects WHERE company_id = ?";

        try {
            connection = ConnectionUtil.getConnectionDB();

            // транзакція - каскадне видалення компанії зі всіх таблиць
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(sqlProjects);
            preparedStatement.setLong(1,company.getId());
            preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement(sqlDevelopers);
            preparedStatement.setLong(1,company.getId());
            preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement(sqlCompanies);
            preparedStatement.setLong(1,company.getId());
            resultSetUpdate = preparedStatement.executeUpdate();
            connection.commit();
            // кінець транзакції

            connection.setAutoCommit(true);
            System.out.println("**********************************");
            if (resultSetUpdate > 0){
                System.out.println("Компанію видалено успішно.");
            } else {
                System.out.println("Неможливо видалити компанію.");
            }
            System.out.println("**********************************");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(true);
        }
    }
}
