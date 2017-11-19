package ua.goit.java8.javadeveloper.dao.hibernate;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ua.goit.java8.javadeveloper.dao.CustomerDAO;
import ua.goit.java8.javadeveloper.dao.utils.HibernateUtil;
import ua.goit.java8.javadeveloper.model.Customer;

import java.util.List;

/**
 * Created by Taras on 11.11.2017.
 */
public class HibernateCustomerDAOImpl implements CustomerDAO {

    @Override
    public Customer getById(Long aLong) {
        Session session = null;
        Transaction tx = null;
        Customer result = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            // HQL
            Query query = session.createQuery("FROM Customer WHERE id = :id");
            query.setParameter("id",aLong);
            List<Customer> results = (List<Customer>) query.list();
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
    public List<Customer> getAll() {
        Session session = null;
        Transaction tx = null;
        List<Customer> result = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();

            // HQL
            result = (List<Customer>) session.createQuery("FROM Customer order by name").list();

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
    public void create(Customer customer) {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            // HQL
            // перевірка чи замовник з таким name існує
            Query query = session.createQuery("FROM Customer WHERE name = :name");
            query.setParameter("name",customer.getName());
            List<Customer> results = (List<Customer>) query.list();
            if (results.size() > 0){
                System.out.println("Замовник з такою назвою вже існує.");
            } else {
                session.save(customer);
                System.out.println("Замовника створено успішно.");
            }
            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
            System.out.println("Неможливо створити замовника.");
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public void update(Customer customer) {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.update(customer);
            System.out.println("Замовника змінено успішно.");
            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
            System.out.println("Неможливо змінити замовника.");
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    // каскадне видалення замовника зі всіх пов'язаних таблиць
    @Override
    public void delete(Customer customer) {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.delete(customer);
            System.out.println("Замовника вилучено успішно.");
            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
            System.out.println("Неможливо вилучити замовника.");
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }
}
