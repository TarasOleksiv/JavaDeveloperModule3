package ua.goit.java8.javadeveloper.view;

import java.util.Scanner;

/**
 * Created by t.oleksiv on 09/11/2017.
 */
public class MainMenu {
    private Scanner sc = new Scanner(System.in);

    public MainMenu(){
        show();
    }

    private void show(){
        System.out.println();
        System.out.println("Головне меню.");
        System.out.println("З яким об'єктом працюєм? (" +
                "1 - Companies, " +
                "2 - Customers, " +
                "3 - Skills, " +
                "4 - Developers, " +
                "5 - Projects, " +
                "інший символ - Вихід з програми)");
        System.out.print("Введіть символ: ");
        String n = sc.nextLine().trim();
        switch (n) {
            case "1":   //Companies
                new CompaniesMenu();
                break;
            case "2":   //Customers
                new CustomersMenu();
                break;
            case "3":   //Skills
                new SkillsMenu();
                break;
            case "4":   //Developers
                new DevelopersMenu();
                break;
            case "5":   //Projects
                new ProjectsMenu();
                break;
            default:
                System.out.println("Ви закінчили виконання програми.");
                return;
        }
        show();
    }
}
