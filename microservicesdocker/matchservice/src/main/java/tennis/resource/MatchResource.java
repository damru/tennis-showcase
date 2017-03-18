package tennis.resource;

import com.damienrubio.kata.tennis.model.Joueur;
import com.damienrubio.kata.tennis.model.Match;
import com.damienrubio.kata.tennis.service.MatchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by damien on 21/11/2016.
 */
@Api(value = "/api/match",
     description = "Gestion des matchs.")
@RestController
@RequestMapping("/api/match")
@Slf4j
public class MatchResource {

    @Autowired
    private MatchService matchService;

    @ApiOperation(value = "Page d'accueil de l'API Match")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity home() {
        return ResponseEntity.ok("Match API");
    }

    @ApiOperation(value = "Créer un nouveau match",
                  notes = "Retourne un nouveau match",
                  response = Match.class)
    @ApiResponse(code = 201,
                 message = "Le match est créé")
    @PostMapping(value = "/creer",
                 consumes = MediaType.APPLICATION_JSON_VALUE,
                 produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity creer(@ApiParam("Les joueurs du match à créer") Map<String, Joueur> joueurs,
                                @ApiParam("Nombre de sets nécessaires pour gagner le match") @RequestParam(value = "nbSetsGagants",
                                                                                                           defaultValue = "2")
                                    int nbSetsGagants) {
        if (joueurs != null && joueurs.containsKey("joueur1") && joueurs.containsKey("joueur2")) {
            return ResponseEntity.ok(new Match(joueurs.get("joueur1"), joueurs.get("joueur2")));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(joueurs);
    }

    // FIXME @damien: remove match param and replace with DAO call
    @ApiOperation(value = "Afficher un match",
                  notes = "Affiche un match",
                  response = Match.class)
    @ApiResponse(code = 200,
                 message = "Le match est créé")
    @PostMapping(value = "/{matchId}",
                 produces = MediaType.APPLICATION_JSON_VALUE,
                 consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity afficher(@ApiParam("Id du match à afficher") @RequestParam("matchId") Long matchId,
                                   @ApiParam("Match à afficher") Match match) {
        return ResponseEntity.ok(match);
    }

    @ApiOperation(value = "Un joueur marque un point dans le match",
                  notes = "Le joueur marque",
                  response = Match.class)
    @ApiResponses(value = {@ApiResponse(code = 200,
                                        message = "Le joueur a marqué un point"),
                           @ApiResponse(code = 400,
                                        message = "Erreur dans les données d'entrée")})
    @PostMapping(value = "/marquer/{joueurId}",
                 produces = MediaType.APPLICATION_JSON_VALUE,
                 consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity marquer(@ApiParam("Match dans lequel un point est marqué") Match match,
                                  @ApiParam("Joueur marquant le point") @RequestParam("joueurId") Long joueurId) {
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
            return ResponseEntity.ok(match);
        }
        return ResponseEntity.badRequest().body(joueurId);
    }

}
