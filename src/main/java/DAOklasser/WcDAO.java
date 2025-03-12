package DAOklasser;

import com.grp5.entitys.Concerts;
import com.grp5.entitys.Customer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import com.grp5.entitys.WC;
import org.hibernate.cfg.Configuration;


public class WcDAO {

    private static final SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();


    // Skapar en biljett
    public void createTicketWC1(WC wc) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.save(wc); // Hibernate save() för att spara objektet
            tx.commit();
            System.out.println("sparas med wcdao: " + wc);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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


            session.save(wc);
            tx.commit();
            System.out.println("Biljett sparad: " + wc);



        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }




}