package me.feelzor.faerunbattle.skills;

import me.feelzor.faerunbattle.model.Castle;

public class Negotiations extends Skill {
    private int remainingTurns;

    public Negotiations(Castle player) {
        super(player);
        setRemainingTurns(0);
    }

    @Override
    public int getCost() {
        return 4;
    }

    private int getRemainingTurns() {
        return remainingTurns;
    }

    private void setRemainingTurns(int remainingTurns) {
        this.remainingTurns = remainingTurns;
    }

    private void decreaseRemainingTurns() {
        setRemainingTurns(getRemainingTurns() - 1);
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
