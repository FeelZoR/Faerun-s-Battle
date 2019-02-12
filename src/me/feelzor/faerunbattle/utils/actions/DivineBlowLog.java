package me.feelzor.faerunbattle.utils.actions;

import me.feelzor.faerunbattle.warriors.Warrior;
import org.jetbrains.annotations.NotNull;

public class DivineBlowLog extends ActionLog {

    public DivineBlowLog(@NotNull Warrior source) {
        super(source, source);
    }

    @Override
    public String getLog() {
        return "Divine blow! Everyone's dead.";
    }
}
