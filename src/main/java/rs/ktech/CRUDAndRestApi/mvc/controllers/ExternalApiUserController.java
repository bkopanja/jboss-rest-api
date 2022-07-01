package rs.ktech.CRUDAndRestApi.mvc.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.awt.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import rs.ktech.CRUDAndRestApi.data.UserService;
import rs.ktech.CRUDAndRestApi.mvc.models.UserModel;

@Path("/import-update-external-users")
public class ExternalApiUserController {

    private static final String BASE_URL = "https://jsonplaceholder.typicode.com/";
    private static final Logger logger = LoggerFactory.getLogger(ExternalApiUserController.class);

    @Inject
    private UserService userService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String importExternalUsers() {

        List<UserModel> response= new ArrayList<>();

        RestTemplate restTemplate = new RestTemplate();

        URI requestUri = URI.create(BASE_URL).resolve("users");
        ParameterizedTypeReference<java.util.List<UserModel>> returnType = new ParameterizedTypeReference<List<UserModel>>() {};
        ResponseEntity<List<UserModel>> userResponse = restTemplate.exchange(requestUri, HttpMethod.GET,
                null, returnType);

        if (userResponse.getStatusCode().is2xxSuccessful()) {
            if (!CollectionUtils.isEmpty(userResponse.getBody())) {
                logger.info("Finished fetching users with HTTP 200, number of users fetched {}",
                        userResponse.getBody().size());
                response = userService.updateInsertFromExternalApi(userResponse.getBody());
            }
        } else {
            logger.warn("Failed getting users from external API, error code is {}", userResponse.getStatusCode());
        }

        logger.info("Fetched 0 users, returning empty array");

        return new Gson().toJson(response);
    }
}