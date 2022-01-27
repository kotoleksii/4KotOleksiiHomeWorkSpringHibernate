package org.example.services;

import org.example.entity.Notebook;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class DataManager {

    public static void showAllRecords(Session session) {
        List<Notebook> records = session.createQuery("from Notebook ").getResultList();
        records.forEach(System.out::println);

        continueMessage();
    }

    public static void addNewRecord(Session session) {
        try {
            Scanner input = new Scanner(System.in);

            System.out.print("Введіть назву запису: ");
            String title = input.nextLine();

            System.out.print("Введіть опис запису: ");
            String description = input.nextLine();

            Notebook notebook = new Notebook(title, description);
            session.save(notebook);

            System.out.print("\nЗапис " + title + " додано!");
            continueMessage();
        } catch (Exception e) {
            System.out.println("\nЩось пішло не так ... Спробуйте ще!");
            continueMessage();
        }
    }

    public static void editRecordByID(Session session, int recordID) {
        try {
            Notebook notebook = session.get(Notebook.class, recordID);
            System.out.println("\nID: " + notebook.getId() + "\t" +
                    "Title: " + notebook.getTitle() + "\t" +
                    "Description: " + notebook.getDescription());

            Scanner input = new Scanner(System.in);
            System.out.print("Введіть новий Title: ");
            String editTitle = input.nextLine();

            System.out.print("Введіть новий Description: ");
            String editDescription = input.nextLine();

            notebook.setTitle(editTitle);
            notebook.setDescription(editDescription);

            System.out.print("\nЗапис " + notebook.getId() + " відредаговано!");
            continueMessage();
        } catch (Exception e) {
            System.out.println("\nЩось пішло не так ... Спробуйте ще!" +
                    " Ймовірно введеного ID " + recordID + " не існує");
            continueMessage();
        }
    }

    public static void deleteRecordByID(Session session, int recordID) {
        try {
            Notebook notebook = session.get(Notebook.class, recordID);
            System.out.println("\nID: " + notebook.getId() + "\t" +
                    "Title: " + notebook.getTitle() + "\t" +
                    "Description: " + notebook.getDescription());

            System.out.print("\nВи точно хочете видалити запис № " + recordID + "(y/n) ");

            Scanner sc = new Scanner(System.in);
            String input = sc.nextLine();

            switch (input) {
                case "y":
                    session.delete(notebook);
                    System.out.print("\nЗапис " + notebook.getId() + " видалено!");
                    break;
                case "n":
                    break;
                default:
                    System.out.println("error");
                    break;
            }
            continueMessage();
        } catch (Exception e) {
            System.out.println("\nЩось пішло не так ... Спробуйте ще!" +
                    " Ймовірно введеного ID " + recordID + " не існує");
            continueMessage();
        }
    }

    public static int getInputRecordID() {
        Scanner inputEditID = new Scanner(System.in);
        System.out.print("Введіть ID запису: ");
        int recordID = inputEditID.nextInt();

        return recordID;
    }

    public static void continueMessage() {
        System.out.println("\nPress any key to continue...");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void selectedMenu(Session session) {
        Scanner sc = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n1.Показати все\n" +
                    "2.Додати новий запис\n" +
                    "3.Редагувати запис\n" +
                    "4.Видалити запис\n" +
                    "0.Вихід");

            System.out.print("\nВиберіть дію: ");
            int option = sc.nextInt();

            System.out.println();

            switch (option) {
                case 0:
                    running = false;
                    break;
                case 1:
                    showAllRecords(session);
                    break;
                case 2:
                    addNewRecord(session);
                    break;
                case 3:
                    editRecordByID(session, getInputRecordID());
                    break;
                case 4:
                    deleteRecordByID(session, getInputRecordID());
                    break;
                default:
                    System.out.println("Щось пішло не так ... Спробуйте ще!");
            }
        }
    }

    public static void start() {
        SessionFactory factory = new Configuration()
//                .configure("resources/hibernate.cfg.xml")
                .configure(new File("resources/hibernate.cfg.xml"))
                .addAnnotatedClass(Notebook.class)
                .buildSessionFactory();

        Session session = factory.getCurrentSession();

        session.beginTransaction();

        selectedMenu(session);

        session.getTransaction().commit();
    }
}
