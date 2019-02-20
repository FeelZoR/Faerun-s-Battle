package me.feelzor.faerunbattle.skills;

import me.feelzor.faerunbattle.model.Castle;
import org.jetbrains.annotations.NotNull;

public class Negotiations extends Skill {

    public Negotiations() { super(); }

    public Negotiations(@NotNull Castle player) {
        super(player);
    }

    @Override
    public int getCost() {
        return 4;
    }

    @Override
    public boolean activate() {
        if (getRemainingTurns() != 0) { throw new IllegalStateException("This skill is still active."); }
        if(!super.activate()) { return false; }

        setRemainingTurns(3);
        return true;
    }

    @Override
    public void newTurn() {
        decreaseRemainingTurns();
    }

    @Override
    public boolean isActive() {
        return getRemainingTurns() > 0;
    }
}
