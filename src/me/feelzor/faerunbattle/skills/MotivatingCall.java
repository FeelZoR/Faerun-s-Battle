package me.feelzor.faerunbattle.skills;

import me.feelzor.faerunbattle.model.Castle;
import me.feelzor.faerunbattle.model.Board;
import me.feelzor.faerunbattle.warriors.Warrior;

import java.util.List;

public class MotivatingCall extends Skill {

    private Board board;
    private boolean available;

    public MotivatingCall(Castle player) {
        this(player, null);
    }

    public MotivatingCall(Castle player, Board board) {
        super(player);
        setBoard(board);
        setAvailable(true);
    }

    @Override
    public int getCost() {
        return 2;
    }

    private Board getBoard() {
        return board;
    }

    private boolean isAvailable() {
        return available;
    }

    private void setBoard(Board board) {
        this.board = board;
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
