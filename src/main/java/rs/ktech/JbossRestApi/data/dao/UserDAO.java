package rs.ktech.JbossRestApi.data.dao;

import org.hibernate.Session;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

import rs.ktech.JbossRestApi.data.entities.Address;
import rs.ktech.JbossRestApi.data.entities.User;

@Stateless
public class UserDAO {

    @PersistenceContext(unitName = "PostgresDS")
    private EntityManager em;

    /**
     * Fetches the list of all {@link User} from DB
     * @return list of {@link User}
     */
    public List<User> findAll() {
        Query query = em.createQuery("SELECT u FROM User u");
        return (List<User>) query.getResultList();
    }

    /**
     * fetches a single {@link User} from DB
     * @param userId - long - the ID of {@link User}
     * @return a single {@link User}
     */
    public User findOne(long userId) {
        Query query = em.createQuery("SELECT u FROM User u where u.id = :userId");
        query.setParameter("userId", userId);
        return (User) query.getSingleResult();
    }

    /**
     * Fetches a list of {@link User} based on a list of IDs from an external system
     * @param extIds - list of long - IDs from an external system
     * @return a list of {@link User}
     */
    public List<User> findAllByExtId(List<Long> extIds) {
        Query query = em.createQuery("SELECT u FROM User u WHERE u.extId IN (:extIds)");
        query.setParameter("extIds", extIds);
        return (List<User>) query.getResultList();
    }

    /**
     * Preservs an {@link User} to DB (saves a new one or updates an existing one)
     * @param user - updated or new {@link User} from DB
     */
    public void save(User user) {
        Session session = em.unwrap(Session.class);
        session.saveOrUpdate(user);
    }

    /**
     * deletes {@link User} from DB
     * @param user - {@link User} we want to remove from DB
     */
    public void delete(User user) {
        em.remove(em.contains(user) ? user : em.merge(user));
    }
}
