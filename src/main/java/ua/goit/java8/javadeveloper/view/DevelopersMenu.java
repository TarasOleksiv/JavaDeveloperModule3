package ua.goit.java8.javadeveloper.view;

import ua.goit.java8.javadeveloper.dao.CompanyDAO;
import ua.goit.java8.javadeveloper.dao.SkillDAO;
import ua.goit.java8.javadeveloper.dao.hibernate.HibernateCompanyDAOImpl;
import ua.goit.java8.javadeveloper.dao.hibernate.HibernateDeveloperDAOImpl;
import ua.goit.java8.javadeveloper.dao.hibernate.HibernateSkillDAOImpl;
import ua.goit.java8.javadeveloper.model.Company;
import ua.goit.java8.javadeveloper.model.Developer;
import ua.goit.java8.javadeveloper.dao.DeveloperDAO;
import ua.goit.java8.javadeveloper.model.Skill;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Taras on 10.11.2017.
 */

public class DevelopersMenu {

    private static DeveloperDAO developerDAO = new HibernateDeveloperDAOImpl();
    private static CompanyDAO companyDAO = new HibernateCompanyDAOImpl();
    private static SkillDAO skillDAO = new HibernateSkillDAOImpl();

    private Scanner sc = new Scanner(System.in);

    public DevelopersMenu(){
        show();
    }

    void show(){
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
                getSkillsByDeveloperId();
                break;
            case "7":
                addDeveloperSkill();
                break;
            case "8":
                deleteDeveloperSkill();
                break;
            default:
                System.out.println("Повернення у Головне меню");
                return;
        }
        show();
    }

    //@Override
    void menu() {
        System.out.println("Меню Developers");
        System.out.println("Які дії виконуєм? (" +
                "1 - Вивести всіх девелоперів, " +
                "2 - Вивести девелопера по id, " +
                "3 - Створити девелопера, " +
                "4 - Оновити девелопера, " +
                "5 - Вилучити девелопера, " + "\n" +
                "6 - Вивести скіли девелопера, " +
                "7 - Додати скіл девелоперу, " +
                "8 - Вилучити скіл девелопера, " +
                "інший символ - Повернутись у Головне меню)");
    }

    //@Override
    void getAll(){
        List<Developer> developers = developerDAO.getAll();

        System.out.println("********** Developers ************");
        if (developers != null){
            for (Developer developer: developers){
                System.out.println(developer);
            }
        } else {
            System.out.println("Девелопери відсутні.");
        }
        System.out.println("**********************************");
    }

    //@Override
    void getById() {
        System.out.print("Введіть id девелопера: ");
        Long id = sc.nextLong();
        sc.nextLine();
        Developer developer = developerDAO.getById(id);

        System.out.println("********** Developer ************");
        if (developer != null){
            System.out.println(developer);
        } else {
            System.out.println("Девелопер з id = " + id + " відсутній.");
        }
        System.out.println("**********************************");
    }

    //@Override
    void create() {
        System.out.println("Введіть через пробіл наступні значення: ");
        System.out.println("Firstname Lastname Company_Id Salary");
        String delims = "[ ]";
        String[] line;
        line = sc.nextLine().split(delims);
        String firstName = line[0];
        String lastName = line[1];
        Long company_id = Long.parseLong(line[2]);
        BigDecimal salary = new BigDecimal(line[3]);
        Company company = companyDAO.getById(company_id);

        if (company == null){
            System.out.println("Компанія з id = " + company_id + " відсутня.");
            return;
        } else {
            Developer developer = new Developer();
            developer.withFirstName(firstName)
                    .withLastName(lastName)
                    .withCompany(company)
                    .withSalary(salary);

            developerDAO.create(developer);
        }
    }

    //@Override
    void update() {
        System.out.print("Введіть id девелопера: ");
        Long id = sc.nextLong();
        sc.nextLine();
        Developer developer = developerDAO.getById(id);;  //перевірка чи девелопер з таким id існує

        if (developer != null){
            System.out.println("Введіть через пробіл наступні значення: ");
            System.out.println("Firstname Lastname Company_Id Salary");
            String delims = "[ ]";
            String[] line;
            line = sc.nextLine().split(delims);
            String firstName = line[0];
            String lastName = line[1];
            Long company_id = Long.parseLong(line[2]);
            BigDecimal salary = new BigDecimal(line[3]);
            Company company = companyDAO.getById(company_id);
            developer.withId(id)
                    .withFirstName(firstName)
                    .withLastName(lastName)
                    .withCompany(company)
                    .withSalary(salary);
            developerDAO.update(developer);
        } else {
            System.out.println("Девелопер з id = " + id + " відсутній.");
        }
    }

    //@Override
    void delete() {
        System.out.print("Введіть id девелопера: ");
        Long id = sc.nextLong();
        sc.nextLine();
        Developer developer = developerDAO.getById(id);  //перевірка чи девелопер з таким id існує

        if (developer != null){
            developerDAO.delete(developer);
        } else {
            System.out.println("Девелопер з id = " + id + " відсутній.");
        }
    }

    // вивести всі скіли девелопера
    void getSkillsByDeveloperId() {
        System.out.print("Введіть id девелопера: ");
        Long id = sc.nextLong();
        sc.nextLine();
        Developer developer = developerDAO.getById(id);

        System.out.println("********** Developer Skills ************");
        if (developer != null){
            System.out.println(developer.showDeveloperSkills());
        } else {
            System.out.println("Девелопер з id = " + id + " відсутній.");
        }
        System.out.println("**********************************");
    }

    // додати скіл девелоперу
    void addDeveloperSkill(){
        System.out.println("Введіть через пробіл наступні значення: ");
        System.out.println("Developer_Id - id девелопера, якому треба додати скіл; Skill_Id - id скіла, який треба додати девелоперу");
        System.out.println("Developer_Id Skill_Id");
        String delims = "[ ]";
        String[] line;
        line = sc.nextLine().split(delims);
        Long developer_id = Long.parseLong(line[0]);
        Long skill_id = Long.parseLong(line[1]);

        Developer developer = developerDAO.getById(developer_id);  //перевірка чи девелопер з таким id існує
        if (developer == null){
            System.out.println("Девелопер з developer_id = " + developer_id + " відсутній.");
            return;
        }

        Skill skill = skillDAO.getById(skill_id);   // перевірка чи скіл з таким id існує
        if (skill == null){
            System.out.println("Скіл з skill_id = " + skill_id + " відсутній.");
            return;
        }

        developerDAO.addSkill(developer,skill);
    }

    // вилучити скіл девелопера
    void deleteDeveloperSkill(){
        System.out.println("Введіть через пробіл наступні значення: ");
        System.out.println("Developer_Id - id девелопера, якому треба вилучити скіл; Skill_Id - id скіла, який треба вилучити у девелопера");
        System.out.println("Developer_Id Skill_Id");
        String delims = "[ ]";
        String[] line;
        line = sc.nextLine().split(delims);
        Long developer_id = Long.parseLong(line[0]);
        Long skill_id = Long.parseLong(line[1]);

        Developer developer = developerDAO.getById(developer_id);  //перевірка чи девелопер з таким id існує
        if (developer == null){
            System.out.println("Девелопер з developer_id = " + developer_id + " відсутній.");
            return;
        }

        Skill skill = skillDAO.getById(skill_id);   // перевірка чи скіл з таким id існує
        if (skill == null){
            System.out.println("Скіл з skill_id = " + skill_id + " відсутній.");
            return;
        }

        developerDAO.deleteSkill(developer,skill);
    }

}
