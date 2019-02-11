package me.feelzor.faerunbattle.competences;

import me.feelzor.faerunbattle.model.Chateau;

public abstract class Competence {
    /**
     * @return Le coût d'utilisation de la compétence.
     */
    public abstract int getCout();
    private Chateau joueur;

    public Competence(Chateau joueur) {
        setJoueur(joueur);
    }

    protected Chateau getJoueur() {
        return joueur;
    }

    private void setJoueur(Chateau joueur) {
        this.joueur = joueur;
    }

    /**
     * Débute un nouveau tour
     */
    public void debutTour() {}

    /**
     * Active la compétence
     * @return true si la compétence a pu s'activer correctement.
     */
    public boolean activer() {
        if (getJoueur().getRessources() < this.getCout()) { return false; }

        getJoueur().consommerRessources(this.getCout());
        return true;
    }

    /**
     * @return true si la compétence est active
     */
    public boolean estActif() { return false; }
}
