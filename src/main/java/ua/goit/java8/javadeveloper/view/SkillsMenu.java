package ua.goit.java8.javadeveloper.view;

import ua.goit.java8.javadeveloper.dao.SkillDAO;
import ua.goit.java8.javadeveloper.dao.hibernate.HibernateSkillDAOImpl;
import ua.goit.java8.javadeveloper.model.Skill;

import java.util.List;
import java.util.Scanner;

/**
 * Created by Taras on 11.11.2017.
 */
public class SkillsMenu {

    private static SkillDAO skillDAO = new HibernateSkillDAOImpl();
    private Scanner sc = new Scanner(System.in);

    public SkillsMenu(){
        show();
    }

    private void show(){
        System.out.println();
        menu();

        System.out.print("Введіть символ: ");
        String n = sc.nextLine().trim();
        switch (n) {
            case "1":
                getAll();
                break;
            case "2":
                getById();
                break;
            case "3":
                create();
                break;
            case "4":
                update();
                break;
            case "5":
                delete();
                break;
            case "6":
                getDevelopersBySkillId();
                break;
            default:
                System.out.println("Повернення у Головне меню");
                return;
        }
        show();
    }

    private void menu() {
        System.out.println("Меню Skills");
        System.out.println("Які дії виконуєм? (" +
                "1 - Вивести всі скіли, " +
                "2 - Вивести скіл по id, " +
                "3 - Створити скіл, " +
                "4 - Оновити скіл, " +
                "5 - Вилучити скіл, " + "\n" +
                "6 - Вивести всіх девелоперів з даним скілом, " +
                "інший символ - Повернутись у Головне меню)");
    }

    private void getAll() {
        List<Skill> skills = skillDAO.getAll();

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

    private void getById() {
        System.out.print("Введіть id скіла: ");
        Long id = sc.nextLong();
        sc.nextLine();
        Skill skill = skillDAO.getById(id);

        System.out.println("********** Skill ************");
        if (skill != null){
            System.out.println(skill);
        } else {
            System.out.println("Скіл з id = " + id + " відсутній.");
        }
        System.out.println("**********************************");
    }

    private void create() {
        System.out.println("Введіть назву скіла: ");
        String name = sc.nextLine().trim();

        Skill skill = new Skill();
        skill.withName(name);
        skillDAO.create(skill);
    }

    private void update() {
        System.out.print("Введіть id скіла: ");
        Long id = sc.nextLong();
        sc.nextLine();
        Skill skill = skillDAO.getById(id);  //перевірка чи скіл з таким id існує

        if (skill != null){
            System.out.println("Введіть назву скіла: ");
            String name = sc.nextLine().trim();
            skill.withId(id)
                    .withName(name);
            skillDAO.update(skill);
        } else {
            System.out.println("Скіл з id = " + id + " відсутній.");
        }
    }

    private void delete() {
        System.out.print("Введіть id скіла: ");
        Long id = sc.nextLong();
        sc.nextLine();
        Skill skill = skillDAO.getById(id);  //перевірка чи скіл з таким id існує

        if (skill != null){
            skillDAO.delete(skill);
        } else {
            System.out.println("Скіл з id = " + id + " відсутній.");
        }
    }

    // вивести всіх девелоперів з даним скілом
    private void getDevelopersBySkillId() {
        System.out.print("Введіть id скіла: ");
        Long id = sc.nextLong();
        sc.nextLine();
        Skill skill = skillDAO.getById(id);

        System.out.println("********** Developers with Skill ************");
        if (skill != null){
            System.out.println(skill.showSkillDevelopers());
        } else {
            System.out.println("Скіл з id = " + id + " відсутній.");
        }
        System.out.println("**********************************");
    }
}
