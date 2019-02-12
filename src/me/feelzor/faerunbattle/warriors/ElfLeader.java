package me.feelzor.faerunbattle.warriors;

import me.feelzor.faerunbattle.Color;
import org.jetbrains.annotations.NotNull;

public class ElfLeader extends Elf {
    public ElfLeader() {
        super();
    }

    public ElfLeader(@NotNull Color col) {
        super(col);
    }

    public ElfLeader(@NotNull Color col, int bonusStrength) {
        super(col, bonusStrength);
    }

    @Override
    public int getStrength() {
        return super.getStrength() * 2;
    }

    @Override
    public int getCost() {
        return 3;
    }
}
