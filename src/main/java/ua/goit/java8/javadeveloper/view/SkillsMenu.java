package ua.goit.java8.javadeveloper.view;

import ua.goit.java8.javadeveloper.dao.jdbc.JdbcSkillDAOImpl;
import ua.goit.java8.javadeveloper.model.Skill;

import java.util.List;

/**
 * Created by Taras on 11.11.2017.
 */
public class SkillsMenu extends AbstractMenu {

    private static JdbcSkillDAOImpl jdbcSkillDAO = new JdbcSkillDAOImpl();

    @Override
    void menu() {
        System.out.println("Меню Skills");
        System.out.println("Які дії виконуєм? (" +
                "1 - Вивести всі скіли, " +
                "2 - Вивести скіл по id, " +
                "3 - Створити скіл, " +
                "4 - Оновити скіл, " +
                "5 - Вилучити скіл, " +
                "інший символ - Повернутись у Головне меню)");
    }

    @Override
    void getAll() {
        List<Skill> skills = jdbcSkillDAO.getAll();

        System.out.println("********** Skills ************");
        if (skills != null){
            for (Skill skill: skills){
                System.out.println(skill);
            }
        } else {
            System.out.println("Скіли відсутні.");
        }
        System.out.println("**********************************");
    }

    @Override
    void getById() {
        System.out.print("Введіть id скіла: ");
        Long id = sc.nextLong();
        sc.nextLine();
        Skill skill = jdbcSkillDAO.getById(id);

        System.out.println("********** Skill ************");
        if (skill != null){
            System.out.println(skill);
        } else {
            System.out.println("Скіл з id = " + id + " відсутній.");
        }
        System.out.println("**********************************");
    }

    @Override
    void create() {
        System.out.println("Введіть назву скіла: ");
        String name = sc.nextLine().trim();

        Skill skill = new Skill();
        skill.withName(name);
        jdbcSkillDAO.create(skill);
    }

    @Override
    void update() {
        System.out.print("Введіть id скіла: ");
        Long id = sc.nextLong();
        sc.nextLine();
        Skill skill = jdbcSkillDAO.getById(id);  //перевірка чи скіл з таким id існує

        if (skill != null){
            System.out.println("Введіть назву скіла: ");
            String name = sc.nextLine().trim();
            skill.withId(id)
                    .withName(name);
            jdbcSkillDAO.update(skill);
        } else {
            System.out.println("Скіл з id = " + id + " відсутній.");
        }
    }

    @Override
    void delete() {
        System.out.print("Введіть id скіла: ");
        Long id = sc.nextLong();
        sc.nextLine();
        Skill skill = jdbcSkillDAO.getById(id);  //перевірка чи скіл з таким id існує

        if (skill != null){
            jdbcSkillDAO.delete(skill);
        } else {
            System.out.println("Скіл з id = " + id + " відсутній.");
        }
    }
}
