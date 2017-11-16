package ua.goit.java8.javadeveloper.dao.jdbc;

import ua.goit.java8.javadeveloper.dao.SkillDAO;
import ua.goit.java8.javadeveloper.dao.utils.ConnectionUtil;
import ua.goit.java8.javadeveloper.model.Skill;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Taras on 11.11.2017.
 */
public class JdbcSkillDAOImpl extends JdbcAbstractDAO implements SkillDAO {

    @Override
    public Skill getById(Long aLong) {
        String sql = "SELECT * FROM skills WHERE id = ?";
        Skill skill = null;
        try {
            connection = ConnectionUtil.getConnectionDB();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1,aLong);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                Long skillId = resultSet.getLong("id");
                String name = resultSet.getString("name");

                skill = new Skill();
                skill.withId(skillId)
                        .withName(name);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(false);
        }

        return skill;
    }

    @Override
    public List<Skill> getAll() {

        List<Skill> skills = new ArrayList<>();
        String sql = "SELECT * FROM skills ORDER BY name";

        try {
            connection = ConnectionUtil.getConnectionDB();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Long skillId = resultSet.getLong("id");
                String name = resultSet.getString("name");

                Skill skill = new Skill();
                skill.withId(skillId)
                        .withName(name);

                skills.add(skill);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(false);
        }

        return skills;
    }

    @Override
    public void create(Skill skill) {
        String sql = "INSERT INTO skills (name) VALUES (?)";
        String sqlSkillCheck = "SELECT * FROM skills WHERE name = ?"; // перевірка чи скіл з таким name існує

        try {
            connection = ConnectionUtil.getConnectionDB();
            preparedStatement = connection.prepareStatement(sqlSkillCheck);
            preparedStatement.setString(1,skill.getName());
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()){
                try {
                    resultSet.close();
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1,skill.getName());
                resultSetUpdate = preparedStatement.executeUpdate();
                if (resultSetUpdate > 0){
                    System.out.println("Скіл створено успішно.");
                } else {
                    System.out.println("Неможливо створити скіл.");
                }

            } else {
                System.out.println("Скіл з такою назвою вже існує.");
            }


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(true);
        }

    }

    @Override
    public void update(Skill skill) {
        String sql = "UPDATE skills SET name = ? WHERE id = ?";
        try {
            connection = ConnectionUtil.getConnectionDB();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,skill.getName());
            preparedStatement.setLong(2,skill.getId());
            resultSetUpdate = preparedStatement.executeUpdate();
            System.out.println("**********************************");
            if (resultSetUpdate > 0){
                System.out.println("Скіл змінено успішно.");
            } else {
                System.out.println("Неможливо змінити скіл.");
            }
            System.out.println("**********************************");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(true);
        }
    }

    // каскадне видалення скіла зі всіх пов'язаних таблиць
    @Override
    public void delete(Skill skill) {
        String sqlSkills = "DELETE FROM skills WHERE id = ?";
        String sqlDevelopers = "DELETE FROM developer_skills WHERE skill_id = ?";

        try {
            connection = ConnectionUtil.getConnectionDB();

            // транзакція - каскадне видалення скіла зі всіх таблиць
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(sqlDevelopers);
            preparedStatement.setLong(1,skill.getId());
            preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement(sqlSkills);
            preparedStatement.setLong(1,skill.getId());
            resultSetUpdate = preparedStatement.executeUpdate();
            connection.commit();
            // кінець транзакції

            connection.setAutoCommit(true);
            System.out.println("**********************************");
            if (resultSetUpdate > 0){
                System.out.println("Скіла видалено успішно.");
            } else {
                System.out.println("Неможливо видалити скіл.");
            }
            System.out.println("**********************************");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(true);
        }
    }
}
