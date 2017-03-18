package com.damienrubio.showcase.tennis.model;

import lombok.Data;
import lombok.NonNull;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by damien on 18/11/2016.
 */

@Data
@Entity
public class Joueur {

    @Id
    @GeneratedValue
    private Long id;

    @Transient
    private static AtomicInteger nextId = new AtomicInteger();

    @NonNull
    private String nom;

    @NonNull
    private String prenom;

    public Joueur() {
        this.id = Long.valueOf(nextId.incrementAndGet());
    }

    public Joueur(String nom, String prenom) {
        this();
        this.nom = nom;
        this.prenom = prenom;
    }

}
