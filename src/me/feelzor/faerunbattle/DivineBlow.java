package me.feelzor.faerunbattle;

import me.feelzor.faerunbattle.warriors.Warrior;

public class DivineBlow extends RuntimeException {
    private Warrior attacker;

    public DivineBlow(Warrior attacker) {
        this.attacker = attacker;
    }

    public Warrior getAttacker() {
        return this.attacker;
    }
}
