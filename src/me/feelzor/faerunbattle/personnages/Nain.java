package me.feelzor.faerunbattle.personnages;

import me.feelzor.faerunbattle.Couleur;

public class Nain extends Guerrier {
    public Nain() {
        super();
    }

    public Nain(Couleur coul) {
        super(coul);
    }

    public Nain(Couleur coul, int bonusForce) {
        super(coul, bonusForce);
    }

    @Override
    protected void subirDegats(int degats) {
        super.subirDegats(degats / 2);
    }

    @Override
    public int getCout() {
        return 2;
    }
}
