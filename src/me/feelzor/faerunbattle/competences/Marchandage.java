package me.feelzor.faerunbattle.competences;

import me.feelzor.faerunbattle.model.Chateau;

public class Marchandage extends Competence {
    @Override
    public int getCout() {
        return 5 * getJoueur().getTauxRessources() + (int) Math.pow(getJoueur().getTauxRessources() - 1, 1.3);
    }

    public Marchandage(Chateau joueur) {
        super(joueur);
    }

    @Override
    public boolean activer() {
        if (!super.activer()) { return false; }

        getJoueur().augmenterTauxRessources();
        return true;
    }
}
