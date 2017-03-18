package com.damienrubio.showcase.tennis.resource;

import com.damienrubio.showcase.tennis.model.Joueur;
import com.damienrubio.showcase.tennis.service.MatchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by damien on 21/11/2016.
 */
@Api(value = "/api/joueur",
     description = "Gestion des joueurs.")
@RestController
@RequestMapping("/api/joueur")
@Slf4j
public class JoueurResource {

    @Autowired
    private MatchService matchService;

    @ApiOperation(value = "Page d'accueil de l'API Joueur")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity home() {
        return ResponseEntity.ok("Joueur API");
    }

    @ApiOperation(value = "Créer un nouveau joueur",
                  notes = "Retourne un nouveau joueur",
                  response = Joueur.class)
    @ApiResponse(code = 201,
                 message = "Le joueur est créé")
    @PostMapping(value = "/creer",
                 consumes = MediaType.APPLICATION_JSON_VALUE,
                 produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity creer(@ApiParam("Nom du joueur à créer") @RequestParam("nom") String nom,
                                @ApiParam("Prénom du joueur à créer") @RequestParam("prenom") String prenom) {
        return ResponseEntity.ok(new Joueur(nom, prenom));
    }

    // FIXME @damien: remove joueur param and replace with DAO call
    @ApiOperation(value = "Afficher un joueur",
                  notes = "Affiche un joueur",
                  response = Joueur.class)
    @ApiResponse(code = 200,
                 message = "Le joueur est affiché")
    @PostMapping(value = "/{joueurId}",
                 consumes = MediaType.APPLICATION_JSON_VALUE,
                 produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity afficher(@ApiParam("Id du joueur à afficher") @PathVariable("joueurId") Long joueurId,
                                   @ApiParam("Joueur à afficher") Joueur joueur) {
        return ResponseEntity.ok(joueur);
    }

}
