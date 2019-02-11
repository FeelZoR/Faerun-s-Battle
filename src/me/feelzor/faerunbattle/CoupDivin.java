package me.feelzor.faerunbattle;

import me.feelzor.faerunbattle.personnages.Guerrier;

public class CoupDivin extends RuntimeException {
    private Guerrier attaquant;

    public CoupDivin(Guerrier attaquant) {
        this.attaquant = attaquant;
    }

    public Guerrier recupererAttaquant() {
        return this.attaquant;
    }
}
