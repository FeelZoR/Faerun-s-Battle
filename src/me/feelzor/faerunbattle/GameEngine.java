package me.feelzor.faerunbattle;

import me.feelzor.faerunbattle.model.Castle;
import me.feelzor.faerunbattle.model.Board;
import me.feelzor.faerunbattle.warriors.Warrior;
import me.feelzor.faerunbattle.warriors.WarriorType;

import java.util.List;
import java.util.Scanner;

public class GameEngine {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Board board;

        while (true) {
            try {
                System.out.print("Enter the board's number of cells (5 / 10 / 15) : ");
                int nbCells = in.nextInt();
                in.nextLine();
                board = new Board(nbCells);
                break;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Castle blue = new Castle(Color.BLUE, board);
        Castle red = new Castle(Color.RED, board);

        while (board.getCellColor(0) != Color.RED
                && board.getCellColor(board.getNbCells() - 1) != Color.BLUE) {
            newTurn(board, blue, red);
            gameTurn(blue, in);
            gameTurn(red, in);
            board.moveUnits();
            board.startFights();
        }

        if (board.getCellColor(0) == Color.RED) {
            System.out.println("Red wins !");
        } else {
            System.out.println("Blue wins !");
        }
    }

    /**
     * Start a new turn
     * @param blue The blue team's castle
     * @param red The red team's castle
     */
    private static void newTurn(Board board, Castle blue, Castle red) {
        System.out.println("-------- New Turn --------");

        board.showBoard();
        for (int i = 0; i < board.getNbCells(); i++) {
            List<Warrior> units = board.getUnitsOnCell(i);
            for (Warrior w : units) {
                w.reinitializeMovements();
            }
        }

        blue.debutTour();
        red.debutTour();
    }

    /**
     * Start a new turn for a castle
     */
    private static void gameTurn(Castle player, Scanner in) {
        System.out.println(player.getColor() + " team's turn.");
        System.out.println("You have " + player.getResources() + " resources.");

        String action;
        do {
            System.out.println();
            System.out.println("Action (start with \"info\" to get more information)");
            System.out.println("Dwarf (d), Elf (e), Dwarf Leader (dl), Elf Leader (el), Paladin (p), Recruiter (r), Healer (h), Skill (s), Empty (0), Quit (q)");
            action = in.nextLine();

            switch (action.toLowerCase()) {
                case "elf": case "e":
                    player.addTraining(WarriorType.ELF);
                    break;
                case "dwarf": case "d":
                    player.addTraining(WarriorType.DWARF);
                    break;
                case "elf leader": case "el":
                    player.addTraining(WarriorType.ELF_LEADER);
                    break;
                case "dwarf leader": case "dl":
                    player.addTraining(WarriorType.DWARF_LEADER);
                    break;
                case "paladin": case "p":
                    player.addTraining(WarriorType.PALADIN);
                    break;
                case "recruiter": case "r":
                    player.addTraining(WarriorType.RECRUITER);
                    break;
                case "healer": case "h":
                    player.addTraining(WarriorType.HEALER);
                    break;
                case "skill": case "skills": case "s":
                    skillsMenu(player, in);
                    break;
                case "empty": case "0":
                    player.cancelTrainings();
                    break;
            }

            if (action.toLowerCase().startsWith("info ")) { Command.showInformation(action.substring(5)); }
        } while (!action.equalsIgnoreCase("quit") && !action.equalsIgnoreCase("q"));

        player.trainUnits();
    }

    /**
     * Open the skill menu
     */
    private static void skillsMenu(Castle player, Scanner in) {
        boolean isSkillValid;
        String action;
        do {
            isSkillValid = true;
            System.out.println();
            System.out.println("Skill to use (start with \"info\" to get more information)");
            System.out.println("Bargaining (b), Motivating Call (mc), Negotiations (n), Intensive Training (it), Quit (q)");
            action = in.nextLine();

            try {
                switch (action.toLowerCase()) {
                    case "bargaining": case "b":
                        useSkill(player, "Bargaining");
                        break;
                    case "motivating call": case "mc":
                        useSkill(player, "Motivating Call");
                        break;
                    case "negotiations": case "n":
                        useSkill(player, "Negotiations");
                        break;
                    case "intensive training": case "it":
                        useSkill(player, "Intensive Training");
                        break;
                    case "quit": case "q":
                        break;
                    default:
                        isSkillValid = false;
                }
            } catch (IllegalStateException e) {
                System.out.print(e.getMessage());
            }

            if (action.toLowerCase().startsWith("info ")) { Command.showSkill(action.substring(5), player); }
        } while (!isSkillValid);
    }

    /**
     * Try to use a skill
     */
    private static void useSkill(Castle player, String skillName) {
        if (player.getSkill(skillName).activate()) {
            System.out.println(skillName + " activated ! You have " + player.getResources() + " resources left.");
        } else {
            System.out.print("Not enough resources to use this skill !");
        }
    }
}
