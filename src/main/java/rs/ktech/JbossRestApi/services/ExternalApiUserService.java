package rs.ktech.JbossRestApi.services;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import rs.ktech.JbossRestApi.data.entities.Address;
import rs.ktech.JbossRestApi.data.entities.User;
import rs.ktech.JbossRestApi.data.dao.UserDAO;
import rs.ktech.JbossRestApi.mvc.models.UserModel;

@Stateless
public class ExternalApiUserService implements Serializable {

    @Inject
    UserDAO userDAO;

    /**
     * Fetches a list of users from an external system and preserves them in our DB
     * If users already exist in our DB it will update them instead
     * @param userModels
     * @return
     */
    public List<UserModel> updateInsertFromExternalApi(List<UserModel> userModels) {
        List<Long> userIds = userModels.stream().map(UserModel::getId).collect(Collectors.toList());
        List<User> allUserForIds = userDAO.findAllByExtId(userIds);
        List<UserModel> models = new ArrayList<>();

        Map<Long, User> userMap = allUserForIds.stream().collect(Collectors.toMap(User::getExtId, user -> user));
        userModels.forEach(um -> {
            User existing = userMap.remove(um.getId());
            if (existing != null) {
                existing.setExtId(um.getId());
                existing.setName(um.getName());
                existing.setPhone(um.getPhone());
                existing.setWebsite(um.getWebsite());
                existing.getAddress().setCity(um.getAddress().getCity());
                existing.getAddress().setSuite(um.getAddress().getSuite());
                existing.getAddress().setStreet(um.getAddress().getStreet());
                existing.getAddress().setZipcode(um.getAddress().getZipcode());
                userDAO.save(existing);
                models.add(User.toUserModel(existing));
            } else {
                User user = new User();
                user.setExtId(um.getId());
                user.setName(um.getName());
                user.setPhone(um.getPhone());
                user.setWebsite(um.getWebsite());
                Address address = Address.fromAddressModel(um.getAddress());
                address.setUser(user);
                user.setAddress(address);
                userDAO.save(user);
                models.add(User.toUserModel(user));
            }
        });

        return models;
    }

}
