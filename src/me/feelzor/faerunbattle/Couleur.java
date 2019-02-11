package me.feelzor.faerunbattle;

public enum Couleur {
    BLEU("Bleu"),
    ROUGE("Rouge"),
    AUCUN("Neutre");

    String name;

    Couleur(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
