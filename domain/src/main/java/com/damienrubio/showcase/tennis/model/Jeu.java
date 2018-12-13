package com.damienrubio.showcase.tennis.model;

import com.damienrubio.showcase.tennis.enums.Point;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by damien on 18/11/2016.
 */
@Data
@Entity
public class Jeu {

    @Id
    private Long id;

    @Transient
    private static AtomicInteger nextId = new AtomicInteger();

    @OneToMany
    private Map<Joueur, Point> scores;

    private boolean enCours;

    @OneToOne
    private Joueur vainqueur;

    public Jeu() {
        this.id = Long.valueOf(nextId.incrementAndGet());
        this.scores = new HashMap<Joueur, Point>();
    }

    public Jeu(Joueur joueur1, Joueur joueur2) {
        this();
        this.addScore(joueur1, Point.NUL);
        this.addScore(joueur2, Point.NUL);
    }

    public Map<Joueur, Point> getScores() {
        return this.scores;
    }

    public void setScores(Map<Joueur, Point> scores) {
        this.scores = scores;
    }

    public void addScore(Joueur jouer, Point point) {
        this.scores.put(jouer, point);
    }
}
