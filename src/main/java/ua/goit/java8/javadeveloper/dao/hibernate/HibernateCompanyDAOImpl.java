package ua.goit.java8.javadeveloper.dao.hibernate;

import org.hibernate.*;
import ua.goit.java8.javadeveloper.dao.CompanyDAO;
import ua.goit.java8.javadeveloper.dao.utils.HibernateUtil;
import ua.goit.java8.javadeveloper.model.Company;

import java.util.List;
import java.util.Map;

/**
 * Created by t.oleksiv on 16/11/2017.
 */
public class HibernateCompanyDAOImpl implements CompanyDAO {

    @Override
    public Company getById(Long aLong) {
        Session session = null;
        Transaction tx = null;
        Company result = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            result = (Company) session.get(Company.class,aLong);
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
    public List<Company> getAll() {
        Session session = null;
        Transaction tx = null;
        List<Company> result = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();

            // HQL
            result = (List<Company>) session.createQuery("FROM Company order by name").list();

            /* SQL Entity query
            SQLQuery query = session.createSQLQuery("SELECT * FROM companies ORDER BY name");
            query.addEntity(Company.class);
            result = query.list();
            */

            /* SQL Scalar query
            SQLQuery query = session.createSQLQuery("SELECT id, name FROM companies ORDER BY name");
            query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
            List data = query.list();
            for(Object object : data) {
                Map row = (Map)object;
                System.out.print("Id: " + row.get("id"));
                System.out.println(", Name: " + row.get("name"));
            }
            */

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
    public void create(Company company) {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            // HQL
            // перевірка чи компанія з таким name існує
            Query query = session.createQuery("FROM Company WHERE name = :name");
            query.setParameter("name",company.getName());
            List<Company> results = (List<Company>) query.list();
            if (results.size() > 0){
                System.out.println("Компанія з такою назвою вже існує.");
            } else {
                session.save(company);
                System.out.println("Компанію створено успішно.");
            }
            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
            System.out.println("Неможливо створити компанію.");
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public void update(Company company) {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.update(company);
            System.out.println("Компанію змінено успішно.");
            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
            System.out.println("Неможливо змінити компанію.");
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    // каскадне видалення компанії зі всіх пов'язаних таблиць
    @Override
    public void delete(Company company) {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.delete(company);
            System.out.println("Компанію вилучено успішно.");
            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
            System.out.println("Неможливо вилучити компанію.");
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }
}
