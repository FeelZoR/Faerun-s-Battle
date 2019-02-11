package me.feelzor.faerunbattle.personnages;

import me.feelzor.faerunbattle.Couleur;

public class ChefElfe extends Elfe {
    public ChefElfe() {
        super();
    }

    public ChefElfe(Couleur coul) {
        super(coul);
    }

    public ChefElfe(Couleur coul, int bonusForce) {
        super(coul, bonusForce);
    }

    @Override
    public int getForce() {
        return super.getForce() * 2;
    }

    @Override
    public int getCout() {
        return 3;
    }
}
