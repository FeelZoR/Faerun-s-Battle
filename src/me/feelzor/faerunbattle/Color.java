package me.feelzor.faerunbattle;

public enum Color {
    BLUE("Blue"),
    RED("Red"),
    NONE("Neutral");

    String name;

    Color(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
