package rs.ktech.CRUDAndRestApi.mvc.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import rs.ktech.CRUDAndRestApi.mvc.models.AddressModel;
import rs.ktech.CRUDAndRestApi.mvc.models.UserModel;

@Path("/users")
public class UserController {
    @GET
    @Produces("application/json")
    public String getAllUsers() {

        List<UserModel> users = new ArrayList<UserModel>();
        Connection conn = null;

        try {
            InitialContext ctx = new InitialContext();
            DataSource ds = (DataSource)ctx.lookup("java:jboss/datasources/PostgresDS");
            conn = ds.getConnection();

            Statement st = conn.createStatement();

            String sql = "select u.*, a.street, a.zipcode, a.city, a.suite " +
                                 "from api_user u " +
                                 "left join address a on u.address_id = a.id";

            ResultSet rs = st.executeQuery(sql);

            while(rs.next()) {
                UserModel userModel = new UserModel();
                userModel.setId(rs.getInt("id"));
                userModel.setName(rs.getString("name"));
                userModel.setPhone(rs.getString("phone"));

                AddressModel addressModel = new AddressModel();
                addressModel.setId(rs.getInt("address_id"));
                addressModel.setStreet(rs.getString("street"));
                addressModel.setSuite(rs.getString("suite"));
                addressModel.setCity(rs.getString("city"));
                addressModel.setZipcode(rs.getString("zipcode"));

                userModel.setAddress(addressModel);

                users.add(userModel);
            }

        } catch (Exception ex) {
            // ... code to handle exceptions
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }

        Gson gson = new GsonBuilder()
                .serializeNulls()
                .create();

        return gson.toJson(users);
    }
}