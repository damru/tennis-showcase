package com.damienrubio.showcase.tennis.model;

import lombok.Data;

import javax.persistence.Entity;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by damien on 18/11/2016.
 */
@Data
@Entity
public class TieBreak extends Jeu {

    private Map<Joueur, Integer> scores;

    public TieBreak() {
        this.scores = new HashMap<Joueur, Integer>();
    }

    public TieBreak(Joueur joueur1, Joueur joueur2) {
        this();
        this.addScore(joueur1, 0);
        this.addScore(joueur2, 0);
    }

    public void setScores(Map<Joueur, Integer> scores) {
        this.scores = scores;
    }

    public Map<Joueur, Integer> getScores() {
        return this.scores;
    }

    public void addScore(Joueur jouer, Integer point) {
        this.scores.put(jouer, point);
    }

}
