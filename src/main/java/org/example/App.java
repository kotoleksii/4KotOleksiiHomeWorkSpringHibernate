package org.example;

import org.example.entity.Notebook;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        System.out.println("Hello World!");

        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Notebook.class)
                .buildSessionFactory();


        // додаємо запис
//        Session session = factory.getCurrentSession();
//        session.beginTransaction();

//        Notebook notebook = new Notebook("title", "description");
//
//        session.save(notebook);
//
//        session.getTransaction().commit();

        // показати всі записи
//        Session session = factory.getCurrentSession();
//        session.beginTransaction();
//
//        List<Notebook> records = session.createQuery("from Notebook ").getResultList();
//        records.forEach(System.out::println);
//
//        session.getTransaction().commit();

        // змінити вибраний запис
//        Session session = factory.getCurrentSession();
//        session.beginTransaction();
//
//        //ID
////        Notebook notebook = session.get(Notebook.class, 1);
////        notebook.setTitle("test " + notebook.getTitle());
//
//        //Title
////        session.createQuery("update Notebook set description = 'proba' where title = 'title'").executeUpdate();
//
//        session.getTransaction().commit();

        // видалити вибраний запис
        Session session = factory.getCurrentSession();
        session.beginTransaction();

        //ID
//        Notebook notebook = session.get(Notebook.class, 1);
//        session.delete(notebook);

        //Filter
//        session.createQuery("delete Notebook where title = 'title'").executeUpdate();

        session.getTransaction().commit();

    }
}
