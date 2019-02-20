package me.feelzor.faerunbattle.warriors;

import me.feelzor.faerunbattle.Color;
import org.jetbrains.annotations.NotNull;

public class DwarfLeader extends Dwarf {
    public static final int COST = 3;

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
        return COST;
    }

    @Override
    protected WarriorType getType() {
        return WarriorType.DWARF_LEADER;
    }
}
