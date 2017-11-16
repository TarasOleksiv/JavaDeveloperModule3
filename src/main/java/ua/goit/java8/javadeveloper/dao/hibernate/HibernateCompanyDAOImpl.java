package ua.goit.java8.javadeveloper.dao.hibernate;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
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
        Company result = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            // HQL
            Query query = session.createQuery("FROM Company WHERE id = :id");
            query.setParameter("id",aLong);
            List<Company> results = (List<Company>) query.list();
            if (results.size() > 0){
                result = results.get(0);
            }
            session.getTransaction().commit();
        } catch (Exception e) {
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
        List<Company> result = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();

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
        } catch (Exception e) {
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
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
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

    @Override
    public void update(Company company) {

    }

    @Override
    public void delete(Company company) {

    }
}
