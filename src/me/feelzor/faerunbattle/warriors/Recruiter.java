package me.feelzor.faerunbattle.warriors;

import me.feelzor.faerunbattle.Color;
import me.feelzor.faerunbattle.utils.RandomUtils;
import me.feelzor.faerunbattle.utils.actions.ActionLog;
import me.feelzor.faerunbattle.utils.actions.RecruitmentLog;
import org.jetbrains.annotations.NotNull;

public class Recruiter extends Warrior {
    public static final int COST = 5;

    public Recruiter(@NotNull Color col) {
        super(col);
        this.setHealthPoints(60);
    }

    @Override
    public int getCost() {
        return COST;
    }

    @Override
    @NotNull
    public ActionLog attack(@NotNull Warrior warrior) {
        if (RandomUtils.generateNumber(100) <= 15) {
            warrior.setColor(this.getColor());
            this.increaseProvocation(RandomUtils.generateNumber(30, 50));
            return new RecruitmentLog(this, warrior);
        } else {
            return super.attack(warrior);
        }
    }
}
