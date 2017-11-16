package ua.goit.java8.javadeveloper.dao.hibernate;

import org.hibernate.Session;
import ua.goit.java8.javadeveloper.dao.CompanyDAO;
import ua.goit.java8.javadeveloper.dao.utils.HibernateUtil;
import ua.goit.java8.javadeveloper.model.Company;

import java.util.Iterator;
import java.util.List;

/**
 * Created by t.oleksiv on 16/11/2017.
 */
public class HibernateCompanyDAOImpl implements CompanyDAO {

    @Override
    public Company getById(Long aLong) {
        return null;
    }

    @Override
    public List<Company> getAll() {
        Session session = null;
        List<Company> result = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            result = (List<Company>) session.createQuery("FROM Company order by name").list();
            session.getTransaction().commit();
            /*
            Iterator itr = resultSelection.iterator();
            while(itr.hasNext()){
                Object[] obj = (Object[]) itr.next();
                //now you have one array of Object for each row
                Long id = Long.parseLong(String.valueOf(obj[0])); // don't know the type of column CLIENT assuming String
                String name = String.valueOf(obj[1]); //SERVICE assumed as int
                //same way for all obj[2], obj[3], obj[4]
                result.add(new Company(id,name));
            }
            */
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
