package me.feelzor.faerunbattle.model;

import me.feelzor.faerunbattle.Color;
import me.feelzor.faerunbattle.DivineBlow;
import me.feelzor.faerunbattle.warriors.Warrior;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.logging.Logger;

public class Cell {
    private final static Logger LOGGER = Logger.getLogger(Cell.class.getName());
    private final List<Warrior> units;

    public Cell() {
        this.units = new ArrayList<>();
    }

    /**
     * Get the color of the cell
     * @return RED if all troops are red, BLUE if all troops are blue and NONE if there is no one or both colors on it
     */
    @NotNull
    public Color getColor() {
        Color col = Color.NONE;
        int i = 0;
        while (i < getNbUnits() && (col == Objects.requireNonNull(getUnitAt(i)).getColor() || col == Color.NONE)) {
            col = Objects.requireNonNull(getUnitAt(i)).getColor();
            i++;
        }

        return (i == getNbUnits()) ? col : Color.NONE;
    }

    /**
     * @return The list of units on the cell
     */
    @NotNull
    public List<Warrior> getUnits() {
        return this.units;
    }

    /**
     * @param index The index of the searched troop
     * @return The troop at the position index
     */
    private Warrior getUnitAt(int index) {
        if (index < 0 || index >= getNbUnits()) {
            LOGGER.warning("Illegal try to get non-existent troop at index " + index);
            return null;
        }

        return getUnits().get(index);
    }

    /**
     * @return The number of troops on the cell
     */
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
            LOGGER.warning("Illegal try to move troops during a fight.");
            return new ArrayList<>();
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
        Collections.shuffle(fightOrder);
        for (Warrior w : fightOrder) {
            if (w.isAlive()) {
                try { w.startTurn(this.getUnits()); }
                catch (DivineBlow e) {
                    System.out.println("Divine Blow ! Everyone's dead.");
                    this.clearUnits();
                    this.addUnit(e.getAttacker());
                }
            }
        }

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
