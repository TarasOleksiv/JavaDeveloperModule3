package ua.goit.java8.javadeveloper.dao.hibernate;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ua.goit.java8.javadeveloper.dao.DeveloperDAO;
import ua.goit.java8.javadeveloper.dao.utils.HibernateUtil;
import ua.goit.java8.javadeveloper.model.Developer;
import ua.goit.java8.javadeveloper.model.Skill;

import java.util.List;

/**
 * Created by Taras on 17.11.2017.
 */
public class HibernateDeveloperDAOImpl implements DeveloperDAO {

    @Override
    public Developer getById(Long aLong) {
        Session session = null;
        Transaction tx = null;
        Developer result = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            result = (Developer) session.get(Developer.class,aLong);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return result;
    }

    @Override
    public List<Developer> getAll() {
        Session session = null;
        Transaction tx = null;
        List<Developer> result = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();

            // HQL
            result = (List<Developer>) session.createQuery("FROM Developer order by id").list();

            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return result;
    }

    @Override
    public void create(Developer developer) {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.save(developer);
            System.out.println("Девелопера створено успішно.");
            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
            System.out.println("Неможливо створити девелопера.");
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public void update(Developer developer) {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.update(developer);
            System.out.println("Девелопера змінено успішно.");
            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
            System.out.println("Неможливо змінити девелопера.");
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    // каскадне видалення девелопера зі всіх пов'язаних таблиць
    @Override
    public void delete(Developer developer) {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.delete(developer);
            System.out.println("Девелопера вилучено успішно.");
            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
            System.out.println("Неможливо вилучити девелопера.");
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public void addSkill(Developer developer, Skill skill) {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            developer.getSkills().add(skill);
            session.update(developer);
            System.out.println("Скіл девелоперу додано успішно.");
            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
            System.out.println("Неможливо додати скіл девелоперу.");
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public void deleteSkill(Developer developer, Skill skill) {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            developer.getSkills().remove(skill);
            session.update(developer);
            System.out.println("Скіл девелопера вилучено успішно.");
            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
            System.out.println("Неможливо вилучити скіл у девелопера.");
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }
}
