package me.feelzor.faerunbattle.utils.actions;

import me.feelzor.faerunbattle.warriors.Warrior;
import org.jetbrains.annotations.NotNull;

public class HealingLog extends ActionLog {
    private final int healingAmount;

    public HealingLog(@NotNull Warrior healer, @NotNull Warrior healed, int healingAmount) {
        super(healer, healed);
        this.healingAmount = healingAmount;
    }

    public int getHealingAmount() {
        return healingAmount;
    }

    @Override
    public String getLog() {
        return getSource() + " healed " + getTarget().getName() + " by " + getHealingAmount() + " hp (new amount : " +
                getTarget().getHealthPoints() + ").";
    }
}
