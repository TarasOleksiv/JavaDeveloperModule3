package ua.goit.java8.javadeveloper.dao.jdbc;

import ua.goit.java8.javadeveloper.dao.ProjectDAO;
import ua.goit.java8.javadeveloper.dao.utils.ConnectionUtil;
import ua.goit.java8.javadeveloper.model.Developer;
import ua.goit.java8.javadeveloper.model.Project;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Taras on 11.11.2017.
 */
public class JdbcProjectDAOImpl extends JdbcAbstractDAO implements ProjectDAO {

    @Override
    public Project getById(Long aLong) {

        String sql = "SELECT * FROM projects WHERE id = ?";
        Project project = null;
        try {
            connection = ConnectionUtil.getConnectionDB();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1,aLong);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                Long projectId = resultSet.getLong("id");
                String name = resultSet.getString("name");
                Long customer_id = resultSet.getLong("customer_id");
                Long company_id = resultSet.getLong("company_id");
                BigDecimal costs = resultSet.getBigDecimal("costs");

                project = new Project();
                project.withId(projectId)
                        .withName(name)
                        .withCustomer_id(customer_id)
                        .withCompany_id(company_id)
                        .withCosts(costs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(false);
        }

        return project;
    }

    @Override
    public List<Project> getAll() {

        List<Project> projects = new ArrayList<>();
        String sql = "SELECT * FROM projects ORDER BY name";

        try {
            connection = ConnectionUtil.getConnectionDB();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Long projectId = resultSet.getLong("id");
                String name = resultSet.getString("name");
                Long customer_id = resultSet.getLong("customer_id");
                Long company_id = resultSet.getLong("company_id");
                BigDecimal costs = resultSet.getBigDecimal("costs");

                Project project = new Project();
                project.withId(projectId)
                        .withName(name)
                        .withCustomer_id(customer_id)
                        .withCompany_id(company_id)
                        .withCosts(costs);

                projects.add(project);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(false);
        }

        return projects;
    }

    @Override
    public void create(Project project) {
        String sql = "INSERT INTO projects (name, customer_id, company_id, costs) VALUES (?, ?, ?, ?)";
        String sqlCompanyCheck = "SELECT * FROM companies WHERE id = ?"; // перевірка чи компанія з таким id існує
        String sqlCustomerCheck = "SELECT * FROM customers WHERE id = ?"; // перевірка чи замовник з таким id існує

        try {
            Boolean valid = false;  // для попередньої перевірки чи введені id компанії та замовника існують
            connection = ConnectionUtil.getConnectionDB();
            preparedStatement = connection.prepareStatement(sqlCompanyCheck);
            preparedStatement.setLong(1,project.getCompany_id());
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                try {
                    resultSet.close();
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                preparedStatement = connection.prepareStatement(sqlCustomerCheck);
                preparedStatement.setLong(1,project.getCustomer_id());
                resultSet = preparedStatement.executeQuery();
                if (resultSet.next()){
                    try {
                        resultSet.close();
                        preparedStatement.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    valid = true;
                }
            }

            if (valid){
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1,project.getName());
                preparedStatement.setLong(2,project.getCustomer_id());
                preparedStatement.setLong(3,project.getCompany_id());
                preparedStatement.setBigDecimal(4,project.getCosts());
                resultSetUpdate = preparedStatement.executeUpdate();
                if (resultSetUpdate > 0){
                    System.out.println("Проект створено успішно.");
                } else {
                    System.out.println("Неможливо створити проект.");
                }

            } else {
                System.out.println("Компанії/замовника з таким id не існує.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(true);
        }

    }

    @Override
    public void update(Project project) {
        String sql = "UPDATE projects SET name = ?, customer_id = ?, company_id = ?, costs = ? WHERE id = ?";
        try {
            connection = ConnectionUtil.getConnectionDB();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,project.getName());
            preparedStatement.setLong(2,project.getCustomer_id());
            preparedStatement.setLong(3,project.getCompany_id());
            preparedStatement.setBigDecimal(4,project.getCosts());
            preparedStatement.setLong(5,project.getId());
            resultSetUpdate = preparedStatement.executeUpdate();
            System.out.println("**********************************");
            if (resultSetUpdate > 0){
                System.out.println("Проект змінено успішно.");
            } else {
                System.out.println("Неможливо змінити проект.");
            }
            System.out.println("**********************************");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(true);
        }
    }

    // каскадне видалення проекта зі всіх пов'язаних таблиць
    @Override
    public void delete(Project project) {
        String sqlProjects = "DELETE FROM projects WHERE id = ?";
        String sqlDevelopers = "DELETE FROM developer_projects WHERE project_id = ?";

        try {
            connection = ConnectionUtil.getConnectionDB();

            // транзакція - каскадне видалення проекта зі всіх таблиць
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(sqlDevelopers);
            preparedStatement.setLong(1,project.getId());
            preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement(sqlProjects);
            preparedStatement.setLong(1,project.getId());
            resultSetUpdate = preparedStatement.executeUpdate();
            connection.commit();
            // кінець транзакції

            connection.setAutoCommit(true);
            System.out.println("**********************************");
            if (resultSetUpdate > 0){
                System.out.println("Проект видалено успішно.");
            } else {
                System.out.println("Неможливо видалити проект.");
            }
            System.out.println("**********************************");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(true);
        }
    }

    // вивести всіх девелоперів на проекті
    public Project getDevelopersById(Long aLong){
        String sql = "SELECT projects.id AS project_id, projects.name AS project_name, developers.* FROM projects\n" +
                "LEFT JOIN developer_projects ON projects.id = developer_projects.project_id\n" +
                "LEFT JOIN developers ON developer_projects.developer_id = developers.id\n" +
                "WHERE projects.id = ?";

        Project project = new Project();
        List<Developer> developers = new ArrayList<>();
        try {
            connection = ConnectionUtil.getConnectionDB();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1,aLong);
            resultSet = preparedStatement.executeQuery();
            Long projectId = null;
            String projectName = null;
            Long developerId = null;
            String firstName = null;
            String lastName = null;
            Long company_id = null;
            BigDecimal salary = null;
            while (resultSet.next()) {
                projectId = resultSet.getLong("project_id");
                projectName = resultSet.getString("project_name");
                developerId = resultSet.getLong("id");
                firstName = resultSet.getString("firstname");
                lastName = resultSet.getString("lastname");
                company_id = resultSet.getLong("company_id");
                salary = resultSet.getBigDecimal("salary");
                Developer developer = new Developer();
                developer.withId(developerId)
                        .withFirstName(firstName)
                        .withLastName(lastName)
                        .withCompany_id(company_id)
                        .withSalary(salary);
                developers.add(developer);
            }
            project.withId(projectId)
                    .withName(projectName)
                    .withDevelopers(developers);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(false);
        }

        return project;
    }

    // додати девелопера на проект
    public void addDeveloper(Long project_id, Long developer_id){
        String sql = "INSERT INTO developer_projects (project_id, developer_id) VALUES (?, ?)";
        String sqlCheck = "SELECT * FROM developer_projects WHERE project_id = ? AND developer_id = ?"; // перевірка чи така пара вже існує

        try {
            connection = ConnectionUtil.getConnectionDB();
            preparedStatement = connection.prepareStatement(sqlCheck);
            preparedStatement.setLong(1,project_id);
            preparedStatement.setLong(2,developer_id);
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()){
                try {
                    resultSet.close();
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setLong(1,project_id);
                preparedStatement.setLong(2,developer_id);
                resultSetUpdate = preparedStatement.executeUpdate();
                if (resultSetUpdate > 0){
                    System.out.println("Девелопера на проект додано успішно.");
                } else {
                    System.out.println("Неможливо додати девелопера на проект.");
                }

            } else {
                System.out.println("Такий девелопер на проекті вже існує.");
            }


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(true);
        }
    }

    // вилучити девелопера з проекту
    public void deleteDeveloper(Long project_id, Long developer_id){
        String sql = "DELETE FROM developer_projects WHERE project_id = ? AND developer_id = ?";

        try {
            connection = ConnectionUtil.getConnectionDB();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1,project_id);
            preparedStatement.setLong(2,developer_id);
            resultSetUpdate = preparedStatement.executeUpdate();
            if (resultSetUpdate > 0){
                System.out.println("девелопера з проекту вилучено успішно.");
            } else {
                System.out.println("Неможливо вилучити девелопера з проекту.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(true);
        }
    }
}
