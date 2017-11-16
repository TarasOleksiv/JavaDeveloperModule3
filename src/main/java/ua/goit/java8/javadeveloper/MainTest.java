package ua.goit.java8.javadeveloper;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ua.goit.java8.javadeveloper.dao.utils.HibernateUtil;
import ua.goit.java8.javadeveloper.model.Company;

import java.util.List;

/**
 * Created by t.oleksiv on 16/11/2017.
 */
public class MainTest {

    // Метод добавляет новую запись в таблицу PROFESSION
    private void addCompany(String name) {

        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Company company= new Company();
            company.setName(name);
            session.save(company);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {

                session.close();
            }
        }
    }

    // Метод возвращает список профессий
    private List<Company> listProfession() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List<Company> result = session.createSQLQuery("SELECT * FROM companies order by name").list();
        session.getTransaction().commit();
        return result;
    }

    // Метод удаляет по очереди все записи, которые ему переданы в виде списка
    private void deleteProfessions(List<Company> result) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        for(Company p : result) {
            System.out.println("Delete:"+p.getId()+":"+p.getName());
            session.delete(p);
            //session.flush();
        }
        session.getTransaction().commit();
    }

    // Методу удаляет одну запись
    private void deleteEntity(Object o) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.delete(o);
        session.flush();
        session.getTransaction().commit();
    }

    public static void main(String[] args) {
        MainTest main = new MainTest();

        // Добавление новых профессий
        main.addCompany("vasya");

        // Вариант вызова списка
        List<Company> result = main.listProfession();

        // Вариант вызова удаления одной записи
        //result = main.listProfession();
        //main.deleteEntity(result.get(0));

        // Вариант вызова списка и последующее удаление
        //result = main.listProfession();
        //main.deleteProfessions(result);
    }

}
