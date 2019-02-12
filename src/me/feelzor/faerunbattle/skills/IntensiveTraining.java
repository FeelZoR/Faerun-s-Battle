package me.feelzor.faerunbattle.skills;

import me.feelzor.faerunbattle.model.Castle;
import org.jetbrains.annotations.NotNull;

public class IntensiveTraining extends Skill {

    private int remainingTurns;
    private int boost;

    public IntensiveTraining(@NotNull Castle player) {
        super(player);
        setRemainingTurns(0);
        setBoost(0);
    }

    @Override
    public int getCost() {
        return (!this.isActive()) ? 3 : 2;
    }

    private int getRemainingTurns() {
        return remainingTurns;
    }

    private void setRemainingTurns(int remainingTurns) {
        this.remainingTurns = Math.max(remainingTurns, 0);
    }

    private void decreaseRemainingTurns() {
        setRemainingTurns(getRemainingTurns() - 1);
    }

    public int getBoost() {
        return this.boost;
    }

    private void setBoost(int boost) {
        this.boost = Math.max(boost, 0);
    }

    private void increaseBoost() {
        setBoost(getBoost() + 1);
    }

    @Override
    public void newTurn() {
        decreaseRemainingTurns();
    }

    @Override
    public boolean activate() {
        if (!super.activate()) { return false; }

        if (this.isActive()) {
            increaseBoost();
        } else {
            setBoost(2);
            setRemainingTurns(2);
        }
        return true;
    }

    @Override
    public boolean isActive() {
        return getRemainingTurns() > 0;
    }
}
