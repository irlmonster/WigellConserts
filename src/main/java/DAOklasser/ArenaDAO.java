package DAOklasser;

import com.grp5.entitys.Arena;
import com.grp5.entitys.Customer;
import org.hibernate.Session;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import com.grp5.entitys.Concerts;
import java.util.List;




public class ArenaDAO {
    private final SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();


    public void saveArena(Arena arena) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(arena);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();     // rollback ångrar alla ändringar om något gått fel
            }
            e.printStackTrace();
        }
    }

    //READ
    // Hämta alla arenor
    public List<Arena> getAllArenas() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Arena", Arena.class).getResultList();
        }
    }



    public Arena getArenaByName(String name) {
        if (name == null) {
            return null;
        }

        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(
                            "FROM Arena a JOIN FETCH a.address ad WHERE a.name = :name", Arena.class)
                    .setParameter("name", name)
                    .uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public Arena getArenaByArenaName(String name) {
        if (name == null) {
            return null;
        }
        try (Session session = sessionFactory.openSession()) {
            // Använd JOIN FETCH för att hämta både Arena och Address
            return session.createQuery("FROM Arena a " +
                            "JOIN FETCH a.address ad " +
                            "WHERE a.name = :name", Arena.class)
                    .setParameter("name", name)
                    .uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //Update - uppdatera arena/info
    public void updateArena(Arena arena) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) { // session skapas och stängs automatiskt
            transaction = session.beginTransaction();
            session.update(arena);
            transaction.commit();
            System.out.println("Arenan har uppdaterats✅");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback(); // rollback fungerar fortfarande
            }
            e.printStackTrace();
        }
    }



    //Delete - ta bort arena
    public void deleteArena(Arena arena){
        Transaction transaction = null;
        try(Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            if (arena != null){
                session.delete(arena);
                System.out.println("Arenan med namnet " + arena + " har tagits bort!");
            } else {
                System.out.println("Fanns ingen arena att ta bort med namnet " + arena);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }





}
