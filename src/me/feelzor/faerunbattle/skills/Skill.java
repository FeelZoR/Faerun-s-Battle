package me.feelzor.faerunbattle.skills;

import me.feelzor.faerunbattle.model.Castle;

public abstract class Skill {
    /**
     * @return The skill usage cost.
     */
    public abstract int getCost();
    private Castle player;

    public Skill(Castle player) {
        setPlayer(player);
    }

    protected Castle getPlayer() {
        return player;
    }

    private void setPlayer(Castle player) {
        this.player = player;
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
