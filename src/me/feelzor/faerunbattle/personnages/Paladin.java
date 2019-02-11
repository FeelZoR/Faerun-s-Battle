package me.feelzor.faerunbattle.personnages;

import me.feelzor.faerunbattle.Couleur;

import java.util.List;

public class Paladin extends Guerrier {
    public Paladin() {
        super();
    }

    public Paladin(Couleur coul) {
        this(coul, 0);
    }

    public Paladin(Couleur coul, int bonusForce) {
        super(coul, bonusForce);
        this.setPointsDeVie(200);
    }

    @Override
    public int getCout() {
        return 4;
    }

    @Override
    public void tour(List<Guerrier> guerriers) {
        int moyenneProvoc = moyenneProvocation(guerriers);
        if (this.getProvocation() < moyenneProvoc) {
            this.augmenterProvocation((moyenneProvoc - this.getProvocation()) * 2);
            System.out.println(this + " provoque ses ennemis !");
        } else {
            super.tour(guerriers);
        }
    }

    @Override
    protected void subirDegats(int degats) {
        super.subirDegats(degats / 3);
    }
}
