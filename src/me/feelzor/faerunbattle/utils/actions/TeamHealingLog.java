package me.feelzor.faerunbattle.utils.actions;

import me.feelzor.faerunbattle.warriors.Warrior;
import org.jetbrains.annotations.NotNull;

public class TeamHealingLog extends HealingLog {

    public TeamHealingLog(@NotNull Warrior healer, int healingAmount) {
        super(healer, healer, healingAmount);
    }

    @Override
    public String getLog() {
        return getSource() + " healed their whole team by " + getHealingAmount() + ".";
    }
}
