package me.feelzor.faerunbattle.utils;

import me.feelzor.faerunbattle.Color;
import me.feelzor.faerunbattle.model.Board;
import me.feelzor.faerunbattle.model.Castle;
import me.feelzor.faerunbattle.model.GameState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.List;
import java.util.Scanner;

public class PromptUtils {
    private static final Scanner IN = new Scanner(System.in);

    @Nullable
    public static GameState startGame() {
        List<File> saveFiles = SaveUtils.getSavedFiles();
        GameState result = null;

        boolean actionsTerminated = false;
        while (!actionsTerminated) {
            PrintUtils.printMainMenuOptions();
            actionsTerminated = true;
            String action = IN.nextLine();
            switch (action.toLowerCase()) {
                case "new game": case "new": case "n":
                    Board b = promptBoardSize();
                    result = new GameState(b, new Castle(Color.BLUE, b), new Castle(Color.RED, b));
                    break;
                case "load game": case "load": case "l":
                    result = promptLoadFile(saveFiles);
                    if (result == null) {
                        actionsTerminated = false;
                    }
                    break;
                case "delete save": case "delete": case "d":
                    actionsTerminated = false;
                    promptDeleteFile(saveFiles);
                    break;
                case "quit": case "q":
                    break;
                default:
                    actionsTerminated = false;
            }
        }

        return result;
    }

    @NotNull
    private static Board promptBoardSize() {
        Board board;
        while (true) {
            try {
                System.out.print("Enter the board's number of cells (5 / 10 / 15) : ");
                int nbCells = IN.nextInt();
                IN.nextLine();
                board = new Board(nbCells);
                break;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return board;
    }

    @Nullable
    private static GameState promptLoadFile(List<File> saveFiles) {
        GameState result = null;
        if (saveFiles.size() != 0) {
            PrintUtils.listSaveFiles(saveFiles);
            int saveId = getInteger("Enter the save number: ", 1, saveFiles.size()) - 1;

            result = SaveUtils.loadGame(saveFiles.get(saveId));
        }

        return result;
    }

    private static void promptDeleteFile(List<File> saveFiles) {
        if (saveFiles.size() != 0) {
            PrintUtils.listSaveFiles(saveFiles);
            int saveId = getInteger("Enter the save number: ", 1, saveFiles.size()) - 1;

            if (SaveUtils.deleteGame(saveFiles, saveId)) {
                PrintUtils.printDeleteSuccess();
            } else {
                PrintUtils.printDeleteFailed();
            }
        }
    }

    @NotNull
    public static String getUserAction() {
        PrintUtils.newLine();
        PrintUtils.printActionOptions();
        return IN.nextLine();
    }

    @NotNull
    public static String getSkillAction() {
        PrintUtils.newLine();
        PrintUtils.printSkillOptions();
        return IN.nextLine();
    }

    public static int getInteger(String promptMessage, int min, int max) {
        int result;
        do {
            System.out.print(promptMessage);
            result = IN.nextInt();
            IN.nextLine();
        } while (result < min || result > max);

        return result;
    }
}
