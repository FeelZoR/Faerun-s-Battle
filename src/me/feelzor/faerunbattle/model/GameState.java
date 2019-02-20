package me.feelzor.faerunbattle.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GameState {
    private Board board;
    private Castle blue;
    private Castle red;

    public GameState(Board b, Castle blue, Castle red) {
        this.board = b;
        this.blue = blue;
        this.red = red;
    }

    public Board getBoard() {
        return board;
    }

    public Castle getBlue() {
        return blue;
    }

    public Castle getRed() {
        return red;
    }

    @JsonCreator
    public static GameState create(@JsonProperty("board") Board b,
                                   @JsonProperty("blue") Castle blue,
                                   @JsonProperty("red") Castle red) {
        blue.setBoard(b);
        red.setBoard(b);
        return new GameState(b, blue, red);
    }
}
