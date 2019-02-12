package me.feelzor.faerunbattle.warriors;

import me.feelzor.faerunbattle.Color;
import me.feelzor.faerunbattle.utils.actions.ActionLog;
import me.feelzor.faerunbattle.utils.actions.ProvocationLog;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Paladin extends Warrior {
    public static final int COST = 4;

    public Paladin(@NotNull Color col) {
        this(col, 0);
    }

    public Paladin(@NotNull Color col, int bonusStrength) {
        super(col, bonusStrength);
        this.setHealthPoints(200);
    }

    @Override
    public int getCost() {
        return COST;
    }

    @Override
    public ActionLog startTurn(@NotNull List<Warrior> warriors) {
        int provocAvg = provocationAverage(warriors);
        if (this.getProvocation() < provocAvg) {
            this.increaseProvocation((provocAvg - this.getProvocation()) * 2);
            return new ProvocationLog(this);
        } else {
            return super.startTurn(warriors);
        }
    }

    @Override
    protected void damage(int amount) {
        super.damage(amount / 3);
    }
}
