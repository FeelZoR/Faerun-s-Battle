package me.feelzor.faerunbattle.personnages;

import me.feelzor.faerunbattle.Couleur;

public class Recruteur extends Guerrier {
    public Recruteur() {
        super();
    }

    public Recruteur(Couleur coul) {
        super(coul);
        this.setPointsDeVie(60);
    }

    @Override
    public int getCout() {
        return 5;
    }

    @Override
    public void attaquer(Guerrier guerrier) {
        if (Math.random() * 100 <= 15) {
            System.out.println(this + " recrute " + guerrier + " !");
            guerrier.setCouleur(this.getCouleur());
            this.augmenterProvocation((int) (Math.random() * 20) + 30);
        } else {
            super.attaquer(guerrier);
        }
    }
}
