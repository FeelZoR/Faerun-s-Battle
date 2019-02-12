package me.feelzor.faerunbattle.utils.actions;

import me.feelzor.faerunbattle.warriors.Warrior;
import org.jetbrains.annotations.NotNull;

public class RecruitmentLog extends ActionLog {
    public RecruitmentLog(@NotNull Warrior source, @NotNull Warrior target) {
        super(source, target);
    }

    @Override
    public String getLog() {
        return getSource() + " recruited " + getTarget().getName();
    }
}
