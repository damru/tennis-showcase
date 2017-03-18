package com.damienrubio.showcase.tennis.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by damien on 18/11/2016.
 */
@Data
@Entity
public class Set {

    @Id
    @GeneratedValue
    private Long id;

    @Transient
    private static AtomicInteger nextId = new AtomicInteger();

    @OneToMany
    private List<Jeu> jeux;

    @OneToOne
    private Joueur vainqueur;

    private boolean enCours;

    public Set() {
        this.id = Long.valueOf(nextId.incrementAndGet());
        this.jeux = new ArrayList<Jeu>();
    }

    public Set(Joueur j1, Joueur j2) {
        this();
        Jeu jeu = new Jeu(j1, j2);
        jeu.setEnCours(true);
        this.addJeu(jeu);
    }

    public void addJeu(Jeu jeu) {
        this.jeux.add(jeu);
    }
}