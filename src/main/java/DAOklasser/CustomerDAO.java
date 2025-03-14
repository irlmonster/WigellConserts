package DAOklasser;

import com.grp5.Booking;
import com.grp5.entitys.Addresses;
import com.grp5.entitys.Concerts;
import com.grp5.entitys.Customer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class CustomerDAO {
    private final SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();


    //Create
    //registrerar en kund
    public void saveCustomer(Customer customer) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(customer);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();     // rollback ångrar alla ändringar om något gått fel
            }
            e.printStackTrace();
        }
    }


    // READ
    //Hämta alla kunder
    public List<Customer> getAllCustomers() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Customer ", Customer.class).getResultList();
        }
    }

    //Hämta en kund
    public Customer getCustomerByFirstName(String firstName) {
        try (Session session = sessionFactory.openSession()) {

            Customer customer = session.createQuery(
                            "FROM Customer c WHERE c.firstName = :firstName", Customer.class)
                    .setParameter("firstName", firstName)
                    .uniqueResult();
            System.out.println(customer);
            return customer;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Ingen kund hittades");
            return null; // returnerar null om ingen kund hittades
        }
    }


    // UPDATE
    public void updateCustomer(Customer customer) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.merge(customer); //MERGE istället för update?
            transaction.commit();
            System.out.println("Kunden har sparats");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();     // rollback ångrar alla ändringar om något gått fel
            }
            e.printStackTrace();
        }
    }

    // Uppdatera customer och address
    public void updateCustomerSettings(Customer customer, Addresses address) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            // Uppdatera adress
            session.merge(address);
            // Sätt adress i customer
            customer.setAddress(address);
            // Uppdatera customer
            session.merge(customer);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // DELETE
    public void deleteCustomer(Customer customer) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            if (customer != null) {
                session.remove(customer); // Remove istället för delete
                System.out.println("Kunden med namn " + customer + " har tagits bort!");
            } else {
                System.out.println("Hittade ingen kund med namnet " + customer + ".");
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();     // rollback ångrar alla ändringar om något gått fel
            }
            e.printStackTrace();
        }
    }
}
