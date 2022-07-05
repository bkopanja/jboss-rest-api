package rs.ktech.JbossRestApi.mvc.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import rs.ktech.JbossRestApi.data.entities.User;
import rs.ktech.JbossRestApi.mvc.models.UserModel;
import rs.ktech.JbossRestApi.services.UserService;

@Path("/user")
@Api(value = "Internal User related REST endpoints")
public class UserRestController {

    @Inject
    UserService userService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Returns a list of all users in our DB",
            notes = "Returns JSON",
            response = String.class)
    public String getAllUsers() {

        List<UserModel> users = new ArrayList<UserModel>();
        List<User> apiUsers = userService.findAll();

        apiUsers.forEach(user -> users.add(User.toUserModel(user)));

        Gson gson = new GsonBuilder()
                            .serializeNulls()
                            .create();

        return gson.toJson(users);
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Returns a single users if he/she exists in the DB or empty JSON if user wasn't found",
            notes = "Returns JSON",
            response = String.class)
    public String getUser(@PathParam("id") long id) {

        User apiUser = userService.findOne(id);

        if(apiUser == null) {
            apiUser = new User();
        }

        Gson gson = new GsonBuilder()
                            .serializeNulls()
                            .create();

        return gson.toJson(User.toUserModel(apiUser));
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Returns an updated version of the user if it was found in DB or empty JSON if user wasn't found",
            notes = "Returns JSON",
            response = String.class)
    public String updateUser(@NotNull @FormParam("id") long id,
            @FormParam("name") String name,
            @FormParam("phone") String phone,
            @FormParam("website") String website) {

        User apiUser = userService.findOne(id);

        apiUser.setName(name);
        apiUser.setPhone(phone);
        apiUser.setWebsite(website);

        userService.save(apiUser);

        Gson gson = new GsonBuilder()
                            .serializeNulls()
                            .create();

        return gson.toJson(User.toUserModel(apiUser));
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Deletes the user if it was found in DB")
    public void deleteUser(@PathParam("id") long id) {
        userService.delete(userService.findOne(id));
    }
}