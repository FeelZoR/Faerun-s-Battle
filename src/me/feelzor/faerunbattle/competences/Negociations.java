package me.feelzor.faerunbattle.competences;

import me.feelzor.faerunbattle.model.Chateau;

public class Negociations extends Competence {
    private int toursRestants;

    public Negociations(Chateau joueur) {
        super(joueur);
        setToursRestants(0);
    }

    @Override
    public int getCout() {
        return 4;
    }

    private int getToursRestants() {
        return toursRestants;
    }

    private void setToursRestants(int toursRestants) {
        this.toursRestants = toursRestants;
    }

    private void decreaseToursRestants() {
        setToursRestants(getToursRestants() - 1);
    }

    @Override
    public boolean activer() {
        if (getToursRestants() != 0) { throw new IllegalStateException("L'effet est encore actif."); }
        if(!super.activer()) { return false; }

        setToursRestants(3);
        return true;
    }

    @Override
    public void debutTour() {
        decreaseToursRestants();
    }

    @Override
    public boolean estActif() {
        return getToursRestants() > 0;
    }
}
