package com.damienrubio.showcase.tennis.resource;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 * Created by damien on 04/03/2017.
 */
@Api(value = "/",
     description = "Kata Tennis API")
@Path("/")
public class DefaultResource {

    @ApiOperation(value = "Page d'accueil de l'API Kata Tennis")
    @GET
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    public Response home() {
        return Response.ok("Kata Tennis API").build();
    }
}
