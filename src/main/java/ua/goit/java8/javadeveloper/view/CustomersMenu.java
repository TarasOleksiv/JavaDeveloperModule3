package ua.goit.java8.javadeveloper.view;

import ua.goit.java8.javadeveloper.dao.hibernate.HibernateCustomerDAOImpl;
import ua.goit.java8.javadeveloper.model.Customer;
import ua.goit.java8.javadeveloper.dao.CustomerDAO;

import java.util.List;
import java.util.Scanner;

/**
 * Created by Taras on 11.11.2017.
 */
public class CustomersMenu {

    private static CustomerDAO сustomerDAO = new HibernateCustomerDAOImpl();
    private Scanner sc = new Scanner(System.in);

    public CustomersMenu(){
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
                getProjectsByCustomerId();
                break;
            default:
                System.out.println("Повернення у Головне меню");
                return;
        }
        show();
    }

    private void menu() {
        System.out.println("Меню Customers");
        System.out.println("Які дії виконуєм? (" +
                "1 - Вивести всіх замовників, " +
                "2 - Вивести замовника по id, " +
                "3 - Створити замовника, " +
                "4 - Оновити замовника, " +
                "5 - Вилучити замовника, " + "\n" +
                "6 - Вивести всі проекти замовника, " +
                "інший символ - Повернутись у Головне меню)");
    }

    private void getAll() {
        List<Customer> customers = сustomerDAO.getAll();

        System.out.println("********** Customers ************");
        if (customers != null){
            for (Customer customer: customers){
                System.out.println(customer);
            }
        } else {
            System.out.println("Замовники відсутні.");
        }
        System.out.println("**********************************");
    }

    private void getById() {
        System.out.print("Введіть id замовника: ");
        Long id = sc.nextLong();
        sc.nextLine();
        Customer customer = сustomerDAO.getById(id);

        System.out.println("********** Customer ************");
        if (customer != null){
            System.out.println(customer);
        } else {
            System.out.println("Замовник з id = " + id + " відсутній.");
        }
        System.out.println("**********************************");
    }

    private void create() {
        System.out.println("Введіть назву замовника: ");
        String name = sc.nextLine().trim();

        Customer customer = new Customer();
        customer.withName(name);
        сustomerDAO.create(customer);
    }

    private void update() {
        System.out.print("Введіть id замовника: ");
        Long id = sc.nextLong();
        sc.nextLine();
        Customer customer = сustomerDAO.getById(id);  //перевірка чи замовник з таким id існує

        if (customer != null){
            System.out.println("Введіть назву замовника: ");
            String name = sc.nextLine().trim();
            customer.withId(id)
                    .withName(name);
            сustomerDAO.update(customer);
        } else {
            System.out.println("Замовник з id = " + id + " відсутній.");
        }
    }

    private void delete() {
        System.out.print("Введіть id замовника: ");
        Long id = sc.nextLong();
        sc.nextLine();
        Customer customer = сustomerDAO.getById(id);  //перевірка чи замовник з таким id існує

        if (customer != null){
            сustomerDAO.delete(customer);
        } else {
            System.out.println("Замовник з id = " + id + " відсутній.");
        }
    }

    // вивести всі проекти замовника
    private void getProjectsByCustomerId() {
        System.out.print("Введіть id замовника: ");
        Long id = sc.nextLong();
        sc.nextLine();
        Customer customer = сustomerDAO.getById(id);

        System.out.println("********** Customer Projects ************");
        if (customer != null){
            System.out.println(customer.showCustomerProjects());
        } else {
            System.out.println("Замовник з id = " + id + " відсутній.");
        }
        System.out.println("**********************************");
    }
}
