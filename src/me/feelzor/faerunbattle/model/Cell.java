package me.feelzor.faerunbattle.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import me.feelzor.faerunbattle.Color;
import me.feelzor.faerunbattle.DivineBlow;
import me.feelzor.faerunbattle.utils.PrintUtils;
import me.feelzor.faerunbattle.utils.actions.ActionLog;
import me.feelzor.faerunbattle.utils.actions.DivineBlowLog;
import me.feelzor.faerunbattle.warriors.Warrior;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class Cell {

    private final List<Warrior> units;

    public Cell() {
        this.units = new ArrayList<>();
    }

    /**
     * Get the color of the cell
     * @return RED if all troops are red, BLUE if all troops are blue and NONE if there is no one or both colors on it
     */
    @NotNull
    @JsonIgnore
    public Color getColor() {
        Color col = Color.NONE;
        int i = 0;
        while (i < getNbUnits() && (col == getUnitAt(i).getColor() || col == Color.NONE)) {
            col = getUnitAt(i).getColor();
            i++;
        }

        return (i == getNbUnits()) ? col : Color.NONE;
    }

    /**
     * @return The list of units on the cell
     */
    @NotNull
    @JsonProperty("troops")
    public List<Warrior> getUnits() {
        return this.units;
    }

    /**
     * @param index The index of the searched troop
     * @return The troop at the position index
     */
    private Warrior getUnitAt(int index) {
        if (index < 0 || index >= getNbUnits()) {
            throw new IllegalArgumentException("Illegal try to get non-existent troop at index " + index);
        }

        return getUnits().get(index);
    }

    /**
     * @return The number of troops on the cell
     */
    @JsonIgnore
    public int getNbUnits() { return getUnits().size(); }

    /**
     * Add a troop on the cell
     * @param unit The unit to add
     */
    public void addUnit(@NotNull Warrior unit) {
        getUnits().add(unit);
    }

    /**
     * Add a list of troops to the cell
     */
    public void addUnits(@NotNull List<Warrior> units) {
        getUnits().addAll(units);
    }

    /**
     * Removes all troops from the cell
     */
    public void clearUnits() {
        getUnits().clear();
    }

    /**
     * Remove a troop from the cell
     */
    public void removeUnit(@NotNull Warrior unit) {
        getUnits().remove(unit);
    }

    /**
     * Remove a troop from the cell using its position
     */
    public void removeUnitAt(int index) {
        getUnits().remove(index);
    }

    /**
     * Remove a list of troops from the cell
     */
    public void removeUnits(@NotNull List<Warrior> warriors) {
        getUnits().removeAll(warriors);
    }

    /**
     * Move troops
     * @return The list of removed troops to move somewhere else
     */
    @NotNull
    public List<Warrior> moveUnits() {
        if (getColor() == Color.NONE) {
            throw new IllegalStateException("Illegal try to move troops during a fight.");
        }

        List<Warrior> result = new ArrayList<>();
        for (Warrior w : getUnits()) {
            if (w.canMove()) {
                result.add(w);
                w.move();
            }
        }

        this.removeUnits(result);
        return result;
    }

    /**
     * Start fights on the cell
     */
    public void startFight() {
        List<Warrior> fightOrder = new ArrayList<>(this.getUnits());
        List<ActionLog> fightLog = new ArrayList<>();
        Collections.shuffle(fightOrder);
        for (Warrior w : fightOrder) {
            if (w.isAlive()) {
                try {
                    fightLog.add(w.startTurn(this.getUnits()));
                } catch (DivineBlow e) {
                    fightLog.add(new DivineBlowLog(e.getAttacker()));
                    this.clearUnits();
                    this.addUnit(e.getAttacker());
                }
            }
        }

        PrintUtils.logFight(fightLog);
        getUnits().removeIf(w -> (!w.isAlive()));
    }

    /**
     * Sort units
     */
    public void sortUnits() {
        getUnits().sort((w1, w2) -> {
            if (w1.getColor() == Color.BLUE && w2.getColor() == Color.RED) { return -1; } // Blue before red
            else if (w1.getColor() == w2.getColor()) { // Same color
                if (w1.getHealthPoints() < w2.getHealthPoints()) { return -1; } // Less hp comes first
                else if (w1.getHealthPoints() == w2.getHealthPoints()) { return 0; } // Same color & same hp -> equal
            }

            return 1;
        });
    }
}
