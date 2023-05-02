package gr.el18044.minesweeper;

import javafx.application.Platform;
import javafx.scene.layout.GridPane;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class MinesweeperGrid{
    private final int size;
    private final int numMines;
    private final Cell[][] board;
    private int revealedCells;
    private int flaggedCells;
    private int numTries;
    private boolean gameover;
    private boolean gamelost;
    private final boolean hasSupermine;
    private final Controller controller;
    private int supertries = 0;


    MinesweeperGrid(Controller controller, int size, int numMines, boolean hasSupermine) {
        this.size = size;
        this.numMines = numMines;
        this.board = new Cell[size][size];
        this.gameover = false;
        this.gamelost = false;
        this.flaggedCells = 0;
        this.revealedCells = 0;
        this.hasSupermine = hasSupermine;
        this.controller = controller;
        controller.updateTotalMines(this.numMines);
        controller.updateMinesFlagged(this.flaggedCells);
    }

    public void showBoard(GridPane gridPane) {
        gridPane.getChildren().clear();
        controller.getTimeline().setOnFinished(event -> {
            showSolution();
            Platform.runLater(() -> Dialog.showInfoDialog("Game Over",null));
        });

        int perfsize;
        if (size == 16) {
            perfsize = 30;
        } else {
            perfsize = 54;
        }
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Cell cell = new Cell(this, i,j);
                cell.setPrefSize(perfsize, perfsize);
                board[i][j] = cell;
                gridPane.add(cell,i,j);
            }
        }
        placeMines();
        calcNearMines();
    }

    private void placeMines(){
        Random random = new Random();
        int mines = 0;
        if (hasSupermine){
            int x = random.nextInt(size);
            int y = random.nextInt(size);
            board[x][y].setMine();
            board[x][y].setSupermine();
            mines++;
        }
        while (mines < numMines){
            int x = random.nextInt(size);
            int y = random.nextInt(size);
            if(!board[x][y].isMine()){
                board[x][y].setMine();
                mines++;
            }
        }

        var filepath = "medialab/mines.txt";
        File file = new File(filepath);
        try {
            FileWriter writer = new FileWriter(file);
            for (int i=0; i < size; i++){
                for (int j=0; j < size; j++){
                    if(board[i][j].isMine()){
                        String supermine = (board[i][j].isSupermine()?"1":"0");
                        writer.write(i + "," + j + "," + supermine + "\n");
                    }
                }
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred writing to the file.");
            e.printStackTrace();
        }
    }

    private void calcNearMines(){
        for(int x=0; x<size; x++){
            for(int y=0; y<size; y++){
                int nearMines = 0;
                for (int i=-1; i<2; i++){
                    for (int j=-1; j<2; j++){
                        int xi = x+i;
                        int yj = y+j;
                        if (inrange(xi,yj)) {
                            if (board[xi][yj].isMine()) {
                                nearMines++;
                            }
                        }
                    }
                }
                board[x][y].setNeighbourMines(nearMines);
            }
        }
    }

    public boolean inrange(int x, int y){
        return 0 <= x && x < size && 0 <= y && y < size;
    }

    public int getSize() {
        return size;
    }
    public int getNumMines() {
        return numMines;
    }
    public boolean isGameNOTover() {
        return !gameover;
    }

    public int getRevealedCells() {
        return revealedCells;
    }
    public int getFlaggedCells() {
        return flaggedCells;
    }

    public Cell[][] getBoard() {
        return board;
    }

    public void setGameover(boolean gameover) {
        this.gameover = gameover;
        RoundsRecord record = new RoundsRecord(numMines, numTries, controller.getSecondsElapsed(), !gamelost);
        controller.addRecord(record);
        controller.setSecondsElapsed(0);
        controller.getTimeline().stop();
    }

    public void setGamelost(boolean gamelost) {
        this.gamelost = gamelost;
    }

    public void increaseRevealedCells(int incr) {
        revealedCells = revealedCells + incr;
    }

    public void increaseFlaggedCells(int incr) {
        flaggedCells = flaggedCells + incr;
        controller.updateMinesFlagged(flaggedCells);
    }
    public void increaseNumTries(int incr) {
        numTries += incr;
    }

    public void showSolution(){
        for (int i=0; i < size; i++){
            for (int j=0; j < size; j++){
                if(board[i][j].isMine()){
                    board[i][j].setRevealed(false, true);
                }
            }
        }
        setGamelost(true);
        setGameover(true);
    }

    public int getSupertries() {
        return supertries;
    }

    public void increaseSupertries(int incr) {
        this.supertries += incr;
    }
}
