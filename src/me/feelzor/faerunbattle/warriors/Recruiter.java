package me.feelzor.faerunbattle.warriors;

import me.feelzor.faerunbattle.Color;

public class Recruiter extends Warrior {
    public Recruiter() {
        super();
    }

    public Recruiter(Color col) {
        super(col);
        this.setHealthPoints(60);
    }

    @Override
    public int getCost() {
        return 5;
    }

    @Override
    public void attack(Warrior warrior) {
        if (Math.random() * 100 <= 15) {
            System.out.println(this + " recruits " + warrior + " !");
            warrior.setColor(this.getColor());
            this.increaseProvocation((int) (Math.random() * 20) + 30);
        } else {
            super.attack(warrior);
        }
    }
}
