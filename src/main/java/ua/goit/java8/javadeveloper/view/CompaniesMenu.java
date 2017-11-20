package ua.goit.java8.javadeveloper.view;

import ua.goit.java8.javadeveloper.dao.hibernate.HibernateCompanyDAOImpl;
import ua.goit.java8.javadeveloper.model.Company;
import ua.goit.java8.javadeveloper.dao.CompanyDAO;

import java.util.List;

/**
 * Created by Taras on 10.11.2017.
 */

class CompaniesMenu extends AbstractMenu {

   private static CompanyDAO сompanyDAO = new HibernateCompanyDAOImpl();

    @Override
    void menu() {
        System.out.println("Меню Companies");
        System.out.println("Які дії виконуєм? (" +
                "1 - Вивести всі компанії, " +
                "2 - Вивести компанію по id, " +
                "3 - Створити компанію, " +
                "4 - Оновити компанію, " +
                "5 - Вилучити компанію, " +
                "інший символ - Повернутись у Головне меню)");
    }

    @Override
    void getAll() {
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

    @Override
    void getById() {
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

    @Override
    void create() {
        System.out.println("Введіть назву компанії: ");
        String name = sc.nextLine().trim();

        Company company = new Company();
        company.withName(name);
        сompanyDAO.create(company);
    }

    @Override
    void update() {
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

    @Override
    void delete() {
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
}
