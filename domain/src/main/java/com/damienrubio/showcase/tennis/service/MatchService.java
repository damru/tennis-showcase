package com.damienrubio.showcase.tennis.service;

import com.damienrubio.showcase.tennis.enums.Point;
import com.damienrubio.showcase.tennis.model.Jeu;
import com.damienrubio.showcase.tennis.model.Joueur;
import com.damienrubio.showcase.tennis.model.Match;
import com.damienrubio.showcase.tennis.model.Set;
import com.damienrubio.showcase.tennis.model.TieBreak;
import org.springframework.stereotype.Service;

/**
 * Created by damien on 17/11/2016.
 */
@Service
public class MatchService {

    /**
     * Récupère le set en cours.
     *
     * @param match
     * @return Set en cours
     */
    public Set getSetEnCours(Match match) {
        return match.getPartie().stream()
                .filter(set -> set.isEnCours())
                .findFirst()
                .get();
    }

    /**
     * Récupère le jeu en cours.
     *
     * @param match
     * @return Jeu en cours
     */
    public Jeu getJeuEnCours(Match match) {
        return getSetEnCours(match).getJeux().stream()
                .filter(jeu -> jeu.isEnCours())
                .findFirst()
                .get();
    }

    /**
     * Récupère le score courant d'un joueur.
     *
     * @param match
     * @param joueur
     * @return
     */
    public Point getScore(Match match, Joueur joueur) {
        return getJeuEnCours(match).getScores().get(joueur);
    }

    /**
     * Récupère le nombre de jeux gagnés par un joueur dans le set en cours.
     *
     * @param match
     * @param joueur
     * @return
     */
    public long getJeuxGagnes(Match match, Joueur joueur) {
        return getJeuxGagnes(getSetEnCours(match), joueur);
    }

    /**
     * Récupère le nombre de jeux gagnés par un joueur dans un set particulier.
     *
     * @param set
     * @param joueur
     * @return
     */
    public long getJeuxGagnes(Set set, Joueur joueur) {
        return set.getJeux().stream()
                .filter(jeu -> joueur.equals(jeu.getVainqueur()))
                .count();
    }

    /**
     * Récupère le nombre de sets gagnés par un joueur dans le match.
     *
     * @param match
     * @param joueur
     * @return
     */
    public long getSetsGagnes(Match match, Joueur joueur) {
        return match.getPartie().stream()
                .filter(jeu -> joueur.equals(jeu.getVainqueur()))
                .count();
    }

    /**
     * Lorsqu'un joueur marque un point, le match avance en fonction du score.
     *
     * @param match
     * @param joueur
     */
    public void marquerPoint(Match match, Joueur joueur) {
        Jeu jeuEnCours = getJeuEnCours(match);
        if (jeuEnCours instanceof TieBreak) {
            marquerPointTieBreak(match, joueur);
        } else {
            marquerPointJeu(match, joueur);
        }

    }

    /**
     * Lorsqu'un joueur marque un point, le match avance en fonction du score.
     *
     * @param match
     * @param joueur
     */
    private void marquerPointJeu(Match match, Joueur joueur) {
        Jeu jeuEnCours = getJeuEnCours(match);
        Joueur adversaire = match.getJoueur1().equals(joueur) ?
                match.getJoueur2()
                : match.getJoueur1();
        Point scoreJoueur = jeuEnCours.getScores().get(joueur);
        Point scoreAdversaire = jeuEnCours.getScores().get(adversaire);

        switch (scoreJoueur) {
            case NUL:
                scoreJoueur = Point.QUINZE;
                break;
            case QUINZE:
                scoreJoueur = Point.TRENTE;
                break;
            case TRENTE:
                if (Point.QUARANTE.equals(scoreAdversaire)) {
                    scoreJoueur = Point.DEUCE;
                    scoreAdversaire = Point.DEUCE;
                } else {
                    scoreJoueur = Point.QUARANTE;
                }
                break;
            case QUARANTE:
            case AVANTAGE:
                gagnerJeu(match, joueur);
                break;
            case DEUCE:
                scoreJoueur = Point.AVANTAGE;
                scoreAdversaire = Point.QUARANTE;
                break;
        }

        jeuEnCours.getScores().put(joueur, scoreJoueur);
        jeuEnCours.getScores().put(adversaire, scoreAdversaire);

    }

    /**
     * Lorsqu'un joueur marque un point, le match avance en fonction du score.
     *
     * @param match
     * @param joueur
     */
    private void marquerPointTieBreak(Match match, Joueur joueur) {
        TieBreak tieBreak = (TieBreak) getJeuEnCours(match);
        Joueur adversaire = match.getJoueur1().equals(joueur) ?
                match.getJoueur2()
                : match.getJoueur1();
        Integer scoreJoueur = (Integer) tieBreak.getScores().get(joueur);
        Integer scoreAdversaire = (Integer) tieBreak.getScores().get(adversaire);

        //Le joueur marque
        tieBreak.getScores().put(joueur, scoreJoueur += 1);
        // si le joueur a au moins 7 et 2 points de plus au moins, il gagne le tie break
        if (scoreJoueur >= 7
                && Math.abs(scoreJoueur - scoreAdversaire) >= 2) {
            gagnerJeu(match, joueur);
        }

    }

    /**
     * Lorsqu'un joueur gagne un jeu, le match avance en fonction du nombre de jeux gagnés.
     *
     * @param match
     * @param joueur
     */
    private void gagnerJeu(Match match, Joueur joueur) {
        // le jeu est gagné par le joueur
        Jeu jeuEnCours = getJeuEnCours(match);
        jeuEnCours.setVainqueur(joueur);
        jeuEnCours.setEnCours(false);

        // si le joueur a gagné au moins 6 jeux et a 2 jeux d'avance au moins, alors le set est gagné
        // si le jeu est un tie break, le set est également gagné
        if ((getJeuxGagnes(match, joueur) >= 6 && Math.abs(getJeuxGagnes(match, match.getJoueur1()) - getJeuxGagnes(match, match.getJoueur2())) >= 2)
                || jeuEnCours instanceof TieBreak) {
            gagnerSet(match, joueur);
        }
        // sinon
        else {
            // si il y a 6 jeux partout et si ce n'est pas le dernier set, alors on commence un jeu "tie-break"
            if (getJeuxGagnes(match, joueur) >= 6
                    && getJeuxGagnes(match, match.getJoueur1()) == getJeuxGagnes(match, match.getJoueur2())
                    && match.getNbSetsGagnants() > match.getPartie().size()) {
                TieBreak nouveauJeu = new TieBreak(match.getJoueur1(), match.getJoueur2());
                nouveauJeu.setEnCours(true);
                getSetEnCours(match).addJeu(nouveauJeu);
            }
            // sinon on commence un nouveau jeu
            else {
                Jeu nouveauJeu = new Jeu(match.getJoueur1(), match.getJoueur2());
                nouveauJeu.setEnCours(true);
                getSetEnCours(match).addJeu(nouveauJeu);
            }
        }

    }

    /**
     * Lorsqu'un joueur gagne un set, le match avance en fonction du nombre de sets gagnés.
     *
     * @param match
     * @param joueur
     */
    private void gagnerSet(Match match, Joueur joueur) {
        // le set en cours est gagné par le joueur
        Set setEnCours = getSetEnCours(match);
        setEnCours.setVainqueur(joueur);
        setEnCours.setEnCours(false);

        // si le nombre de sets gagnés par le joueur est >= au nombre de sets gagnants du match, alors le match est gagné
        if (getSetsGagnes(match, joueur) >= match.getNbSetsGagnants()) {
            gagnerMatch(match, joueur);
        }
        // sinon on commence un nouveau set
        else {
            Set set = new Set(match.getJoueur1(), match.getJoueur2());
            set.setEnCours(true);
            match.addSet(set);
        }
    }

    /**
     * Lorsqu'un joueur gagne le match, la partie s'arrête.
     *
     * @param match
     */
    private void gagnerMatch(Match match, Joueur joueur) {
        match.setVainqueur(joueur);
    }

}
