package rs.ktech.JbossRestApi.data.dao;

import org.hibernate.Session;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

import rs.ktech.JbossRestApi.data.entities.Address;

@Stateless
public class AddressDAO {

    @PersistenceContext(unitName = "PostgresDS")
    private EntityManager em;

    /**
     * Fetches the list of all {@link Address} from DB
     * @return list of {@link Address}
     */
    public List<Address> findAll() {
        Query query = em.createQuery("SELECT a FROM Address a");
        return (List<Address>) query.getResultList();
    }

    /**
     * fetches a single {@link Address} from DB
     * @param addressId - long - the ID of {@link Address}
     * @return a single {@link Address}
     */
    public Address findOne(long addressId) {
        Query query = em.createQuery("SELECT a FROM Address a where a.id = :addressId");
        query.setParameter("addressId", addressId);
        return (Address) query.getSingleResult();
    }

    /**
     * Preservs an {@link Address} to DB (saves a new one or updates an existing one)
     * @param address - updated or new {@link Address} from DB
     */
    public void save(Address address) {
        Session session = em.unwrap(Session.class);
        session.saveOrUpdate(address);
    }

    /**
     * deletes {@link Address} from DB
     * @param address - {@link Address} we want to remove from DB
     */
    public void delete(Address address) {
        em.remove(em.contains(address) ? address : em.merge(address));
    }

}
