package me.feelzor.faerunbattle.warriors;

import me.feelzor.faerunbattle.Color;
import org.jetbrains.annotations.NotNull;

public class Dwarf extends Warrior {
    public static final int COST = 2;

    public Dwarf(@NotNull Color col) {
        super(col);
    }

    public Dwarf(@NotNull Color col, int bonusStrength) {
        super(col, bonusStrength);
    }

    @Override
    protected void damage(int amount) {
        super.damage(amount / 2);
    }

    @Override
    public int getCost() {
        return COST;
    }
}
