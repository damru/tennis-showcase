package com.damienrubio.showcase.tennis.model;

import lombok.Data;
import lombok.NonNull;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by damien on 18/11/2016.
 */
@Data
@Entity
public class Match {

    @Id
    @GeneratedValue
    private Long id;

    @Transient
    private static AtomicInteger nextId = new AtomicInteger();

    @OneToMany
    private ArrayList<Set> partie;

    @OneToOne
    @NonNull
    private Joueur joueur1;

    @OneToOne
    @NonNull
    private Joueur joueur2;

    @OneToOne
    private Joueur vainqueur;

    int nbSetsGagnants = 2;

    public Match() {
        this.id = Long.valueOf(nextId.incrementAndGet());
    }

    public Match(Joueur j1, Joueur j2) {
        this();
        this.partie = new ArrayList<Set>();
        this.joueur1 = j1;
        this.joueur2 = j2;
        Set set = new Set(j1, j2);
        set.setEnCours(true);
        this.addSet(set);
    }

    public Match(Joueur j1, Joueur j2, int nbSetsGagnants) {
        this(j1, j2);
        this.nbSetsGagnants = nbSetsGagnants;
    }

    public void addSet(Set set) {
        this.partie.add(set);
    }
}
