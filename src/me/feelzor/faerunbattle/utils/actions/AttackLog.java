package me.feelzor.faerunbattle.utils.actions;

import me.feelzor.faerunbattle.warriors.Warrior;
import org.jetbrains.annotations.NotNull;

public class AttackLog extends ActionLog {
    private final int baseDamage, realDamage, remainingHp;

    public AttackLog(@NotNull Warrior attacker, @NotNull Warrior defender, int baseDamage, int realDamage, int remainingHp) {
        super(attacker, defender);
        this.baseDamage = baseDamage;
        this.realDamage = realDamage;
        this.remainingHp = remainingHp;
    }

    public int getBaseDamage() {
        return baseDamage;
    }

    public int getRealDamage() {
        return realDamage;
    }

    public int getRemainingHp() {
        return remainingHp;
    }

    @Override
    public String getLog() {
        return getSource() + " attacked " + getTarget().getColor() + " " + getTarget().getName() + " for " + getRealDamage() +
                " damage (" + getBaseDamage() + " base damage). Remaining hp: " + getRemainingHp() + ".";
    }
}
