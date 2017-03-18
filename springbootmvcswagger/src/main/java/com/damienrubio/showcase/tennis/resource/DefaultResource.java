package com.damienrubio.showcase.tennis.resource;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by damien on 04/03/2017.
 */
@Api(value = "/api",
     description = "Kata Tennis API")
@RestController
@RequestMapping("/api")
public class DefaultResource {

    @ApiOperation(value = "Page d'accueil de l'API Kata Tennis")
    @GetMapping
    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity home() {
        return ResponseEntity.ok("Welcome to the Kata Tennis API");
    }
}
