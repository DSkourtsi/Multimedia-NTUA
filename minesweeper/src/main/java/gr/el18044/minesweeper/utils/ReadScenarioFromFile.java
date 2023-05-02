package gr.el18044.minesweeper.utils;

import gr.el18044.minesweeper.exceptions.InvalidDescriptionException;
import gr.el18044.minesweeper.exceptions.InvalidValueException;
import gr.el18044.minesweeper.Scenario;
import gr.el18044.minesweeper.DifficultySettings;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class ReadScenarioFromFile {
    private final Scenario scenario;
    public ReadScenarioFromFile(String name) throws InvalidValueException, InvalidDescriptionException, IOException {
        String filename = name + ".txt";
        var filepath = "medialab/scenarios/" + filename;
        Path path = Paths.get(filepath);
        final List<String> lines;
        try {
            lines = Files.readAllLines(path);
        } catch (IOException e) {
            throw new IOException();
        }
        if (lines.size() != 4) throw new InvalidDescriptionException();

        int difficulty, numMines, totalTime;
        boolean hasSupermine;
        try {
            difficulty = Integer.parseInt(lines.get(0));
            numMines = Integer.parseInt(lines.get(1));
            totalTime = Integer.parseInt(lines.get(2));
            hasSupermine = Integer.parseInt(lines.get(3)) != 0;
        } catch (NumberFormatException e){
            throw new InvalidValueException();
        }
        var difficultySettings = new DifficultySettings(difficulty);
        var gameParams = difficultySettings.getSettings();
        var scenario = new Scenario(numMines, totalTime, hasSupermine, gameParams);
        if (scenario.isValid()) {
            this.scenario = scenario;
        }
        else {
            throw new InvalidValueException();
        }
    }
    public Scenario getScenario() {
        return scenario;
    }
}
