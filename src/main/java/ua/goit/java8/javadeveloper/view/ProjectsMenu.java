package ua.goit.java8.javadeveloper.view;

import ua.goit.java8.javadeveloper.dao.CompanyDAO;
import ua.goit.java8.javadeveloper.dao.CustomerDAO;
import ua.goit.java8.javadeveloper.dao.DeveloperDAO;
import ua.goit.java8.javadeveloper.dao.ProjectDAO;
import ua.goit.java8.javadeveloper.dao.hibernate.HibernateCompanyDAOImpl;
import ua.goit.java8.javadeveloper.dao.hibernate.HibernateCustomerDAOImpl;
import ua.goit.java8.javadeveloper.dao.hibernate.HibernateDeveloperDAOImpl;
import ua.goit.java8.javadeveloper.dao.hibernate.HibernateProjectDAOImpl;
import ua.goit.java8.javadeveloper.model.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Taras on 11.11.2017.
 */
public class ProjectsMenu {

    private static ProjectDAO projectDAO = new HibernateProjectDAOImpl();
    private static CompanyDAO companyDAO = new HibernateCompanyDAOImpl();
    private static CustomerDAO customerDAO = new HibernateCustomerDAOImpl();
    private static DeveloperDAO developerDAO = new HibernateDeveloperDAOImpl();
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
                getDevelopersByProjectId();
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
        List<Project> projects = projectDAO.getAll();

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
        Project project = projectDAO.getById(id);

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

        Company company = companyDAO.getById(company_id);
        // перевіряєм чи компанія існує
        if (company == null){
            System.out.println("Компанія з id = " + company_id + " відсутня.");
            return;
        }

        Customer customer = customerDAO.getById(customer_id);
        // перевіряєм чи замовник існує
        if (customer == null){
            System.out.println("Замовник з id = " + customer_id + " відсутній.");
            return;
        }

        Project project = new Project();
        project.withName(name)
                .withCustomer(customer)
                .withCompany(company)
                .withCosts(costs);

        projectDAO.create(project);
    }

    //@Override
    void update() {
        System.out.print("Введіть id проекта: ");
        Long id = sc.nextLong();
        sc.nextLine();
        Project project = projectDAO.getById(id);  //перевірка чи проект з таким id існує

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
            Company company = companyDAO.getById(company_id);
            Customer customer = customerDAO.getById(customer_id);
            project.withId(id)
                    .withName(name)
                    .withCustomer(customer)
                    .withCompany(company)
                    .withCosts(costs);
            projectDAO.update(project);
        } else {
            System.out.println("Проект з id = " + id + " відсутній.");
        }
    }

    //@Override
    void delete() {
        System.out.print("Введіть id проекта: ");
        Long id = sc.nextLong();
        sc.nextLine();
        Project project = projectDAO.getById(id);  //перевірка чи проект з таким id існує

        if (project != null){
            projectDAO.delete(project);
        } else {
            System.out.println("Проект з id = " + id + " відсутній.");
        }
    }

    // вивести список девелоперів на проекті
    void getDevelopersByProjectId() {
        System.out.print("Введіть id проекта: ");
        Long id = sc.nextLong();
        sc.nextLine();
        Project project = projectDAO.getById(id);

        System.out.println("********** Project Developers ************");
        if (project != null){
            System.out.println(project.showProjectDevelopers());
        } else {
            System.out.println("Проект з id = " + id + " відсутній.");
        }
        System.out.println("**********************************");
    }

    // додати девелопера на проект
    void addProjectDeveloper(){
        System.out.println("Введіть через пробіл наступні значення: ");
        System.out.println("Project_Id - id проекту, якому треба додати девелопера; Developer_Id - id девелопера, якого треба додати на проект");
        System.out.println("Project_Id Developer_Id");
        String delims = "[ ]";
        String[] line;
        line = sc.nextLine().split(delims);
        Long project_id = Long.parseLong(line[0]);
        Long developer_id = Long.parseLong(line[1]);

        Project project = projectDAO.getById(project_id);  //перевірка чи проект з таким id існує
        if (project == null){
            System.out.println("Проект з project_id = " + project_id + " відсутній.");
            return;
        }

        Developer developer = developerDAO.getById(developer_id);   // перевірка чи девелопер з таким id існує
        if (developer == null){
            System.out.println("Девелопер з developer_id = " + developer_id + " відсутній.");
            return;
        }

        projectDAO.addDeveloper(project,developer);
    }

    // вилучити девелопера з проекту
    void deleteProjectDeveloper(){
        System.out.println("Введіть через пробіл наступні значення: ");
        System.out.println("Project_Id - id проекту, з якого треба вилучити девелопера; Developer_Id - id девелопера, якого треба вилучити з проекту");
        System.out.println("Project_Id Developer_Id");
        String delims = "[ ]";
        String[] line;
        line = sc.nextLine().split(delims);
        Long project_id = Long.parseLong(line[0]);
        Long developer_id = Long.parseLong(line[1]);

        Project project = projectDAO.getById(project_id);  //перевірка чи проект з таким id існує
        if (project == null){
            System.out.println("Проект з project_id = " + project_id + " відсутній.");
            return;
        }

        Developer developer = developerDAO.getById(developer_id);   // перевірка чи девелопер з таким id існує
        if (developer == null){
            System.out.println("Девелопер з developer_id = " + developer_id + " відсутній.");
            return;
        }

        projectDAO.deleteDeveloper(project,developer);
    }
}
