package me.feelzor.faerunbattle.skills;

import me.feelzor.faerunbattle.model.Castle;
import org.jetbrains.annotations.NotNull;

public class IntensiveTraining extends Skill {

    public IntensiveTraining() { super(); }

    public IntensiveTraining(@NotNull Castle player) {
        super(player);
    }

    @Override
    public int getCost() {
        return (!this.isActive()) ? 3 : 2;
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
