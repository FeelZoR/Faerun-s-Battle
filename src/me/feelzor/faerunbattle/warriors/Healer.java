package me.feelzor.faerunbattle.warriors;

import me.feelzor.faerunbattle.Color;
import me.feelzor.faerunbattle.utils.RandomUtils;
import me.feelzor.faerunbattle.utils.actions.ActionLog;
import me.feelzor.faerunbattle.utils.actions.HealingLog;
import me.feelzor.faerunbattle.utils.actions.TeamHealingLog;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Healer extends Warrior {
    public static final int COST = 5;

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
        return COST;
    }

    @Override
    @Nullable
    public ActionLog startTurn(@NotNull List<Warrior> warriors) {
        int nb = RandomUtils.generateNumber(100);
        Warrior target = this.mostInjured(warriors);
        if (nb >= 40 || target == null) { // Attacks (60%)
            return super.startTurn(warriors);
        } else if (nb != 1) { // Heals the most injured ally (39%)
            int healingAmount = 20;
            target.setHealthPoints(target.getHealthPoints() + healingAmount);
            this.increaseProvocation(RandomUtils.generateNumber(10, 40));
            return new HealingLog(this, target, healingAmount);
        } else { // Heals all allies (1%)
            int healingAmount = 10;
            for (Warrior w : warriors) {
                if (w.getColor() == this.getColor()) {
                    w.setHealthPoints(w.getHealthPoints() + healingAmount);
                    this.increaseProvocation(RandomUtils.generateNumber(40, 60));
                }
            }
            return new TeamHealingLog(this, healingAmount);
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
