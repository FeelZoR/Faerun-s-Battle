package me.feelzor.faerunbattle.utils;

import me.feelzor.faerunbattle.model.Castle;
import me.feelzor.faerunbattle.warriors.*;
import org.jetbrains.annotations.NotNull;

public class CommandUtils {
    /**
     * Show information about a warrior
     */
    public static void showInformation(@NotNull String request) {
        switch (request.toLowerCase()) {
            case "dwarf": case "d":
                showWarrior("Dwarf", Dwarf.COST);
                break;
            case "elf": case "e":
                showWarrior("Elf", Elf.COST);
                break;
            case "dwarf leader": case "dl":
                showWarrior("Dwarf Leader", DwarfLeader.COST);
                break;
            case "elf leader": case "el":
                showWarrior("Elf Leader", ElfLeader.COST);
                break;
            case "paladin": case "p":
                showWarrior("Paladin", "\nIt will provoke its enemies if its provocation level is below the average." +
                        "\nIt also has more health and defense than a regular warrior.", Paladin.COST);
                break;
            case "recruiter": case "r":
                showWarrior("Recruiter", "\nIt has 15% chances to change its target's color into yours for next turn.",
                        Recruiter.COST);
                break;
            case "healer": case "h":
                showWarrior("Healer", "\nIt has 40% chances to heal an ally by 20hp or all its allies by 10hp.",
                        Healer.COST);
                break;
            case "skill": case "skills": case "s":
                PrintUtils.printInfo("Skills", "Open the skills menu.", "Variable");
                break;
            case "empty": case "0":
                PrintUtils.printInfo("Empty", "Empties the training queue.", "None");
                break;
        }
    }

    private static void showWarrior(String name, int cost) {
        showWarrior(name, "", cost);
    }

    private static void showWarrior(String name, String desc, int cost) {
        desc = "Add a " + name + " to the warriors training queue." + desc;
        PrintUtils.printInfo(name, desc, Integer.toString(cost));
    }

    /**
     * Show information about a skill
     */
    public static void showSkill(@NotNull String request, @NotNull Castle player) {
        switch (request.toLowerCase()) {
            case "bargaining": case "b":
                PrintUtils.printInfo("Bargaining", "Increase resources received each turn by 1.",
                        String.valueOf(player.getSkill("Bargaining").getCost()));
                break;
            case "motivating call": case "mc":
                PrintUtils.printInfo("Motivating Call", "Motivate troops, giving them the strength to walk two cells this turn.",
                        String.valueOf(player.getSkill("MotivatingCall").getCost()));
                break;
            case "negotiations": case "n":
                PrintUtils.printInfo("Negotiations", "Negotiate training costs and reduce them by 1 for 3 turns (min 1).",
                        String.valueOf(player.getSkill("Negotiations").getCost()));
                break;
            case "intensive training": case "it":
                PrintUtils.printInfo("Intensive training", "Increase base strength of trained troops by 1 for 2 turns.\n" +
                        "Every additional use of this skill will increase the bonus by 1, for a reduced cost.",
                        String.valueOf(player.getSkill("IntensiveTraining").getCost()));
                break;
        }
    }
}