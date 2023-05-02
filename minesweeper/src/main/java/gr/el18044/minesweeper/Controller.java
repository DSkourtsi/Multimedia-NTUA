package gr.el18044.minesweeper;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Controller {

    @FXML
    private GridPane Grid;
    @FXML
    private Label MinesFlagged;
    @FXML
    private Label TotalMines;
    @FXML
    private Label TimeLeft;
    private Scenario scenarioLoaded;
    private int secondsElapsed;
    private int curr_totaltime;
    private MinesweeperGrid grid;
    private final Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
        increaseSecondsElapsed(1);
        updateTimer(getSecondsElapsed());
    }));
    private List<RoundsRecord> records = new ArrayList<>();

    @FXML
    protected void Create() throws IOException {
        CreateScenario.show();
    }
    @FXML
    protected void Load() throws IOException {
        SelectScenario.show(this);
    }
    @FXML
    protected void Start() {
        timeline.stop();
        curr_totaltime = scenarioLoaded.totalTime();
        timeline.setCycleCount(curr_totaltime);
        secondsElapsed = 0;
        updateTimer(0);
        timeline.play();
        grid = new MinesweeperGrid(this, scenarioLoaded.params().getBoardSize(), scenarioLoaded.numMines(), scenarioLoaded.hasSupermine());
        grid.showBoard(Grid);
    }
    @FXML
    protected void Exit() { Platform.exit(); }
    @FXML
    protected void Solution() { grid.showSolution(); }
    @FXML
    public void Rounds(){
        StringBuilder content = new StringBuilder();
        for (RoundsRecord roundRecord : records) {
            content.append(roundRecord.numMines()).append(", ").append(roundRecord.numTries()).append(", ").append(roundRecord.time()).append(", ").append(roundRecord.won() ? "Player" : "Computer").append("\n");
        }
        Dialog.showInfoDialog("Last 5 rounds\nMines, Tries, Time, Winner\n", content.toString());
    }
    public void updateTotalMines(int numTotalMines) {
        TotalMines.setText("Total Mines: "+ numTotalMines);
    }
    public void updateMinesFlagged(int numMinesFlagged) {
        MinesFlagged.setText("Mines Flagged: "+ numMinesFlagged);
    }
    public void updateTimer(int time) {
        int timeremaining = curr_totaltime - time;
        TimeLeft.setText("Remaining Time: "+ timeremaining);
    }

    public Timeline getTimeline() {
        return timeline;
    }
    public int getSecondsElapsed() {
        return secondsElapsed;
    }
    public void setScenarioLoaded(Scenario scenario) {
        this.scenarioLoaded = scenario;
    }
    public void setSecondsElapsed(int secondsElapsed) {
        this.secondsElapsed = secondsElapsed;
    }
    public void increaseSecondsElapsed(int incr){
        secondsElapsed = secondsElapsed + incr;
    }

    public void addRecord(RoundsRecord record){
        if (records.size() == 5){
            records.remove(0);
        }
        records.add(record);
    }
}