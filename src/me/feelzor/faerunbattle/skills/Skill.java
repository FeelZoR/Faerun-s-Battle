package me.feelzor.faerunbattle.skills;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import me.feelzor.faerunbattle.model.Castle;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public abstract class Skill {
    private Castle player;
    private int remainingTurns;
    private int boost;

    public Skill(){}

    public Skill(@NotNull Castle player) {
        setPlayer(player);
        setRemainingTurns(0);
        setBoost(0);
    }

    /**
     * @return The skill usage cost.
     */
    @JsonIgnore
    public abstract int getCost();

    @NotNull
    protected final Castle getPlayer() {
        return player;
    }

    public void setPlayer(@NotNull Castle player) {
        this.player = player;
    }

    @JsonProperty("remainingTurns")
    protected int getRemainingTurns() {
        return remainingTurns;
    }

    protected void setRemainingTurns(int remainingTurns) {
        this.remainingTurns = Math.max(remainingTurns, 0);
    }

    protected void decreaseRemainingTurns() {
        setRemainingTurns(getRemainingTurns() - 1);
    }

    @JsonProperty("boost")
    public int getBoost() {
        return this.boost;
    }

    protected void setBoost(int boost) {
        this.boost = Math.max(boost, 0);
    }

    protected void increaseBoost() {
        setBoost(getBoost() + 1);
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
    @JsonIgnore
    public boolean isActive() { return false; }

    @JsonProperty("type")
    public String getClassName() {
        return this.getClass().getName();
    }

    @Nullable
    @JsonCreator
    private static Skill createSkill(
            @JsonProperty("type") String type,
            @JsonProperty("remainingTurns") int remainingTurns,
            @JsonProperty("boost") int boost
    ) {
        try {
            Class<?> sClass = Class.forName(type);
            Constructor<?> constructor = sClass.getConstructor();
            Skill s = (Skill) constructor.newInstance();
            s.setRemainingTurns(remainingTurns);
            s.setBoost(boost);
            return s;
        } catch (NoSuchMethodException | ClassNotFoundException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }
}
