package gr.el18044.minesweeper.utils;

import gr.el18044.minesweeper.Scenario;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WriteScenarioToFile {
    public WriteScenarioToFile(String name, Scenario scenario){
        String filename = name + ".txt";
        var filepath = "medialab/scenarios/" + filename;
        File file = new File(filepath);
        try {
            FileWriter writer = new FileWriter(file);
            writer.write(scenario.params().getDifficulty() + "\n");
            writer.write(scenario.numMines() + "\n");
            writer.write(scenario.totalTime() + "\n");
            writer.write(scenario.hasSupermine() ? "1" : "0");
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred writing to the file.");
            e.printStackTrace();
        }
    }
}
