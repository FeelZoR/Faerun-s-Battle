package me.feelzor.faerunbattle.warriors;

import me.feelzor.faerunbattle.Color;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Healer extends Warrior {

    public Healer() {
        super();
    }

    public Healer(@NotNull Color col) {
        super(col);
    }

    @Override
    public int getStrength() {
        return (int) (super.getStrength() / 1.5);
    }

    @Override
    protected void damage(int amount) {
        super.damage((int) (amount * 1.5));
    }

    @Override
    public int getCost() {
        return 5;
    }

    @Override
    public void startTurn(@NotNull List<Warrior> warriors) {
        int nb = (int) (Math.random() * 100);
        Warrior target = this.mostInjured(warriors);
        if (nb >= 40 || target == null) { // Attacks (60%)
            super.startTurn(warriors);
        } else if (nb != 1) { // Heals the most injured ally (39%)
            target.setHealthPoints(target.getHealthPoints() + 20);
            this.increaseProvocation((int) (Math.random() * 30) + 10);
            System.out.println(this + " nursed " + target);
        } else { // Heals all allies (1%)
            for (Warrior w : warriors) {
                if (w.getColor() == this.getColor()) {
                    w.setHealthPoints(w.getHealthPoints() + 10);
                    this.increaseProvocation((int) (Math.random() * 20) + 40);
                    System.out.println(this + " nursed all his allied !");
                }
            }
        }
    }

    /**
     * @param warriors The list of warriors on the cell
     * @return The most injured ally
     */
    private Warrior mostInjured(List<Warrior> warriors) {
        if (warriors.size() == 0) return null;

        List<Warrior> targets = new ArrayList<>(warriors);
        targets.removeIf(w -> w.getColor() != this.getColor());
        targets.sort((w1, w2) -> w2.getHealthPoints() - w1.getHealthPoints());

        return targets.get(0);
    }
}
