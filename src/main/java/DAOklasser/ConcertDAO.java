package DAOklasser;


import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import com.grp5.entitys.Concerts;
import java.util.List;

public class ConcertDAO {
    private static final SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }



    // CREATE - lägg till en ny konsert
    public void saveConcert(Concerts concert) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(concert);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();     // rollback ångrar alla ändringar om något gått fel
            }
            e.printStackTrace();
        }
    }

    // READ
    // Hämta alla konserter
    public List<Concerts> getAllConcerts() {
        try (Session session = sessionFactory.openSession()) {
         return session.createQuery("FROM Concerts", Concerts.class).getResultList();
        }
    }
    //Hämta en konsert (artist_name)
    public Concerts getConcertByArtist(String artistName) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(
                            "FROM Concerts c " +
                                    "JOIN FETCH c.arena a " +  // Hämta arenan samtidigt
                                    "JOIN FETCH a.address ad " + // Hämta adressen samtidigt
                                    "WHERE c.artist_name = :artist_name", Concerts.class)
                    .setParameter("artist_name", artistName)
                    .uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Ingen konsert hittades");
            return null; // returnerar null om ingen konsert hittades
        }
    }

    // Hämta en konsert baserat på concertId
    public Concerts getConcertById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(
                            "FROM Concerts c JOIN FETCH c.arena a JOIN FETCH a.address ad WHERE c.id = :id", Concerts.class)
                    .setParameter("id", id)
                    .uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // kollar hur många konserter som är bokade på en arena
    // I ConcertDAO
    public long countConcertsForArena(int arenaId) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("SELECT COUNT(c) FROM Concerts c WHERE c.arena.id = :arenaId", Long.class)
                    .setParameter("arenaId", arenaId)
                    .uniqueResult();
        }
    }


    //UPDATE
    public void updateConcerts(Concerts concert) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.update(concert);
            transaction.commit();
            System.out.println("Konserten har sparats");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();     // rollback ångrar alla ändringar om något gått fel
            }
            e.printStackTrace();
        }
    }


    //DELETE
    public void deleteConcerts(Concerts concert) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            if (concert != null) {
                session.delete(concert);
                System.out.println("Konserten med namn " + concert + " har tagits bort!");
            } else {
                System.out.println("Hittade ingen konsert med namnet " + concert + ".");
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