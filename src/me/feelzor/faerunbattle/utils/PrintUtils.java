package me.feelzor.faerunbattle.utils;

import me.feelzor.faerunbattle.Color;
import me.feelzor.faerunbattle.model.Board;
import me.feelzor.faerunbattle.model.Castle;
import me.feelzor.faerunbattle.model.Cell;
import me.feelzor.faerunbattle.utils.actions.*;
import me.feelzor.faerunbattle.warriors.Warrior;
import me.feelzor.faerunbattle.warriors.WarriorType;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public class PrintUtils {

    ///////////////////////////////
    //      Object printing      //
    ///////////////////////////////
    public static void logFight(@NotNull List<ActionLog> actions) {
        for (ActionLog action : actions) {
            System.out.println(action.getLog());
        }
    }

    public static void printBoard(@NotNull Board board) {
        for (int i = 0; i < board.getNbCells(); i++) {
            System.out.println("------- Cell " + (i+1) + " -------");
            Cell c = Objects.requireNonNull(board.getCellAt(i));
            c.sortUnits();
            for (Warrior w : c.getUnits()) {
                System.out.println(w);
            }

            if (i < 10) { System.out.println("----------------------"); }
            else { System.out.println("-----------------------"); }
        }
    }


    ////////////////////////////////
    //    Interaction Messages    //
    ////////////////////////////////

    public static void printCastleTurn(@NotNull Castle castle) {
        System.out.println(castle.getColor() + " team's turn.");
        System.out.println("You have " + castle.getResources() + " resources.");
    }

    public static void printActionOptions() {
        System.out.println("Action (start with \"info\" to get more information)");
        System.out.println("Dwarf (d), Elf (e), Dwarf Leader (dl), Elf Leader (el), Paladin (p), Recruiter (r), Healer (h), Skill (s), Empty (0), Quit (q)");
    }

    public static void printSkillOptions() {
        System.out.println("Skill to use (start with \"info\" to get more information)");
        System.out.println("Bargaining (b), Motivating Call (mc), Negotiations (n), Intensive Training (it), Quit (q)");
    }

    ///////////////////////////////
    //    Interaction Answers    //
    ///////////////////////////////

    public static void printSkillError(@NotNull String errorMessage) {
        System.out.println(errorMessage);
    }

    public static void printSkillActivation(@NotNull String skillName, int resourcesLeft) {
        System.out.println(skillName + " activated! You have " + resourcesLeft + " resources left.");
    }

    public static void printNotEnoughResources() {
        System.out.println("You do not have enough resources to do that!");
    }

    public static void printTroopAdded(WarriorType type) {
        System.out.println("A new " + type + " has been added to the training queue.");
    }

    public static void printTrainingQueueEmptied() {
        System.out.println("Training queue has been emptied.");
    }

    public static void printInfo(@NotNull String title, @NotNull String description, @NotNull String cost) {
        System.out.println("----- Information | " + title + " -----");
        System.out.println("Cost: " + cost);
        System.out.println(description);
    }


    ////////////////////////////////
    //    Game Status Messages    //
    ////////////////////////////////

    public static void printNewTurn() {
        System.out.println("-------- New Turn --------");
    }

    public static void printVictoryMessage(@NotNull Color winner) {
        if (winner == Color.RED) {
            System.out.println("Red wins!");
        } else {
            System.out.println("Blue wins!");
        }
    }


    ///////////////////////////////
    //           Utils           //
    ///////////////////////////////
    public static void newLine() {
        System.out.println();
    }
}
