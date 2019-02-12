package me.feelzor.faerunbattle.model;

import me.feelzor.faerunbattle.Color;
import me.feelzor.faerunbattle.warriors.Warrior;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

public class Board {
    private final static Logger LOGGER = Logger.getLogger(Board.class.getName());
    private final Cell[] cells;

    public Board(int nbCells) {
        if (nbCells != 5 && nbCells != 10 && nbCells != 15) {
            throw new IllegalArgumentException("The number of cells can only be 5, 10 or 15.");
        }
        this.cells = new Cell[nbCells];
        for (int i = 0; i < nbCells; i++) {
            this.cells[i] = new Cell();
        }
    }

    /**
     * @return All cells of the board
     */
    private Cell[] getCells() {
        return cells;
    }

    /**
     * @param index The number of the cell to get
     * @return The cell at position index, or null if it doesn't exist
     */
    private Cell getCellAt(int index) {
        if (index < 0 || index >= getNbCells()) {
            LOGGER.warning("Illegal try to access non-existent cell at index " + index);
            return null;
        }

        return getCells()[index];
    }

    /**
     * @param index The cell index
     * @return The troops placed on a cell
     */
    @NotNull
    public List<Warrior> getUnitsOnCell(int index) {
        Cell c = getCellAt(index);
        return (c != null) ? c.getUnits() : new ArrayList<>();
    }

    /**
     * @param index The cell index
     * @return The cell's color
     */
    @NotNull
    public Color getCellColor(int index) {
        Cell c = getCellAt(index);
        return (c != null) ? c.getColor() : Color.NONE;
    }

    /**
     * @return The number of cells in the game
     */
    public int getNbCells() {
        return cells.length;
    }

    /**
     * Add a troop from a castle
     * @param unit The unit to add
     */
    public void addUnit(@NotNull Warrior unit) {
        if (unit.getColor() == Color.BLUE) {
            Objects.requireNonNull(getCellAt(0)).addUnit(unit);
        } else { // Color rouge
            Objects.requireNonNull(getCellAt(getNbCells() - 1)).addUnit(unit);
        }
    }

    /**
     * Move troops as long as it's possible
     */
    public void moveUnits() {
        while (!movementsFinished()) {
            moveColor(Color.BLUE, 1);
            moveColor(Color.RED, -1);
        }
    }

    /**
     * @return true if movements are all terminated
     */
    private boolean movementsFinished() {
        Color col;
        boolean result = true;
        int i = 0;
        while (i < getNbCells() && result) {
            Cell c = getCellAt(i);
            if (c == null) { continue; }
            if ((col = c.getColor()) == Color.NONE || // If there are fights on this cell, or it is empty
                    col == Color.BLUE && i == getNbCells() - 1 || // Or if the units have reached the end of the board
                    col == Color.RED && i == 0) { i++; continue; }

            List<Warrior> warriors = c.getUnits();
            int j = 0;
            while (j < warriors.size() && result) {
                if (warriors.get(j).canMove()) {
                    result = false;
                }
                j++;
            }
            i++;
        }

        return result;
    }

    /**
     * Start fights on the board (on only one cell)
     */
    public void startFights() {
        int i = 0;
        Cell cell;
        while (i < getNbCells() && ((Objects.requireNonNull(cell = getCellAt(i))).getColor() != Color.NONE || cell.getNbUnits() == 0)) {
            i++;
        }

        if (i < getNbCells()) {
            Cell c = Objects.requireNonNull(getCellAt(i));
            if (c.getNbUnits() > 0 && c.getColor() == Color.NONE) {
                c.startFight();
                this.moveUnits();
            }
        }
    }

    /**
     * Move all troops of one color
     * @param direction 1 of the color is blue, -1 if the color is red
     */
    private void moveColor(Color col, int direction) {
        int i = (direction == 1) ? 0 : getNbCells() - 1;
        Cell cell;
        while (i >= 0 && i < getNbCells() && ((Objects.requireNonNull(cell = getCellAt(i))).getColor() == col || cell.getColor() == Color.NONE)) {
            if (cell.getColor() != Color.NONE && (direction == 1 && i != getNbCells() - 1 || direction == -1 && i != 0)) { // If it's not the last cell
                List<Warrior> movements = cell.moveUnits();
                Objects.requireNonNull(getCellAt(i + direction)).addUnits(movements);
            }
            i += direction;
        }
    }

    /**
     * Show the board
     */
    public void showBoard() {
        for (int i = 0; i < this.getNbCells(); i++) {
            System.out.println("------- Cell " + (i+1) + " -------");
            Cell c = Objects.requireNonNull(getCellAt(i));
            c.sortUnits();
            for (Warrior w : c.getUnits()) {
                System.out.println(w);
            }

            if (i < 10) { System.out.println("----------------------"); }
            else { System.out.println("-----------------------"); }
        }
    }
}
