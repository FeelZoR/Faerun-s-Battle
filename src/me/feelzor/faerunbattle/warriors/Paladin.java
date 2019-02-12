package me.feelzor.faerunbattle.warriors;

import me.feelzor.faerunbattle.Color;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Paladin extends Warrior {
    public Paladin() {
        super();
    }

    public Paladin(@NotNull Color col) {
        this(col, 0);
    }

    public Paladin(@NotNull Color col, int bonusStrength) {
        super(col, bonusStrength);
        this.setHealthPoints(200);
    }

    @Override
    public int getCost() {
        return 4;
    }

    @Override
    public void startTurn(@NotNull List<Warrior> warriors) {
        int provocAvg = provocationAverage(warriors);
        if (this.getProvocation() < provocAvg) {
            this.increaseProvocation((provocAvg - this.getProvocation()) * 2);
            System.out.println(this + " provoke its enemies !");
        } else {
            super.startTurn(warriors);
        }
    }

    @Override
    protected void damage(int amount) {
        super.damage(amount / 3);
    }
}
