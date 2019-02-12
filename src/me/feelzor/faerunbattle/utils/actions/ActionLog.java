package me.feelzor.faerunbattle.utils.actions;

import me.feelzor.faerunbattle.warriors.Warrior;
import org.jetbrains.annotations.NotNull;

public abstract class ActionLog {
    private final Warrior source, target;

    public ActionLog(@NotNull Warrior source, @NotNull Warrior target) {
        this.source = source;
        this.target = target;
    }

    @NotNull
    public Warrior getSource() {
        return source;
    }

    @NotNull
    public Warrior getTarget() {
        return target;
    }

    public abstract String getLog();
}
