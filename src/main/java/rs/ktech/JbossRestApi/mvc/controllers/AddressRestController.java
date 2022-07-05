package rs.ktech.JbossRestApi.mvc.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.persistence.NoResultException;
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
import rs.ktech.JbossRestApi.data.entities.Address;
import rs.ktech.JbossRestApi.data.entities.User;
import rs.ktech.JbossRestApi.mvc.models.AddressModel;
import rs.ktech.JbossRestApi.services.AddressService;

@Path("/address")
@Api(value = "Address related REST endpoints")
public class AddressRestController {

    private static Logger logger = LoggerFactory.getLogger(AddressRestController.class);

    @Inject
    AddressService addressService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Returns a list of all addresses in our DB",
            notes = "Returns JSON",
            response = String.class)
    public String getAllAddresses() {

        List<AddressModel> addressModels = new ArrayList<AddressModel>();
        List<Address> addresses = addressService.findAll();

        addresses.forEach(address -> addressModels.add(Address.toAddressModel(address)));

        Gson gson = new GsonBuilder()
                            .serializeNulls()
                            .create();

        return gson.toJson(addressModels);
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Returns a single Address if it exists in the DB or empty JSON if it wasn't found",
            notes = "Returns JSON",
            response = String.class)
    public String getAddress(@PathParam("id") long id) {

        Address address = addressService.findOne(id);;

        Gson gson = new GsonBuilder()
                            .serializeNulls()
                            .create();

        return gson.toJson(Address.toAddressModel(address));
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Returns an updated version of the address if it was found in DB or empty JSON if it wasn't found",
            notes = "Returns JSON",
            response = String.class)
    public String updateAddress(@NotNull @FormParam("id") long id,
            @FormParam("street") String street,
            @FormParam("city") String city,
            @FormParam("suite") String suite,
            @FormParam("zipcode") String zipcode) {

        Address address = addressService.findOne(id);

        if(!StringUtils.isEmpty(city)) {
            address.setCity(city);
        }

        if(!StringUtils.isEmpty(street)) {
            address.setStreet(street);
        }

        if(!StringUtils.isEmpty(suite)) {
            address.setSuite(suite);
        }

        if(!StringUtils.isEmpty(zipcode)) {
            address.setZipcode(zipcode);
        }

        addressService.save(address);

        Gson gson = new GsonBuilder()
                            .serializeNulls()
                            .create();

        return gson.toJson(Address.toAddressModel(address));
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Deletes the address if it was found in DB")
    public void deleteAddress(@PathParam("id") long id) {
        addressService.delete(addressService.findOne(id));
    }
}