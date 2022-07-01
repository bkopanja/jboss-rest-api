package rs.ktech.CRUDAndRestApi.mvc.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import rs.ktech.CRUDAndRestApi.data.User;
import rs.ktech.CRUDAndRestApi.data.UserDAO;
import rs.ktech.CRUDAndRestApi.mvc.models.AddressModel;
import rs.ktech.CRUDAndRestApi.mvc.models.UserModel;

@Path("/users")
public class UserController {

    @Inject
    UserDAO userDAO;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllUsers() {

        List<UserModel> users = new ArrayList<UserModel>();
        Connection conn = null;

        List<User> apiUsers = userDAO.findAll();

        for (User user : apiUsers) {
            UserModel userModel = new UserModel();
            userModel.setId(user.getId());
            userModel.setName(user.getName());
            userModel.setPhone(user.getPhone());

            AddressModel addressModel = new AddressModel();
            addressModel.setId(user.getAddress().getId());
            addressModel.setStreet(user.getAddress().getStreet());
            addressModel.setSuite(user.getAddress().getSuite());
            addressModel.setCity(user.getAddress().getCity());
            addressModel.setZipcode(user.getAddress().getZipcode());

            userModel.setAddress(addressModel);

            users.add(userModel);
        }

        Gson gson = new GsonBuilder()
                            .serializeNulls()
                            .create();

        return gson.toJson(users);
    }
}