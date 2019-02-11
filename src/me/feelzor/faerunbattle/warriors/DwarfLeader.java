package me.feelzor.faerunbattle.warriors;

import me.feelzor.faerunbattle.Color;

public class DwarfLeader extends Dwarf {
    public DwarfLeader() {
        super();
    }

    public DwarfLeader(Color col) {
        super(col);
    }

    public DwarfLeader(Color col, int bonusStrength) {
        super(col, bonusStrength);
    }

    @Override
    protected void damage(int amount) {
        super.damage(amount / 2);
    }

    @Override
    public int getCost() {
        return 3;
    }
}
