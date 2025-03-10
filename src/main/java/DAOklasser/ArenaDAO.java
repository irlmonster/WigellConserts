package DAOklasser;

import com.grp5.entitys.Arena;
import org.hibernate.Session;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import com.grp5.entitys.Concerts;
import java.util.List;




public class ArenaDAO {
    private static SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

    //READ
    // Hämta alla arenor
    public List<Arena> getAllArenas() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Arena", Arena.class).getResultList();
        }
    }

    //Hämta en arena (name)
    public Arena getArenaByName(String name) {
        if (name == null) {
            return null;
        }

        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Arena WHERE name = :name", Arena.class)
                    .setParameter("name", name)
                    .uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }






}
