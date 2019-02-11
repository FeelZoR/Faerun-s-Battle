package me.feelzor.faerunbattle.personnages;

import me.feelzor.faerunbattle.Couleur;

public class ChefNain extends Nain {
    public ChefNain() {
        super();
    }

    public ChefNain(Couleur coul) {
        super(coul);
    }

    public ChefNain(Couleur coul, int bonusForce) {
        super(coul, bonusForce);
    }

    @Override
    protected void subirDegats(int degats) {
        super.subirDegats(degats / 2);
    }

    @Override
    public int getCout() {
        return 3;
    }
}
