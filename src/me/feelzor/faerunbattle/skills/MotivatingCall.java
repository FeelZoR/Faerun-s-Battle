package me.feelzor.faerunbattle.skills;

import me.feelzor.faerunbattle.model.Castle;
import me.feelzor.faerunbattle.model.Board;
import me.feelzor.faerunbattle.warriors.Warrior;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MotivatingCall extends Skill {

    private Board board;
    private boolean available;

    public MotivatingCall() {
        super();
        this.board = null;
    }

    public MotivatingCall(@NotNull Castle player) {
        this(player, null);
    }

    public MotivatingCall(@NotNull Castle player, @Nullable Board board) {
        super(player);
        setBoard(board);
        setAvailable(true);
    }

    @Override
    public int getCost() {
        return 2;
    }

    @Nullable
    private Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    private boolean isAvailable() {
        return available;
    }

    private void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public void newTurn() {
        setAvailable(true);
    }

    @Override
    public boolean activate() {
        if (!isAvailable()) {
            throw new IllegalStateException("Skill already used this turn.");
        } else if (getBoard() == null) {
            throw new IllegalStateException("Skill not initialized with a board.");
        }
        if (!super.activate()) { return false; }

        setAvailable(false);
        for (int i = 0; i < getBoard().getNbCells(); i++) {
            List<Warrior> warriors = getBoard().getUnitsOnCell(i);
            for (Warrior w : warriors) {
                if (w.getColor() == getPlayer().getColor()) {
                    w.increaseMaxMovements();
                }
            }
        }
        return true;
    }
}
