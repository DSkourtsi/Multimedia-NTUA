package gr.el18044.minesweeper;

import gr.el18044.minesweeper.exceptions.InvalidDescriptionException;
import gr.el18044.minesweeper.exceptions.InvalidValueException;
import gr.el18044.minesweeper.utils.ReadScenarioFromFile;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;

public class SelectScenario {
    @FXML
    private TextField scenarioIDtxt;
    @FXML
    private Button selectScenarioButton;
    private Controller controller;

    public SelectScenario(){}
    public static void show(Controller controller) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Minesweeper.class.getResource("scenarioSelect.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        SelectScenario selectScenario = fxmlLoader.getController();
        selectScenario.setController(controller);
        Stage stage = new Stage();
        stage.setTitle("Load scenario");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    public void SelectScenarioButtonClicked() {
        String scenarioID = scenarioIDtxt.getText();
        try {
            ReadScenarioFromFile readScenario = new ReadScenarioFromFile(scenarioID);
            Scenario selectedScenario = readScenario.getScenario();
            this.controller.setScenarioLoaded(selectedScenario);
            ((Stage) selectScenarioButton.getScene().getWindow()).close();
        } catch (InvalidValueException e) {
            Dialog.showErrDialog("Invalid value", null);
        } catch (InvalidDescriptionException e) {
            Dialog.showErrDialog("Invalid description", null);
        } catch (IOException e) {
            Dialog.showErrDialog("Error loading file", "Check if file exists");
        }
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

}
