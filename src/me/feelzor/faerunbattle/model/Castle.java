package me.feelzor.faerunbattle.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import me.feelzor.faerunbattle.Color;
import me.feelzor.faerunbattle.utils.PrintUtils;
import me.feelzor.faerunbattle.warriors.WarriorType;
import me.feelzor.faerunbattle.skills.*;
import me.feelzor.faerunbattle.warriors.*;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Queue;

public class Castle {

    @JsonProperty("skills")
    private final HashMap<String, Skill> skills;
    private final Queue<Warrior> warriorsQueue;
    private final Color color;
    private Board board;

    private int resources;
    private int resourcesOnNewTurn;

    public Castle(@NotNull Color col, @NotNull Board board) {
        if (col == Color.NONE) {
            throw new IllegalArgumentException("Illegal try to set castle color to NONE.");
        }

        this.warriorsQueue = new ArrayDeque<>();
        this.skills = new HashMap<>();
        this.color = col;

        addSkill("bargaining", new Bargaining(this));
        addSkill("motivating call", new MotivatingCall(this));
        addSkill("negotiations", new Negotiations(this));
        addSkill("intensive training", new IntensiveTraining(this));

        setBoard(board);
        setResources(3);
        setResourcesOnNewTurn(1);
    }

    @JsonCreator
    private Castle (
            @JsonProperty("color") @NotNull Color col,
            @JsonProperty("resources") int resources,
            @JsonProperty("resourcesOnNewTurn") int resourcesOnNewTurn,
            @JsonProperty("skills") HashMap<String, Skill> skills
    ) {
        this.color = col;
        this.skills = skills;
        setResources(resources);
        setResourcesOnNewTurn(resourcesOnNewTurn);

        this.warriorsQueue = new ArrayDeque<>();
        for (Skill s : skills.values()) {
            s.setPlayer(this);
        }
    }

    /**
     * Get the castle's color
     * @return RED ou BLUE
     */
    @NotNull
    public Color getColor() {
        return color;
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

    void setBoard(Board board) {
        this.board = board;
        ((MotivatingCall) getSkill("motivating call")).setBoard(board);
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
    @NotNull
    public Skill getSkill(@NotNull String name) {
        if (!getSkills().containsKey(name.toLowerCase())) {
            throw new IllegalArgumentException("Illegal try to get non-existent skill.");
        }

        return getSkills().get(name.toLowerCase());
    }

    /**
     * Consumes a certain number of resources
     * @param amount The number of resources to consume, must be less than the number of resources of castle
     */
    public void consumeResources(int amount) {
        if (amount < 0 || amount > getResources()) {
            throw new IllegalArgumentException("Illegal try to consume " + amount + " resources. Available resources : " + getResources());
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
    public void addTraining(@NotNull WarriorType type) {
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

        PrintUtils.printTroopAdded(type);
    }

    private void addUnit(Warrior warrior) {
        getWarriorsQueue().add(warrior);
    }

    /**
     * Cancel all trainings in the queue
     */
    public void cancelTrainings() {
        getWarriorsQueue().clear();
        PrintUtils.printTrainingQueueEmptied();
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
    public void beginTurn() {
        for (Skill skill : getSkills().values()) {
            skill.newTurn();
        }
    }

    public void endTurn() {
        this.getTurnResources();
    }
}
