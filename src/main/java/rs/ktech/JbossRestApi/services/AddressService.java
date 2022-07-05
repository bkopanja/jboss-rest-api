package rs.ktech.JbossRestApi.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJBTransactionRolledbackException;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import java.io.Serializable;
import java.util.List;

import rs.ktech.JbossRestApi.data.dao.AddressDAO;
import rs.ktech.JbossRestApi.data.entities.Address;

@Stateless
public class AddressService implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(AddressService.class);

    @Inject
    AddressDAO addressDAO;

    /**
     * Fetches the list of all {@link Address} from DB
     * @return list of {@link Address}
     */
    public List<Address> findAll() {
        return addressDAO.findAll();
    }


    /**
     * fetches a single {@link Address} from DB, if it wasn't found it will return an empty entity
     * @param addressId - long - the ID of {@link Address}
     * @return a single {@link Address}
     */
    public Address findOne(long addressId) {
        Address address;

        try {
            address = addressDAO.findOne(addressId);
        } catch (NoResultException | EJBTransactionRolledbackException e) {
            logger.info("No address found with ID: {}", addressId);
            address = new Address();
        }
        return address;
    }

    /**
     * Preservs an {@link Address} to DB (saves a new one or updates an existing one)
     * @param address - updated or new {@link Address} from DB
     */
    public void save(Address address) {
        addressDAO.save(address);
    }

    /**
     * deletes {@link Address} from DB
     * @param address - {@link Address} we want to remove from DB
     */
    public void delete(Address address) {
        if (address != null) {
            addressDAO.delete(address);
        }
    }
}
