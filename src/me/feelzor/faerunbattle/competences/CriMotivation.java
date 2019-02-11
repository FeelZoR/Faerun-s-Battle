package me.feelzor.faerunbattle.competences;

import me.feelzor.faerunbattle.model.Chateau;
import me.feelzor.faerunbattle.model.Plateau;
import me.feelzor.faerunbattle.personnages.Guerrier;

import java.util.List;

public class CriMotivation extends Competence {

    private Plateau plateau;
    private boolean disponible;

    public CriMotivation(Chateau joueur) {
        this(joueur, null);
    }

    public CriMotivation(Chateau joueur, Plateau plateau) {
        super(joueur);
        setPlateau(plateau);
        setDisponible(true);
    }

    @Override
    public int getCout() {
        return 2;
    }

    private Plateau getPlateau() {
        return plateau;
    }

    private boolean isDisponible() {
        return disponible;
    }

    private void setPlateau(Plateau plateau) {
        this.plateau = plateau;
    }

    private void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    @Override
    public void debutTour() {
        setDisponible(true);
    }

    @Override
    public boolean activer() {
        if (!isDisponible()) {
            throw new IllegalStateException("Compétence déjà utilisée durant le tour actuel.");
        }
        if (!super.activer()) { return false; }

        setDisponible(false);
        for (int i = 0; i < getPlateau().getNbCarreaux(); i++) {
            List<Guerrier> guerriers = getPlateau().getUnitesCarreau(i);
            for (Guerrier g : guerriers) {
                if (g.getCouleur() == getJoueur().getCouleur()) {
                    g.augmenterNbDeplacements();
                }
            }
        }
        return true;
    }
}
