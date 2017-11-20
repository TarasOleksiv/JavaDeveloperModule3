package ua.goit.java8.javadeveloper.view;

import ua.goit.java8.javadeveloper.dao.hibernate.HibernateCompanyDAOImpl;
import ua.goit.java8.javadeveloper.model.Company;
import ua.goit.java8.javadeveloper.dao.CompanyDAO;

import java.util.List;
import java.util.Scanner;

/**
 * Created by Taras on 10.11.2017.
 */

class CompaniesMenu {

   private static CompanyDAO сompanyDAO = new HibernateCompanyDAOImpl();
    private Scanner sc = new Scanner(System.in);

    public CompaniesMenu(){
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
                getDevelopersByCompanyId();
                break;
            case "7":
                getProjectsByCompanyId();
                break;
            default:
                System.out.println("Повернення у Головне меню");
                return;
        }
        show();
    }

    private void menu() {
        System.out.println("Меню Companies");
        System.out.println("Які дії виконуєм? (" +
                "1 - Вивести всі компанії, " +
                "2 - Вивести компанію по id, " +
                "3 - Створити компанію, " +
                "4 - Оновити компанію, " +
                "5 - Вилучити компанію, " + "\n" +
                "6 - Вивести всіх девелоперів компанії, " +
                "7 - Вивести всі проекти компанії, " +
                "інший символ - Повернутись у Головне меню)");
    }

    private void getAll() {
        List<Company> companies = сompanyDAO.getAll();

        System.out.println("********** Companies ************");
        if (companies != null){
            for (Company company: companies){
                System.out.println(company);
            }
        } else {
            System.out.println("Компанії відсутні.");
        }
        System.out.println("**********************************");


    }

    private void getById() {
        System.out.print("Введіть id компанії: ");
        Long id = sc.nextLong();
        sc.nextLine();
        Company company = сompanyDAO.getById(id);

        System.out.println("********** Company ************");
        if (company != null){
            System.out.println(company);
        } else {
            System.out.println("Компанія з id = " + id + " відсутня.");
        }
        System.out.println("**********************************");
    }

    private void create() {
        System.out.println("Введіть назву компанії: ");
        String name = sc.nextLine().trim();

        Company company = new Company();
        company.withName(name);
        сompanyDAO.create(company);
    }

    private void update() {
        System.out.print("Введіть id компанії: ");
        Long id = sc.nextLong();
        sc.nextLine();
        Company company = сompanyDAO.getById(id);  //перевірка чи компанія з таким id існує

        if (company != null){
            System.out.println("Введіть назву компанії: ");
            String name = sc.nextLine().trim();
            company.withId(id)
                    .withName(name);
            сompanyDAO.update(company);
        } else {
            System.out.println("Компанія з id = " + id + " відсутня.");
        }
    }

    private void delete() {
        System.out.print("Введіть id компанії: ");
        Long id = sc.nextLong();
        sc.nextLine();
        Company company = сompanyDAO.getById(id);  //перевірка чи компанія з таким id існує

        if (company != null){
            сompanyDAO.delete(company);
        } else {
            System.out.println("Компанія з id = " + id + " відсутня.");
        }
    }

    // вивести всіх девелоперів компанії
    private void getDevelopersByCompanyId() {
        System.out.print("Введіть id компанії: ");
        Long id = sc.nextLong();
        sc.nextLine();
        Company company = сompanyDAO.getById(id);

        System.out.println("********** Company Developers ************");
        if (company != null){
            System.out.println(company.showCompanyDevelopers());
        } else {
            System.out.println("Компанія з id = " + id + " відсутня.");
        }
        System.out.println("**********************************");
    }

    // вивести всі проекти компанії
    private void getProjectsByCompanyId() {
        System.out.print("Введіть id компанії: ");
        Long id = sc.nextLong();
        sc.nextLine();
        Company company = сompanyDAO.getById(id);

        System.out.println("********** Company Projects ************");
        if (company != null){
            System.out.println(company.showCompanyProjects());
        } else {
            System.out.println("Компанія з id = " + id + " відсутня.");
        }
        System.out.println("**********************************");
    }
}
