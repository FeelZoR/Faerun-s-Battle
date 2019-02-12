package me.feelzor.faerunbattle.utils.actions;

import me.feelzor.faerunbattle.model.Board;
import me.feelzor.faerunbattle.utils.PrintUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Scanner;

public class PromptUtils {
    private static final Scanner IN = new Scanner(System.in);

    @NotNull
    public static Board promptBoardSize() {
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
}
