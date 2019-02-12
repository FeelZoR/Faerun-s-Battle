package me.feelzor.faerunbattle.utils.actions;

import me.feelzor.faerunbattle.warriors.Warrior;
import org.jetbrains.annotations.NotNull;

public class ProvocationLog extends ActionLog {

    public ProvocationLog(@NotNull Warrior source) {
        super(source, source);
    }

    @Override
    public String getLog() {
        return getSource() + " provoked their enemies!";
    }
}
