package rs.ktech.CRUDAndRestApi.data;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

@Stateless
public class UserService implements Serializable {

    @PersistenceContext(unitName="userDatabase")
    private EntityManager em;

    public List<User> findAll() {
        Query query = em.createQuery("SELECT u FROM User u ");//+
                                             //"LEFT JOIN u.address a");
        return (List<User>) query.getResultList();
    }

}
