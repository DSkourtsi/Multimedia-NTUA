package gr.el18044.minesweeper;

import gr.el18044.minesweeper.utils.WriteScenarioToFile;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class CreateScenario {
    @FXML
    private TextField scenarioIDtxt;
    @FXML
    private TextField difficultytxt;
    @FXML
    private TextField numMinestxt;
    @FXML
    private TextField timetxt;
    @FXML
    private CheckBox hasSuperminecb;
    @FXML
    private Button createScenarioButton;
    public static void show() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Minesweeper.class.getResource("scenarioForm.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("Create scenario");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    public void CreateScenarioButtonClicked() {
        String scenarioID = scenarioIDtxt.getText();
        String s_difficulty = difficultytxt.getText();
        String s_numMines = numMinestxt.getText();
        String s_time = timetxt.getText();

        if (Objects.equals(scenarioID, "") || Objects.equals(s_difficulty, "") || Objects.equals(s_numMines, "") || Objects.equals(s_time, "")){
            Dialog.showErrDialog("Values cannot be null", null);
        }
        else {
            int difficulty = Integer.parseInt(s_difficulty);
            int numMines = Integer.parseInt(s_numMines);
            int time = Integer.parseInt(s_time);
            boolean hasSupermine = hasSuperminecb.isSelected();

            var difficultySettings = new DifficultySettings(difficulty);
            var gameParams = difficultySettings.getSettings();
            var scenario = new Scenario(numMines, time, hasSupermine, gameParams);

            String filename = scenarioID + ".txt";
            Path path = Paths.get("medialab/scenarios/" + filename);
            if (Files.exists(path)) {
                Dialog.showErrDialog("This Scenario_ID already exists", null);
            } else if (scenario.isValid() && !scenarioID.isBlank()) {
                saveScenario();
                ((Stage) createScenarioButton.getScene().getWindow()).close();
            } else {
                String content = "If difficulty is 1: Mines:[9-11], Time:[120-180], No super-mine\n" +
                        "If difficulty is 2: Mines:[35-45], Time:[240-360]";
                Dialog.showErrDialog("Invalid Parameters", content);
            }
        }
    }
    public void saveScenario(){
        String scenarioID = scenarioIDtxt.getText();
        int difficulty = Integer.parseInt(difficultytxt.getText());
        int numMines = Integer.parseInt(numMinestxt.getText());
        int time = Integer.parseInt(timetxt.getText());
        boolean hasSupermine = hasSuperminecb.isSelected();

        var difficultySettings = new DifficultySettings(difficulty);
        var gameParams = difficultySettings.getSettings();
        var scenario = new Scenario(numMines, time, hasSupermine, gameParams);
        new WriteScenarioToFile(scenarioID, scenario);
    }
}
