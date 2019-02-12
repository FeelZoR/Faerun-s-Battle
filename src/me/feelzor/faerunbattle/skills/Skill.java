package me.feelzor.faerunbattle.skills;

import me.feelzor.faerunbattle.model.Castle;
import org.jetbrains.annotations.NotNull;

public abstract class Skill {
    private final Castle player;

    public Skill(@NotNull Castle player) {
        this.player = player;
    }

    /**
     * @return The skill usage cost.
     */
    public abstract int getCost();

    @NotNull
    protected final Castle getPlayer() {
        return player;
    }

    /**
     * Starts a new turn
     */
    public void newTurn() {}

    /**
     * Activated the skill
     * @return true if the skill was correctly activated.
     */
    public boolean activate() {
        if (getPlayer().getResources() < this.getCost()) { return false; }

        getPlayer().consumeResources(this.getCost());
        return true;
    }

    /**
     * @return true if the skill is active
     */
    public boolean isActive() { return false; }
}
