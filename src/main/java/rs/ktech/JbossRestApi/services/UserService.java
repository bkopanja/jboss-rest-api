package rs.ktech.JbossRestApi.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJBTransactionRolledbackException;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import java.io.Serializable;
import java.util.List;

import rs.ktech.JbossRestApi.data.dao.UserDAO;
import rs.ktech.JbossRestApi.data.entities.User;

@Stateless
public class UserService implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Inject
    UserDAO userDAO;

    /**
     * Fetches the list of all {@link User} from DB
     * @return list of {@link User}
     */
    public List<User> findAll() {
        return userDAO.findAll();
    }

    /**
     * fetches a single {@link User} from DB, if it wasn't found it will return an empty entity
     * @param userId - long - the ID of {@link User}
     * @return a single {@link User}
     */
    public User findOne(long userId) {
        User user;
        try {
            user = userDAO.findOne(userId);
        } catch (NoResultException | EJBTransactionRolledbackException e) {
            logger.info("No user found with ID: {}", userId);
            user = new User();
        }
        return user;
    }

    /**
     * Preservs an {@link User} to DB (saves a new one or updates an existing one)
     * @param user - updated or new {@link User} from DB
     */
    public void save(User user) {
        userDAO.save(user);
    }

    /**
     * deletes {@link User} from DB
     * @param user - {@link User} we want to remove from DB
     */
    public void delete(User user) {
        if (user != null) {
            userDAO.delete(user);
        }
    }
}
