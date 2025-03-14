package DAOklasser;

import com.grp5.entitys.Arena;
import com.grp5.entitys.Concerts;
import com.grp5.entitys.Customer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import com.grp5.entitys.WC;
import org.hibernate.cfg.Configuration;


import java.util.*;


public class WcDAO {

    private static final SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

// CREATE

    public void createTicketWC(WC wc) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();

            // Hämta konsert från databasen
            Concerts concert = session.get(Concerts.class, wc.getConcert().getId());
            if (concert == null) {
                throw new IllegalStateException("Konserten existerar inte i databasen!");
            }
            wc.setConcert(concert);

            // Hämta kunden från databasen
            Customer customer = session.get(Customer.class, wc.getCustomer().getId());
            if (customer == null) {
                throw new IllegalStateException("Kunden existerar inte i databasen!");
            }
            wc.setCustomer(customer);

            System.out.println("Försöker skapa biljett:");
            System.out.println("Namn: " + wc.getName());
            System.out.println("Konsert-ID: " + (wc.getConcert() != null ? wc.getConcert().getId() : "null"));
            System.out.println("Kund-ID: " + (wc.getCustomer() != null ? wc.getCustomer().getId() : "null"));

            session.persist(wc);
            tx.commit();
            System.out.println("Biljett sparad: " + wc);

        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    // READ
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

    // hämta bokningar från kund till vald koncert
    public Map<Customer, Long> getTicketCountsForConcert(Concerts concert) {
        Map<Customer, Long> ticketsMap = new HashMap<>();
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();

            List<Object[]> resultList = session.createQuery(
                            "SELECT wc.customer, COUNT(wc) " +
                                    "FROM WC wc " +
                                    "WHERE wc.concert.id = :concertId " +
                                    "GROUP BY wc.customer", Object[].class)
                    .setParameter("concertId", concert.getId())
                    .getResultList();

            for (Object[] row : resultList) {
                Customer customer = (Customer) row[0];
                Long count = (Long) row[1];
                ticketsMap.put(customer, count);
            }

            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
        return ticketsMap;
    }

    // UPDATE
    public void updateArena(Arena arena) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.merge(arena); //MERGE istället för update?
            transaction.commit();
            System.out.println("Arenan har uppdaterats");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();     // rollback ångrar alla ändringar om något gått fel
            }
            e.printStackTrace();
        }
    }

    // DELETE
    public void deleteTicketsForCustomerAndConcert(Customer customer, Concerts concert) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            int deletedCount = session.createQuery("DELETE FROM WC WHERE customer.id = :customerId AND concert.id = :concertId")
                    .setParameter("customerId", customer.getId())
                    .setParameter("concertId", concert.getId())
                    .executeUpdate();
            tx.commit();
            System.out.println("Antal biljetter borttagna för kund " + customer.getId() + " och konsert " + concert.getId() + ": " + deletedCount);
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    public void deleteAllTickets() {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            int deletedCount = session.createQuery("DELETE FROM WC").executeUpdate();
            tx.commit();
            System.out.println("Antal biljetter borttagna: " + deletedCount);
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    public void deleteTicketsForCustomer(Customer customer) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            int deletedCount = session.createQuery("DELETE FROM WC WHERE customer.id = :customerId")
                    .setParameter("customerId", customer.getId())
                    .executeUpdate();
            tx.commit();
            System.out.println("Antal biljetter borttagna för customer med id " + customer.getId() + ": " + deletedCount);
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        }
    }

}