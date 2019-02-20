package me.feelzor.faerunbattle.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.feelzor.faerunbattle.model.Board;
import me.feelzor.faerunbattle.model.Castle;
import me.feelzor.faerunbattle.model.GameState;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class SaveUtils {
    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();
    private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void createSave(Board b, Castle blue, Castle red) {
        try {
            File save = new File(getSaveFileName());

            GameState state = new GameState(b, blue, red);
            JSON_MAPPER.writeValue(save, state);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getSaveFileName() {
        return "save" + new Date().getTime() + ".json";
    }

    public static List<File> getSavedFiles() {
        List<File> result = new ArrayList<>();
        File[] files = new File(".").listFiles();
        if (files == null) { return result; }

        result = new LinkedList<>(Arrays.asList(files));
        result.removeIf(f -> !f.getName().startsWith("save") || !f.getName().endsWith(".json"));
        result.sort((f1, f2) -> -f1.getName().compareTo(f2.getName())); // The most recent first

        return result;
    }

    public static String getDate(String filename) {
        if (!filename.startsWith("save") || !filename.endsWith(".json")) {
            throw new IllegalArgumentException("This is not a save file !");
        }

        return DATE_FORMATTER.format(new Date(Long.valueOf(filename.substring(4, filename.length() - 5))));
    }

    public static GameState loadGame(File save) {
        GameState load = null;
        try {
            load = JSON_MAPPER.readValue(save, GameState.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return load;
    }

    public static boolean deleteGame(List<File> saveFiles, int index) {
        return saveFiles.remove(index).delete();
    }
}
