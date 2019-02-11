package me.feelzor.faerunbattle.model;

import me.feelzor.faerunbattle.Color;
import me.feelzor.faerunbattle.DivineBlow;
import me.feelzor.faerunbattle.warriors.Warrior;

import java.util.ArrayList;
import java.util.List;

public class Cell {
    private List<Warrior> units;

    public Cell() {
        this.units = new ArrayList<>();
    }

    /**
     * Get the color of the cell
     * @return RED if all troops are red, BLUE if all troops are blue and NONE if there is no one or both colors on it
     */
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
    public List<Warrior> getUnits() {
        return this.units;
    }

    /**
     * @param index The index of the searched troop
     * @return The troop at the position index
     */
    private Warrior getUnitAt(int index) {
        if (index < 0 || index >= getNbUnits()) {
            throw new IllegalArgumentException("There is no troop with index " + index + ".");
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
    public void addUnit(Warrior unit) {
        getUnits().add(unit);
    }

    /**
     * Add a list of troops to the cell
     */
    public void addUnits(List<Warrior> units) {
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
    public void removeUnit(Warrior unit) {
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
    public void removeUnits(List<Warrior> warriors) {
        getUnits().removeAll(warriors);
    }

    /**
     * Move troops
     * @return The list of removed troops to move somewhere else
     */
    public List<Warrior> moveUnits() {
        if (getColor() == Color.NONE) {
            throw new IllegalStateException("Cannot move troops during a fight.");
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
        for (Warrior w : this.getUnits()) {
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
}
