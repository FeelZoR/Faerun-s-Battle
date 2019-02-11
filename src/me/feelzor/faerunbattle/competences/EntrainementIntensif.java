package me.feelzor.faerunbattle.competences;

import me.feelzor.faerunbattle.model.Chateau;

public class EntrainementIntensif extends Competence {

    private int toursRestants;
    private int boost;

    public EntrainementIntensif(Chateau joueur) {
        super(joueur);
        this.toursRestants = 0;
        this.boost = 0;
    }

    @Override
    public int getCout() {
        return (!this.estActif()) ? 3 : 2;
    }

    private int getToursRestants() {
        return toursRestants;
    }

    private void setToursRestants(int toursRestants) {
        this.toursRestants = Math.max(toursRestants, 0);
    }

    private void decreaseToursRestants() {
        setToursRestants(getToursRestants() - 1);
    }

    public int getBoost() {
        return this.boost;
    }

    private void setBoost(int boost) {
        this.boost = Math.max(boost, 0);
    }

    private void increaseBoost() {
        setBoost(getBoost() + 1);
    }

    @Override
    public void debutTour() {
        decreaseToursRestants();
    }

    @Override
    public boolean activer() {
        if (!super.activer()) { return false; }

        if (this.estActif()) {
            increaseBoost();
        } else {
            setBoost(2);
            setToursRestants(2);
        }
        return true;
    }

    @Override
    public boolean estActif() {
        return getToursRestants() > 0;
    }
}
