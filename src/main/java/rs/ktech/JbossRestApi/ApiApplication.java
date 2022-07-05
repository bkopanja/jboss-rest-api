package rs.ktech.JbossRestApi;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import io.swagger.jaxrs.config.BeanConfig;
import rs.ktech.JbossRestApi.mvc.controllers.ExternalApiUserRestController;

@ApplicationPath("/api")
public class ApiApplication extends Application {

    public ApiApplication() {
        init();
    }


    private void init() {

        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setVersion("1.0.0");
        beanConfig.setSchemes(new String[]{"http"});
        beanConfig.setHost("java.k-tech.rs:8080");
        beanConfig.setBasePath("/api");
        beanConfig.setResourcePackage(ExternalApiUserRestController.class.getPackage().getName());
        beanConfig.setTitle("JbossRestApi Documentation");
        beanConfig.setDescription("Sample REST API built for the ZCAM assignment");
        beanConfig.setScan(true);
    }

}