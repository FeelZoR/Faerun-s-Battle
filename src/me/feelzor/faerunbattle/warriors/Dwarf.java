package me.feelzor.faerunbattle.warriors;

import me.feelzor.faerunbattle.Color;

public class Dwarf extends Warrior {
    public Dwarf() {
        super();
    }

    public Dwarf(Color col) {
        super(col);
    }

    public Dwarf(Color col, int bonusStrength) {
        super(col, bonusStrength);
    }

    @Override
    protected void damage(int amount) {
        super.damage(amount / 2);
    }

    @Override
    public int getCost() {
        return 2;
    }
}
