package me.feelzor.faerunbattle.skills;

import me.feelzor.faerunbattle.model.Castle;
import org.jetbrains.annotations.NotNull;

public class Bargaining extends Skill {
    @Override
    public int getCost() {
        return 5 * getPlayer().getResourcesOnNewTurn() + (int) Math.pow(getPlayer().getResourcesOnNewTurn() - 1, 1.3);
    }

    public Bargaining() { super(); }

    public Bargaining(@NotNull Castle player) {
        super(player);
    }

    @Override
    public boolean activate() {
        if (!super.activate()) { return false; }

        getPlayer().increaseResourcesOnNewTurn();
        return true;
    }
}
