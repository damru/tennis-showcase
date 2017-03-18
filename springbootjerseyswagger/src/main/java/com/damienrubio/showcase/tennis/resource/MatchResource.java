package com.damienrubio.showcase.tennis.resource;

import com.damienrubio.showcase.tennis.model.Joueur;
import com.damienrubio.showcase.tennis.model.Match;
import com.damienrubio.showcase.tennis.service.MatchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;

/**
 * Created by damien on 21/11/2016.
 */
@Component
@Api(value = "/match",
     description = "Gestion des matchs.")
@Path("/match")
@Slf4j
public class MatchResource {

    @Autowired
    private MatchService matchService;

    @ApiOperation(value = "Page d'accueil de l'API Match")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response home() {
        return Response.ok("Match API").build();
    }

    @ApiOperation(value = "Créer un nouveau match",
                  notes = "Retourne un nouveau match",
                  response = Match.class)
    @ApiResponse(code = 201,
                 message = "Le match est créé")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path(value = "/creer")
    public Response creer(@ApiParam("Les joueurs du match à créer") Map<String,Joueur> joueurs,
                          @ApiParam("Nombre de sets nécessaires pour gagner le match") @QueryParam("nbSetsGagants") @DefaultValue("2")
                              int nbSetsGagants) {
        if (joueurs != null && joueurs.containsKey("joueur1") && joueurs.containsKey("joueur2")) {
            return Response.ok(new Match(joueurs.get("joueur1"), joueurs.get("joueur2"))).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).entity(joueurs).build();
    }

    // FIXME @damien: remove match param and replace with DAO call
    @ApiOperation(value = "Afficher un match",
                  notes = "Affiche un match",
                  response = Match.class)
    @ApiResponse(code = 200,
                 message = "Le match est créé")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path(value = "/{matchId}")
    public Response afficher(@ApiParam("Id du match à afficher") @PathParam("matchId") Long matchId,
                             @ApiParam("Match à afficher") Match match) {
        return Response.ok(match).build();
    }

    @ApiOperation(value = "Un joueur marque un point dans le match",
                  notes = "Le joueur marque",
                  response = Match.class)
    @ApiResponses(value = {@ApiResponse(code = 200,
                                        message = "Le joueur a marqué un point"),
                           @ApiResponse(code = 400,
                                        message = "Erreur dans les données d'entrée")})
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path(value = "/marquer/{joueurId}")
    public Response marquer(@ApiParam("Match dans lequel un point est marqué") Match match,
                            @ApiParam("Joueur marquant le point") @PathParam("joueurId") Long joueurId) {
        Joueur joueurQuiMarque = null;
        switch (joueurId.intValue()) {
            case 1:
                joueurQuiMarque = match.getJoueur1();
                break;
            case 2:
                joueurQuiMarque = match.getJoueur2();
        }
        if (joueurQuiMarque != null) {
            matchService.marquerPoint(match, joueurQuiMarque);
            return Response.ok(match).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).entity(joueurId).build();
    }

}
