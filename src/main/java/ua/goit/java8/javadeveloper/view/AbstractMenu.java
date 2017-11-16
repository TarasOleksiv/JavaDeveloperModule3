package ua.goit.java8.javadeveloper.view;

import java.util.Scanner;

/**
 * Created by Taras on 10.11.2017.
 */

abstract class AbstractMenu {
    protected Scanner sc = new Scanner(System.in);

    AbstractMenu(){
        show();
    }

    private void show() {
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
            default:
                System.out.println("Повернення у Головне меню");
                return;
        }
        show();
    }

    abstract void menu();

    abstract void getAll();

    abstract void getById();

    abstract void create();

    abstract void update();

    abstract void delete();
}
