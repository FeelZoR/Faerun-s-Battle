package me.feelzor.faerunbattle;

import me.feelzor.faerunbattle.model.Castle;
import me.feelzor.faerunbattle.model.Board;
import me.feelzor.faerunbattle.utils.CommandUtils;
import me.feelzor.faerunbattle.utils.PrintUtils;
import me.feelzor.faerunbattle.utils.actions.PromptUtils;
import me.feelzor.faerunbattle.warriors.Warrior;
import me.feelzor.faerunbattle.warriors.WarriorType;

import java.util.List;

public class GameEngine {
    public static void main(String[] args) {
        Board board = PromptUtils.promptBoardSize();

        Castle blue = new Castle(Color.BLUE, board);
        Castle red = new Castle(Color.RED, board);

        while (board.getCellColor(0) != Color.RED
                && board.getCellColor(board.getNbCells() - 1) != Color.BLUE) {
            newTurn(board, blue, red);
            gameTurn(blue);
            gameTurn(red);
            board.moveUnits();
            board.startFights();
        }

        PrintUtils.printVictoryMessage(board.getCellColor(0) == Color.RED ? Color.RED : Color.BLUE);
    }

    /**
     * Start a new turn
     * @param blue The blue team's castle
     * @param red The red team's castle
     */
    private static void newTurn(Board board, Castle blue, Castle red) {
        PrintUtils.printNewTurn();
        PrintUtils.printBoard(board);
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
    private static void gameTurn(Castle player) {
        PrintUtils.printCastleTurn(player);

        String action;
        do {
            action = PromptUtils.getUserAction();

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
                    skillsMenu(player);
                    break;
                case "empty": case "0":
                    player.cancelTrainings();
                    break;
            }

            if (action.toLowerCase().startsWith("info ")) { CommandUtils.showInformation(action.substring(5)); }
        } while (!action.equalsIgnoreCase("quit") && !action.equalsIgnoreCase("q"));

        player.trainUnits();
    }

    /**
     * Open the skill menu
     */
    private static void skillsMenu(Castle player) {
        boolean isSkillValid;
        String action;
        do {
            isSkillValid = true;
            action = PromptUtils.getSkillAction();

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
                PrintUtils.printSkillError(e.getMessage());
            }

            if (action.toLowerCase().startsWith("info ")) { CommandUtils.showSkill(action.substring(5), player); }
        } while (!isSkillValid);
    }

    /**
     * Try to use a skill
     */
    private static void useSkill(Castle player, String skillName) {
        if (player.getSkill(skillName).activate()) {
            PrintUtils.printSkillActivation(skillName, player.getResources());
        } else {
            PrintUtils.printNotEnoughResources();
        }
    }
}
