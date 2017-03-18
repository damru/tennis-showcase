package com.damienrubio.showcase.tennis.resource;

import com.damienrubio.showcase.tennis.model.Joueur;
import com.damienrubio.showcase.tennis.service.MatchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by damien on 21/11/2016.
 */
@Component
@Api(value = "/joueur",
     description = "Gestion des joueurs.")
@Path("/joueur")
@Slf4j
public class JoueurResource {

    @Autowired
    private MatchService matchService;

    @ApiOperation(value = "Page d'accueil de l'API Joueur")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response home() {
        return Response.ok("Joueur API").build();
    }

    @ApiOperation(value = "Créer un nouveau joueur",
                  notes = "Retourne un nouveau joueur",
                  response = Joueur.class)
    @ApiResponse(code = 201,
                 message = "Le joueur est créé")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/creer")
    public Response creer(@ApiParam("Nom du joueur à créer") @QueryParam("nom") String nom,
                          @ApiParam("Prénom du joueur à créer") @QueryParam("prenom") String prenom) {
        return Response.ok(new Joueur(nom, prenom)).build();
    }

    // FIXME @damien: remove joueur param and replace with DAO call
    @ApiOperation(value = "Afficher un joueur",
                  notes = "Affiche un joueur",
                  response = Joueur.class)
    @ApiResponse(code = 200,
                 message = "Le joueur est affiché")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{joueurId}")
    public Response afficher(@ApiParam("Id du joueur à afficher") @PathParam("joueurId") Long joueurId,
                             @ApiParam("Joueur à afficher") Joueur joueur) {
        return Response.ok(joueur).build();
    }

}
