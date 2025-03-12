package DAOklasser;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import com.grp5.entitys.WC;
import org.hibernate.cfg.Configuration;


public class WcDAO {

    private static final SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();


    // Skapar en biljett
    public void createTicketWC(WC wc) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.save(wc); // Hibernate save() för att spara objektet
            tx.commit();
            System.out.println("sparas med wcdao: " + wc);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}