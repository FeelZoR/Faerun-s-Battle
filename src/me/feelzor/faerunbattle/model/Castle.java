package me.feelzor.faerunbattle.model;

import me.feelzor.faerunbattle.Color;
import me.feelzor.faerunbattle.warriors.WarriorType;
import me.feelzor.faerunbattle.skills.*;
import me.feelzor.faerunbattle.warriors.*;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Queue;
import java.util.logging.Logger;

public class Castle {
    private final static Logger LOGGER = Logger.getLogger(Castle.class.getName());
    private HashMap<String, Skill> skills;
    private Queue<Warrior> warriorsQueue;
    private Color color;
    private Board board;

    private int resources;
    private int resourcesOnNewTurn;

    public Castle(Color col, Board board) {
        this.warriorsQueue = new ArrayDeque<>();
        this.skills = new HashMap<>();
        setColor(col);
        setBoard(board);
        setResources(3);
        setResourcesOnNewTurn(1);

        addSkill("bargaining", new Bargaining(this));
        addSkill("motivating call", new MotivatingCall(this, getBoard()));
        addSkill("negotiations", new Negotiations(this));
        addSkill("intensive training", new IntensiveTraining(this));
    }

    /**
     * Get the castle's color
     * @return RED ou BLUE
     */
    public Color getColor() {
        return color;
    }

    private void setColor(Color color) {
        if (color == Color.NONE) {
            LOGGER.warning("Illegal try to set castle color to NONE.");
            return;
        }

        this.color = color;
    }

    /**
     * @return The number of castle's resources
     */
    public int getResources() {
        return this.resources;
    }

    /**
     * Set the number of resources in the castle
     * @param nbResources The new amount of resources
     */
    private void setResources(int nbResources) {
        this.resources = Math.max(nbResources, 0);
    }

    /**
     * @return The number of resources earned at the beginning of each turn
     */
    public int getResourcesOnNewTurn() {
        return this.resourcesOnNewTurn;
    }

    /**
     * Set the number of resources earned at the beginning of each turn
     */
    private void setResourcesOnNewTurn(int newAmount) {
        this.resourcesOnNewTurn = Math.max(newAmount, 0);
    }

    /**
     * @return The game's board
     */
    private Board getBoard() {
        return board;
    }

    /**
     * Sets the game's board
     */
    private void setBoard(Board board) {
        this.board = board;
    }

    /**
     * @return The training queue
     */
    private Queue<Warrior> getWarriorsQueue() {
        return warriorsQueue;
    }

    /**
     * @return The list of skills
     */
    private HashMap<String, Skill> getSkills() {
        return skills;
    }

    /**
     * Add a new skill in the castle
     * @param nom The skill's identifier
     * @param s The skill
     */
    private void addSkill(String nom, Skill s) {
        skills.put(nom, s);
    }

    /**
     * @param name The skill's name
     * @return The skill found
     */
    public Skill getSkill(String name) {
        if (!getSkills().containsKey(name.toLowerCase())) {
            LOGGER.warning("Illegal try to get non-existent skill.");
            return null;
        }

        return getSkills().get(name.toLowerCase());
    }

    /**
     * Consumes a certain number of resources
     * @param amount The number of resources to consume, must be less than the number of resources of castle
     */
    public void consumeResources(int amount) {
        if (amount < 0 || amount > getResources()) {
            LOGGER.warning("Illegal try to consume " + amount + " resources. Available resources : " + getResources());
            return;
        }

        setResources(getResources() - amount);
    }

    /**
     * Increases the number of resources earned each turn
     */
    public void increaseResourcesOnNewTurn() {
        setResourcesOnNewTurn(getResourcesOnNewTurn() + 1);
    }

    /**
     * Increases the castle's resources using the number of resources earned each turn
     */
    private void getTurnResources() {
        setResources(getResources() + getResourcesOnNewTurn());
    }

    /**
     * Add a warrior to the training queue
     */
    public void addTraining(WarriorType type) {
        int bonusStrength = 0;
        if (this.getSkill("Intensive Training").isActive()) {
            bonusStrength = ((IntensiveTraining) this.getSkill("Intensive Training")).getBoost();
        }

        switch (type) {
            case ELF:
                addUnit(new Elf(getColor(), bonusStrength));
                break;
            case DWARF:
                addUnit(new Dwarf(getColor(), bonusStrength));
                break;
            case ELF_LEADER:
                addUnit(new ElfLeader(getColor(), bonusStrength));
                break;
            case DWARF_LEADER:
                addUnit(new DwarfLeader(getColor(), bonusStrength));
                break;
            case PALADIN:
                addUnit(new Paladin(getColor(), bonusStrength));
                break;
            case RECRUITER:
                addUnit(new Recruiter(getColor()));
                break;
            case HEALER:
                addUnit(new Healer(getColor()));
                break;
        }

        System.out.println("A " + type + " has been added to the training queue.");
    }

    private void addUnit(Warrior warrior) {
        getWarriorsQueue().add(warrior);
    }

    /**
     * Cancel all trainings in the queue
     */
    public void cancelTrainings() {
        getWarriorsQueue().clear();
        System.out.println("Training queue has been emptied.");
    }

    /**
     * Trains as much troops as possible with current number of resources
     */
    public void trainUnits() {
        Queue<Warrior> queue = getWarriorsQueue();
        while (queue.peek() != null && queue.peek().getCost() <= getResources()) {
            Warrior w = queue.remove();
            w.move(); // tells the troop that it has moved during this turn once
            getBoard().addUnit(w);
            int cost = (this.getSkill("negotiations").isActive()) ? Math.max(w.getCost() - 1, 1) : w.getCost();
            consumeResources(cost);
        }
    }

    /**
     * Starts a new turn
     */
    public void debutTour() {
        this.getTurnResources();

        for (Skill skill : getSkills().values()) {
            skill.newTurn();
        }
    }
}
