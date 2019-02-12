package me.feelzor.faerunbattle.warriors;

import me.feelzor.faerunbattle.Color;
import org.jetbrains.annotations.NotNull;

public class DwarfLeader extends Dwarf {
    public DwarfLeader() {
        super();
    }

    public DwarfLeader(@NotNull Color col) {
        super(col);
    }

    public DwarfLeader(@NotNull Color col, int bonusStrength) {
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
