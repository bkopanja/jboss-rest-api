package rs.ktech.JbossRestApi.mvc.controllers;

import com.google.gson.Gson;

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
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import rs.ktech.JbossRestApi.services.ExternalApiUserService;
import rs.ktech.JbossRestApi.mvc.models.UserModel;

@Path("/import-update-external-users")
@Api(value = "External User API")
public class ExternalApiUserRestController {

    private static final String BASE_URL = "https://jsonplaceholder.typicode.com/";
    private static final Logger logger = LoggerFactory.getLogger(ExternalApiUserRestController.class);

    @Inject
    private ExternalApiUserService externalApiUserService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Returns list of imported users", notes = "Returns JSON", response = String.class)
    public String importExternalUsers() {

        List<UserModel> response= new ArrayList<>();

        RestTemplate restTemplate = new RestTemplate();

        URI requestUri = URI.create(BASE_URL).resolve("users");
        ParameterizedTypeReference<java.util.List<UserModel>> returnType = new ParameterizedTypeReference<List<UserModel>>() {};
        ResponseEntity<List<UserModel>> userResponse = restTemplate.exchange(requestUri, HttpMethod.GET,
                null, returnType);

        if (userResponse.getStatusCode().is2xxSuccessful()) {
            if (!CollectionUtils.isEmpty(userResponse.getBody())) {
                logger.info("Successfully fetched {} users", userResponse.getBody().size());
                response = externalApiUserService.updateInsertFromExternalApi(userResponse.getBody());
            }
        } else {
            logger.warn("Failed getting users from external API, the error code is {}", userResponse.getStatusCode());
        }

        logger.info("Fetched 0 users, returning an empty array");

        return new Gson().toJson(response);
    }
}