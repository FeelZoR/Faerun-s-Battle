package me.feelzor.faerunbattle.warriors;

import me.feelzor.faerunbattle.Color;
import org.jetbrains.annotations.NotNull;

public class Elf extends Warrior {
    public Elf() {
        super();
    }

    public Elf(@NotNull Color col) {
        super(col);
    }

    public Elf(@NotNull Color col, int bonusStrength) {
        super(col, bonusStrength);
    }

    @Override
    public int getStrength() {
        return super.getStrength() * 2;
    }

    @Override
    public int getCost() {
        return 2;
    }
}
