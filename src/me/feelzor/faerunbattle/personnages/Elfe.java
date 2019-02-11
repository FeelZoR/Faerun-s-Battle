package me.feelzor.faerunbattle.personnages;

import me.feelzor.faerunbattle.Couleur;

public class Elfe extends Guerrier {
    public Elfe() {
        super();
    }

    public Elfe(Couleur coul) {
        super(coul);
    }

    public Elfe(Couleur coul, int bonusForce) {
        super(coul, bonusForce);
    }

    @Override
    public int getForce() {
        return super.getForce() * 2;
    }

    @Override
    public int getCout() {
        return 2;
    }
}
