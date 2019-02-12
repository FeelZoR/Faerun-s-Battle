package me.feelzor.faerunbattle.warriors;

import me.feelzor.faerunbattle.Color;
import me.feelzor.faerunbattle.DivineBlow;
import me.feelzor.faerunbattle.utils.RandomUtils;
import me.feelzor.faerunbattle.utils.actions.ActionLog;
import me.feelzor.faerunbattle.utils.actions.AttackLog;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public abstract class Warrior {
    private final static Logger LOGGER = Logger.getLogger(Warrior.class.getName());
    private Color col;
    private int healthPoints;
    private int nbMovements;
    private int maxMovements;
    private int provocation;
    private int strength;

    public Warrior() {
        this(Color.NONE);
    }

    public Warrior(@NotNull Color col) {
        this.setHealthPoints(100);
        reinitializeMovements();
        setProvocation(1);
        setStrength(10);

        if (col != Color.NONE) { this.setColor(col); }
    }

    public Warrior(@NotNull Color col, int bonusStrength) {
        this(col);
        if (bonusStrength < 0) {
            LOGGER.warning("Illegal try to set a negative strength bonus.");
            return;
        }

        setStrength(this.strength + bonusStrength);
    }

    /**
     * @return The warrior's hp
     */
    public int getHealthPoints() {
        return healthPoints;
    }

    /**
     * @return The warrior's color (RED ou BLUE)
     */
    @NotNull
    public Color getColor() {
        return col;
    }

    /**
     * @return The provocation of the warrior
     */
    public int getProvocation() {
        return provocation;
    }
    /**
     * @return The warrior's strength
     */
    public int getStrength() {
        return strength;
    }

    /**
     * @return The number of times the warrior moved in this turn
     */
    private int getNbMovements() {
        return nbMovements;
    }

    /**
     * @return The maximum number of times the warrior can move in this turn
     */
    private int getMaxMovements() {
        return maxMovements;
    }

    /**
     * @return The warriors training cost
     */
    public int getCost() {
        return 1;
    }

    /**
     * Update the warrior's hp
     */
    public void setHealthPoints(int healthPoints) {
        this.healthPoints = Math.max(healthPoints, 0);
    }

    /**
     * Change the team of the warrior
     * @param col The new color (either RED or BLUE)
     */
    public void setColor(@NotNull Color col) {
        if (col == Color.NONE) {
            LOGGER.warning("Illegal try to set color NONE");
            return;
        }

        this.col = col;
    }

    /**
     * Set the number of times the warrior moved during this turn
     */
    private void setNbMovements(int nbMovements) {
        this.nbMovements = Math.max(nbMovements, 0);
    }

    /**
     * Set the maximum number of movements the warrior can make
     */
    private void setMaxMovements(int maxMovements) {
        this.maxMovements = Math.max(maxMovements, 0);
    }

    /**
     * Change the provocation of the warrior
     */
    private void setProvocation(int provocation) {
        this.provocation = Math.max(provocation, 1);
    }

    /**
     * Set the warrior's strength
     */
    private void setStrength(int strength) {
        this.strength = Math.max(strength, 0);
    }

    /**
     * Increases the maximum number of movements by one
     */
    public void increaseMaxMovements() {
        setMaxMovements(getMaxMovements() + 1);
    }

    /**
     * Increases the provocation of the warrior
     */
    public void increaseProvocation(int amount) {
        if (amount < 0) {
            LOGGER.warning("Illegal try to increase provocation with a negative amount.");
            return;
        }

        setProvocation(getProvocation() + amount);
    }

    /**
     * @return true if the warrior is still alive
     */
    public boolean isAlive() {
        return this.getHealthPoints() > 0;
    }

    /**
     * @return true if the warrior can still move
     */
    public boolean canMove() {
        return getNbMovements() != getMaxMovements();
    }

    /**
     * Indicates the warrior it has moved once
     */
    public void move() {
        if (!canMove()) {
            LOGGER.warning("Illegal try to move a warrior unable to move.");
            return;
        }

        setNbMovements(getNbMovements() + 1);
    }

    /**
     * Reinitialize movements (at the beginning of a turn)
     */
    public void reinitializeMovements() {
        setMaxMovements(1);
        setNbMovements(0);
    }

    /**
     * @param warriors The list of warriors on a cell
     * @return The average level of provocation of allies
     */
    protected int provocationAverage(@NotNull List<Warrior> warriors) {
        int nbAllies = 0;
        int totalProvoc = 0;
        for (Warrior w : warriors) {
            if (w.getColor() != this.getColor()) { continue; }
            nbAllies++;
            totalProvoc += w.getProvocation();
        }

        return Math.round((float) totalProvoc / nbAllies);
    }

    /**
     * Endure an attack
     */
    protected void damage(int amount) {
        this.setHealthPoints(this.getHealthPoints() - (amount));
    }

    /**
     * Attack another warrior
     */
    @NotNull
    public ActionLog attack(@NotNull Warrior warrior) {
        if (!this.isAlive()) {
            throw new IllegalStateException("Illegal try to attack another warrior when dead.");
        }

        int damage = RandomUtils.throwDice(this.getStrength(), 1, 3);

        if (this.getStrength() * 3 * 0.95 <= damage && damage != 0) { throw new DivineBlow(this); }
        int healthBeforeBlow = warrior.healthPoints;
        warrior.damage(damage);
        this.increaseProvocation(damage);

        return new AttackLog(this, warrior, damage, healthBeforeBlow - warrior.getHealthPoints(), warrior.getHealthPoints());
    }

    /**
     * Start a new turn
     * @param warriors The warrior's list
     */
    @Nullable
    public ActionLog startTurn(@NotNull List<Warrior> warriors) {
        Color enemyColor = (this.getColor() == Color.BLUE) ? Color.RED : Color.BLUE;
        Warrior target = firstAlive(warriors, enemyColor);

        if (target == null) {
            return null;
        }
        return this.attack(target);
    }

    /**
     * Get the first warrior in the list alive and corresponding to the given color
     * @return The first corresponding warrior or null if there is none
     */
    private Warrior firstAlive(List<Warrior> warriors, Color color) {
        List<Warrior> targets = new ArrayList<>();
        for (Warrior w : warriors) {
            if (w.getColor() == color && w.isAlive()) {
                targets.add(w);
            }
        }

        if (targets.size() == 0) return null;
        targets.sort((g1, g2) -> (g2.getProvocation() - g1.getProvocation()));

        int currentProb = 0;
        int maxProb = 0;
        for (Warrior w : targets) {
            maxProb += w.getProvocation();
        }

        int i = -1;
        do {
            i++;
            currentProb += targets.get(i).getProvocation();
        } while (i < targets.size() && RandomUtils.generateNumber(maxProb) > currentProb);

        return targets.get(i);
    }

    /**
     * @return The warrior's name depending on its class
     */
    @NotNull
    public String getName() {
        String name = this.getClass().getSimpleName();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < name.length(); i++) {
            if (i != 0 && Character.isUpperCase(name.charAt(i))) { builder.append(' '); }
            builder.append(name.charAt(i));
        }

        return builder.toString();
    }

    @Override
    public String toString() {
        return this.getColor().toString() + ' ' + this.getName() + " [" + this.getHealthPoints() + "]";
    }
}
