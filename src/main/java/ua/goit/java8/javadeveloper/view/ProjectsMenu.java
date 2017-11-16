package ua.goit.java8.javadeveloper.view;

import ua.goit.java8.javadeveloper.dao.jdbc.JdbcDeveloperDAOImpl;
import ua.goit.java8.javadeveloper.dao.jdbc.JdbcProjectDAOImpl;
import ua.goit.java8.javadeveloper.model.Developer;
import ua.goit.java8.javadeveloper.model.Project;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Taras on 11.11.2017.
 */
public class ProjectsMenu {

    private static JdbcProjectDAOImpl jdbcProjectDAO = new JdbcProjectDAOImpl();
    private static JdbcDeveloperDAOImpl jdbcDeveloperDAO = new JdbcDeveloperDAOImpl();
    private Scanner sc = new Scanner(System.in);

    public ProjectsMenu(){
        show();
    }

    void show() {
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
                getDevelopersById();
                break;
            case "7":
                addProjectDeveloper();
                break;
            case "8":
                deleteProjectDeveloper();
                break;
            default:
                System.out.println("Повернення у Головне меню");
                return;
        }
        show();
    }

    //@Override
    void menu() {
        System.out.println("Меню Projects");
        System.out.println("Які дії виконуєм? (" +
                "1 - Вивести всі проекти, " +
                "2 - Вивести проект по id, " +
                "3 - Створити проект, " +
                "4 - Оновити проект, " +
                "5 - Вилучити проект, " +  "\n" +
                "6 - Вивести девелоперів на проекті, " +
                "7 - Додати девелопера на проект, " +
                "8 - Вилучити девелопера з проекту, " +
                "інший символ - Повернутись у Головне меню)");
    }

    //@Override
    void getAll() {
        List<Project> projects = jdbcProjectDAO.getAll();

        System.out.println("********** Projects ************");
        if (projects != null){
            for (Project project: projects){
                System.out.println(project);
            }
        } else {
            System.out.println("Проекти відсутні.");
        }
        System.out.println("**********************************");
    }

    //@Override
    void getById() {
        System.out.print("Введіть id проекта: ");
        Long id = sc.nextLong();
        sc.nextLine();
        Project project = jdbcProjectDAO.getById(id);

        System.out.println("********** Project ************");
        if (project != null){
            System.out.println(project);
        } else {
            System.out.println("Проект з id = " + id + " відсутній.");
        }
        System.out.println("**********************************");
    }

    //@Override
    void create() {
        System.out.println("Введіть через пробіл наступні значення: ");
        System.out.println("Name Customer_Id Company_Id Costs");
        String delims = "[ ]";
        String[] line;
        line = sc.nextLine().split(delims);
        String name = line[0];
        Long customer_id = Long.parseLong(line[1]);
        Long company_id = Long.parseLong(line[2]);
        BigDecimal costs = new BigDecimal(line[3]);
        Project project = new Project();
        project.withName(name)
                .withCustomer_id(customer_id)
                .withCompany_id(company_id)
                .withCosts(costs);

        jdbcProjectDAO.create(project);
    }

    //@Override
    void update() {
        System.out.print("Введіть id проекта: ");
        Long id = sc.nextLong();
        sc.nextLine();
        Project project = jdbcProjectDAO.getById(id);  //перевірка чи проект з таким id існує

        if (project != null){
            System.out.println("Введіть через пробіл наступні значення: ");
            System.out.println("Name Customer_Id Company_Id Costs");
            String delims = "[ ]";
            String[] line;
            line = sc.nextLine().split(delims);
            String name = line[0];
            Long customer_id = Long.parseLong(line[1]);
            Long company_id = Long.parseLong(line[2]);
            BigDecimal costs = new BigDecimal(line[3]);
            project.withId(id)
                    .withName(name)
                    .withCustomer_id(customer_id)
                    .withCompany_id(company_id)
                    .withCosts(costs);
            jdbcProjectDAO.update(project);
        } else {
            System.out.println("Проект з id = " + id + " відсутній.");
        }
    }

    //@Override
    void delete() {
        System.out.print("Введіть id проекта: ");
        Long id = sc.nextLong();
        sc.nextLine();
        Project project = jdbcProjectDAO.getById(id);  //перевірка чи проект з таким id існує

        if (project != null){
            jdbcProjectDAO.delete(project);
        } else {
            System.out.println("Проект з id = " + id + " відсутній.");
        }
    }

    void getDevelopersById() {
        System.out.print("Введіть id проекта: ");
        Long id = sc.nextLong();
        sc.nextLine();
        Project project = jdbcProjectDAO.getDevelopersById(id);

        System.out.println("********** Project Developers ************");
        if (project != null){
            System.out.println(project.showProjectDevelopers());
        } else {
            System.out.println("Проект з id = " + id + " відсутній.");
        }
        System.out.println("**********************************");
    }

    void addProjectDeveloper(){
        System.out.println("Введіть через пробіл наступні значення: ");
        System.out.println("Project_Id Developer_Id");
        String delims = "[ ]";
        String[] line;
        line = sc.nextLine().split(delims);
        Long project_id = Long.parseLong(line[0]);
        Long developer_id = Long.parseLong(line[1]);

        Project project = jdbcProjectDAO.getById(project_id);  //перевірка чи проект з таким id існує
        if (project == null){
            System.out.println("Проект з project_id = " + project_id + " відсутній.");
            return;
        }

        Developer developer = jdbcDeveloperDAO.getById(developer_id);   // перевірка чи девелопер з таким id існує
        if (developer == null){
            System.out.println("Девелопер з developer_id = " + developer_id + " відсутній.");
            return;
        }

        jdbcProjectDAO.addDeveloper(project_id,developer_id);
    }

    void deleteProjectDeveloper(){
        System.out.println("Введіть через пробіл наступні значення: ");
        System.out.println("Project_Id Developer_Id");
        String delims = "[ ]";
        String[] line;
        line = sc.nextLine().split(delims);
        Long project_id = Long.parseLong(line[0]);
        Long developer_id = Long.parseLong(line[1]);

        Project project = jdbcProjectDAO.getById(project_id);  //перевірка чи проект з таким id існує
        if (project == null){
            System.out.println("Проект з project_id = " + project_id + " відсутній.");
            return;
        }

        Developer developer = jdbcDeveloperDAO.getById(developer_id);   // перевірка чи девелопер з таким id існує
        if (developer == null){
            System.out.println("Девелопер з developer_id = " + developer_id + " відсутній.");
            return;
        }

        jdbcProjectDAO.deleteDeveloper(project_id,developer_id);
    }
}
