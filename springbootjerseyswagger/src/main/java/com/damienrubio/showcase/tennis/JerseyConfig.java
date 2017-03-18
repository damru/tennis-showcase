package com.damienrubio.showcase.tennis;

import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.jaxrs.listing.ApiListingResource;
import io.swagger.jaxrs.listing.SwaggerSerializers;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * Created by damien on 04/03/2017.
 */
@Configuration
public class JerseyConfig extends ResourceConfig {

    @Value("${spring.jersey.application-path:/}")
    private String apiPath;

    public JerseyConfig() {
        this.registerEndpoints();
    }

    private void registerEndpoints() {
        packages(getClass().getPackage().getName() + ".resource");
    }

    @PostConstruct
    public void init() {
        // Register components where DI is needed
        this.configureSwagger();
    }

    private void configureSwagger() {
        // Available at localhost:port/swagger.json
        this.register(ApiListingResource.class);
        this.register(SwaggerSerializers.class);

        BeanConfig config = new BeanConfig();
        config.setConfigId("springboot-jersey-swagger");
        config.setTitle("Spring Boot + Jersey + Swagger");
        config.setVersion("v1");
        config.setContact("Damien Rubio");
        config.setSchemes(new String[]{"http",
                                       "https"});
        config.setBasePath(this.apiPath);
        config.setResourcePackage(this.getClass().getPackage().getName()+".resource");
        config.setPrettyPrint(true);
        config.setScan(true);
    }
}
