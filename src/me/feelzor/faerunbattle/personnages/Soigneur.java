package me.feelzor.faerunbattle.personnages;

import me.feelzor.faerunbattle.Couleur;

import java.util.ArrayList;
import java.util.List;

public class Soigneur extends Guerrier {

    public Soigneur() {
        super();
    }

    public Soigneur(Couleur coul) {
        super(coul);
    }

    @Override
    public int getForce() {
        return (int) (super.getForce() / 1.5);
    }

    @Override
    protected void subirDegats(int degats) {
        super.subirDegats((int) (degats * 1.5));
    }

    @Override
    public int getCout() {
        return 5;
    }

    @Override
    public void tour(List<Guerrier> guerriers) {
        int nb = (int) (Math.random() * 100);
        Guerrier cible = this.plusMalEnPoint(guerriers);
        if (nb >= 40 || cible == null) { // Attaque (60%)
            super.tour(guerriers);
        } else if (nb != 1) { // Soigne le premier coéquipier (39%)
            cible.setPointsDeVie(cible.getPointsDeVie() + 20);
            this.augmenterProvocation((int) (Math.random() * 30) + 10);
            System.out.println(this + " a soigné " + cible);
        } else { // Soigne tout le monde (1%)
            for (Guerrier g : guerriers) {
                if (g.getCouleur() == this.getCouleur()) {
                    g.setPointsDeVie(g.getPointsDeVie() + 10);
                    this.augmenterProvocation((int) (Math.random() * 20) + 40);
                    System.out.println(this + " a soigné toute son équipe !");
                }
            }
        }
    }

    /**
     * @param guerriers Les guerriers présents sur la case
     * @return L'allié le plus mal en point
     */
    private Guerrier plusMalEnPoint(List<Guerrier> guerriers) {
        if (guerriers.size() == 0) return null;

        List<Guerrier> cibles = new ArrayList<>(guerriers);
        cibles.removeIf(p -> p.getCouleur() != this.getCouleur());
        cibles.sort((p1, p2) -> p2.getPointsDeVie() - p1.getPointsDeVie());

        return cibles.get(0);
    }
}
