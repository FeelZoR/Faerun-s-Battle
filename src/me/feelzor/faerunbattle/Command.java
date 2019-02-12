package me.feelzor.faerunbattle;

import me.feelzor.faerunbattle.model.Castle;
import me.feelzor.faerunbattle.warriors.*;
import org.jetbrains.annotations.NotNull;

public class Command {
    /**
     * Show information about a warrior
     */
    public static void showInformation(@NotNull String request) {
        switch (request.toLowerCase()) {
            case "dwarf": case "d":
                showWarrior("Dwarf");
                break;
            case "elf": case "e":
                showWarrior("Elf");
                break;
            case "dwarf leader": case "dl":
                showWarrior("Chef Dwarf");
                break;
            case "elf leader": case "el":
                showWarrior("Chef Elf");
                break;
            case "paladin": case "p":
                showWarrior("Paladin", "\nIt will provoke its enemies if its provocation level is below the average." +
                        "\nIt also has more health and defense than a regular warrior.");
                break;
            case "recruiter": case "r":
                showWarrior("Recruiter", "\nIt has 15% chances to change its target's color into yours for next turn.");
                break;
            case "healer": case "h":
                showWarrior("Healer", "\nIt has 40% chances to heal an ally by 20hp or all its allies by 10hp.");
                break;
            case "skill": case "skills": case "s":
                printInfo("Skills", "Open the skills menu.", "Variable");
                break;
            case "empty": case "0":
                printInfo("Empty", "Empties the training queue.", "None");
                break;
        }
    }

    private static void showWarrior(String name) {
        showWarrior(name, "");
    }

    private static void showWarrior(String name, String desc) {
        try {
            Warrior w = (Warrior) Class.forName("me.feelzor.faerunbattle.warriors." + name.replaceAll("\\s", "")).newInstance();
            desc = "Add a " + name + " to the warriors training queue." + desc;
            printInfo(name, desc, Integer.toString(w.getCost()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Show information about a skill
     */
    public static void showSkill(@NotNull String request, @NotNull Castle player) {
        switch (request.toLowerCase()) {
            case "bargaining": case "b":
                printInfo("Bargaining", "Increase resources received each turn by 1.",
                        String.valueOf(player.getSkill("Bargaining").getCost()));
                break;
            case "motivating call": case "mc":
                printInfo("Motivating Call", "Motivate troops, giving them the strength to walk two cells this turn.",
                        String.valueOf(player.getSkill("MotivatingCall").getCost()));
                break;
            case "negotiations": case "n":
                printInfo("Negotiations", "Negotiate training costs and reduce them by 1 for 3 turns (min 1).",
                        String.valueOf(player.getSkill("Negotiations").getCost()));
                break;
            case "intensive training": case "it":
                printInfo("Intensive training", "Increase base strength of trained troops by 1 for 2 turns.\n" +
                        "Every additional use of this skill will increase the bonus by 1, for a reduced cost.",
                        String.valueOf(player.getSkill("IntensiveTraining").getCost()));
                break;
        }
    }

    /**
     * Show information
     * @param title The information's title
     * @param description The information's description
     * @param cost The usage cost
     */
    private static void printInfo(String title, String description, String cost) {
        System.out.println("----- Information | " + title + " -----");
        System.out.println("Cost : " + cost);
        System.out.println(description);
    }
}