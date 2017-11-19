package ua.goit.java8.javadeveloper.dao.hibernate;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ua.goit.java8.javadeveloper.dao.SkillDAO;
import ua.goit.java8.javadeveloper.dao.utils.HibernateUtil;
import ua.goit.java8.javadeveloper.model.Skill;

import java.util.List;

/**
 * Created by Taras on 11.11.2017.
 */
public class HibernateSkillDAOImpl implements SkillDAO {

    @Override
    public Skill getById(Long aLong) {
        Session session = null;
        Transaction tx = null;
        Skill result = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            // HQL
            Query query = session.createQuery("FROM Skill WHERE id = :id");
            query.setParameter("id",aLong);
            List<Skill> results = (List<Skill>) query.list();
            if (results.size() > 0){
                result = results.get(0);
            }
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
    public List<Skill> getAll() {
        Session session = null;
        Transaction tx = null;
        List<Skill> result = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();

            // HQL
            result = (List<Skill>) session.createQuery("FROM Skill order by name").list();

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
    public void create(Skill skill) {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            // HQL
            // перевірка чи замовник з таким name існує
            Query query = session.createQuery("FROM Skill WHERE name = :name");
            query.setParameter("name",skill.getName());
            List<Skill> results = (List<Skill>) query.list();
            if (results.size() > 0){
                System.out.println("Скіл з такою назвою вже існує.");
            } else {
                session.save(skill);
                System.out.println("Скіл створено успішно.");
            }
            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
            System.out.println("Неможливо створити скіл.");
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public void update(Skill skill) {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.update(skill);
            System.out.println("Скіл змінено успішно.");
            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
            System.out.println("Неможливо змінити скіл.");
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    // каскадне видалення скіла зі всіх пов'язаних таблиць
    @Override
    public void delete(Skill skill) {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.delete(skill);
            System.out.println("Скіл вилучено успішно.");
            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
            System.out.println("Неможливо вилучити скіл.");
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }
}
