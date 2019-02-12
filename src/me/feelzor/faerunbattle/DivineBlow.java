package me.feelzor.faerunbattle;

import me.feelzor.faerunbattle.warriors.Warrior;
import org.jetbrains.annotations.NotNull;

public class DivineBlow extends RuntimeException {
    private Warrior attacker;

    public DivineBlow(@NotNull Warrior attacker) {
        this.attacker = attacker;
    }

    @NotNull
    public Warrior getAttacker() {
        return this.attacker;
    }
}
