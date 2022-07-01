package rs.ktech.CRUDAndRestApi.data;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Stateless
public class UserDAO {

    @PersistenceContext(unitName="userDatabase")
    private EntityManager em;

    public List<User> findAll() {
        Query query = em.createQuery("SELECT u FROM User u ");
        return (List<User>) query.getResultList();
    }

    public List<User> findAllByExtId(List<Long> extIds) {
        Query query = em.createQuery("SELECT u FROM User u WHERE u.extId IN (:extIds)");
        query.setParameter("extIds", extIds);
        return (List<User>) query.getResultList();
    }

    public void save(User user) {
        em.persist(user);
    }

}
