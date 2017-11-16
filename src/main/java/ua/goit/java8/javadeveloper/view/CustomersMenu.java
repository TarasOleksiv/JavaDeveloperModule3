package ua.goit.java8.javadeveloper.view;

import ua.goit.java8.javadeveloper.dao.jdbc.JdbcCustomerDAOImpl;
import ua.goit.java8.javadeveloper.model.Customer;

import java.util.List;

/**
 * Created by Taras on 11.11.2017.
 */
public class CustomersMenu extends AbstractMenu {

    private static JdbcCustomerDAOImpl jdbcCustomerDAO = new JdbcCustomerDAOImpl();

    @Override
    void menu() {
        System.out.println("Меню Customers");
        System.out.println("Які дії виконуєм? (" +
                "1 - Вивести всіх замовників, " +
                "2 - Вивести замовника по id, " +
                "3 - Створити замовника, " +
                "4 - Оновити замовника, " +
                "5 - Вилучити замовника, " +
                "інший символ - Повернутись у Головне меню)");
    }

    @Override
    void getAll() {
        List<Customer> customers = jdbcCustomerDAO.getAll();

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

    @Override
    void getById() {
        System.out.print("Введіть id замовника: ");
        Long id = sc.nextLong();
        sc.nextLine();
        Customer customer = jdbcCustomerDAO.getById(id);

        System.out.println("********** Customer ************");
        if (customer != null){
            System.out.println(customer);
        } else {
            System.out.println("Замовник з id = " + id + " відсутній.");
        }
        System.out.println("**********************************");
    }

    @Override
    void create() {
        System.out.println("Введіть назву замовника: ");
        String name = sc.nextLine().trim();

        Customer customer = new Customer();
        customer.withName(name);
        jdbcCustomerDAO.create(customer);
    }

    @Override
    void update() {
        System.out.print("Введіть id замовника: ");
        Long id = sc.nextLong();
        sc.nextLine();
        Customer customer = jdbcCustomerDAO.getById(id);  //перевірка чи замовник з таким id існує

        if (customer != null){
            System.out.println("Введіть назву замовника: ");
            String name = sc.nextLine().trim();
            customer.withId(id)
                    .withName(name);
            jdbcCustomerDAO.update(customer);
        } else {
            System.out.println("Замовник з id = " + id + " відсутній.");
        }
    }

    @Override
    void delete() {
        System.out.print("Введіть id замовника: ");
        Long id = sc.nextLong();
        sc.nextLine();
        Customer customer = jdbcCustomerDAO.getById(id);  //перевірка чи замовник з таким id існує

        if (customer != null){
            jdbcCustomerDAO.delete(customer);
        } else {
            System.out.println("Замовник з id = " + id + " відсутній.");
        }
    }
}
