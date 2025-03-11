package DAOklasser;

import com.grp5.entitys.Addresses;
import com.grp5.entitys.Concerts;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class AddressDAO {

    private static final SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

    // CREATE - lägg till en ny konsert
    public void saveAddress(Addresses addresses) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(addresses);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();     // rollback ångrar alla ändringar om något gått fel
            }
            e.printStackTrace();
        }
    }

    // READ
    // Hämta alla Addresser
    public Addresses getAddressByArena(String arenaName) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(
                            "SELECT ad FROM Arena a " +
                                    "JOIN a.address ad " + // Koppling till address
                                    "WHERE a.name = :arena_name", Addresses.class)
                    .setParameter("arena_name", arenaName)
                    .uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Ingen arena hittades med det namnet.");
            return null; // returnerar null om ingen arena hittades
        }
    }


    //UPDATE
    public void updateAddress(Addresses addresses) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.update(addresses);
            transaction.commit();
            System.out.println("Adressen har sparats");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();     // rollback ångrar alla ändringar om något gått fel
            }
            e.printStackTrace();
        }
    }


    // DELETE
    public void deleteAddress(Addresses addresses) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            if (addresses != null) {
                session.delete(addresses);
                System.out.println("Adressen " + addresses + " har tagits bort!");
            } else {
                System.out.println("Hittade inte denna adress:  " + addresses + ".");
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
