package gr.el18044.minesweeper;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;

/**
 *The Cell class represents a single cell of the minesweeper game.
 * It extends the Button class by adding properties and methods specific to the minesweeper game.
 * Each Cell object keeps track of its position in the grid, whether it is a mine,
 * whether it is a super-mine, the number of neighbouring mines,
 * whether it has been revealed and whether it has been flagged.
 *
 * @author Dimitra Anna Skourtsi
 */
public class Cell extends Button {
    private final MinesweeperGrid grid;
    private final int x;
    private final int y;
    private boolean isMine;
    private boolean isSupermine;
    private int neighbourMines;
    private boolean revealed;
    private boolean flagged;

    /**
     * Constructs a new Cell object given its position (x,y), and the minesweeper grid it belong to.
     * Upon initialization the cell is neither a mine nor a super-mine, has no neighbouring mines,
     * is not revealed and is not flagged.
     * Its initial appearance is set to lightgrey with a black boarder of 0.1px.
     * Its on-click behaviour is defined so that a left click reveals cell, while right click flags/unflags the cell.
     *
     * @param grid the MinesweeperGrid object that the Cell belongs to.
     * @param x the x-coordinate of the cell in the grid.
     * @param y the y-coordinate of the cell in the grid.
     */
    public Cell(MinesweeperGrid grid, int x, int y) {
        this.grid = grid;
        this.x = x;
        this.y = y;
        this.isMine = false;
        this.isSupermine = false;
        this.neighbourMines = 0;
        this.revealed = false;
        this.flagged = false;
        this.setStyle("-fx-background-color: lightgrey; "  + "-fx-border-color: black; " + "-fx-border-width: 0.1px; ");
        this.setOnMouseClicked(mouseEvent -> {
            if(mouseEvent.getButton() == MouseButton.PRIMARY){
                grid.increaseNumTries(1);
                setRevealed(false, false);
            }
            else if(mouseEvent.getButton() == MouseButton.SECONDARY){
                setFlagged();
            }
        });

    }

    protected boolean isRevealed() { return revealed; }

    /**
     * Returns whether the Cell is a mine.
     *
     * @return true is Cell is a mine, otherwise false.
     */
    public boolean isMine() {
        return isMine;
    }
    protected boolean isSupermine() {
        return isSupermine;
    }

    protected void setRevealed(boolean fromSupermine, boolean fromSolution) {
        if(!this.revealed && !flagged && (grid.isGameNOTover() || fromSolution)) {
            this.revealed = true;
            if (isMine) {
                if(!fromSupermine && !fromSolution) {
                    Image image = null;
                    if (!isSupermine) {
                        image = new Image("mine.png");
                    }else{
                        image = new Image("bluemine.png");
                    }
                    ImageView imageView = new ImageView(image);
                    if(grid.getSize() == 16) {
                        imageView.setFitWidth(14);
                        imageView.setFitHeight(14);
                    } else {
                        imageView.setFitWidth(38);
                        imageView.setFitHeight(38);
                    }
                    this.setGraphic(imageView);
                    this.setStyle("-fx-background-color: red; " + "-fx-border-color: black; " + "-fx-border-width: 0.1px; ");
                    grid.increaseNumTries(-1);
                    grid.showSolution();
                    Dialog.showInfoDialog("Game Over", null);
                } else if (fromSolution || !isSupermine){
                    Image image = null;
                    if (!isSupermine) {
                        image = new Image("mine.png");
                    }else{
                        image = new Image("bluemine.png");
                    }
                    ImageView imageView = new ImageView(image);
                    if(grid.getSize() == 16) {
                        imageView.setFitWidth(14);
                        imageView.setFitHeight(14);
                    } else {
                        imageView.setFitWidth(38);
                        imageView.setFitHeight(38);
                    }
                    this.setGraphic(imageView);
                    this.setStyle("-fx-background-color: white; " + "-fx-border-color: black; " + "-fx-border-width: 0.1px; ");
                } else{
                    this.revealed = false;
                }
            } else if (neighbourMines == 0) {
                grid.increaseRevealedCells(1);
                if(!fromSupermine) {
                    this.setText("");
                    this.setStyle("-fx-background-color: white; " + "-fx-border-color: black; " + "-fx-border-width: 0.1px; ");
                    for (int i = -1; i < 2; i++) {
                        for (int j = -1; j < 2; j++) {
                            int xi = x + i;
                            int yj = y + j;
                            if (grid.inrange(xi, yj)) {
                                Cell[][] cells = grid.getBoard();
                                if (!cells[xi][yj].isRevealed()) {
                                    cells[xi][yj].setRevealed(false, false);
                                }
                            }
                        }
                    }
                }
                else {
                    this.setText("");
                    this.setStyle("-fx-background-color: white; "+ "-fx-border-color: black; " + "-fx-border-width: 0.1px; ");
                }
            } else {
                grid.increaseRevealedCells(1);
                this.setText(Integer.toString(neighbourMines));
                this.setStyle("-fx-background-color: white; "+ "-fx-border-color: black; " + "-fx-border-width: 0.1px; ");
            }
        }
        if (grid.getRevealedCells() == grid.getSize()*grid.getSize() - grid.getNumMines()) {
            grid.setGameover(true);
            Dialog.showInfoDialog("Congratulations", null);
            System.out.println(grid.getRevealedCells()+", "+grid.getSize()*grid.getSize()+", "+grid.getNumMines());
        }
    }
    protected void setFlagged() {
        if (!revealed && grid.isGameNOTover() && grid.getFlaggedCells() < grid.getNumMines()) {
            grid.increaseSupertries(1);
            flagged = !flagged;
            if(flagged){
                Image image = new Image("flag.png");
                ImageView imageView = new ImageView(image);
                if(grid.getSize() == 16) {
                    imageView.setFitWidth(14);
                    imageView.setFitHeight(14);
                } else {
                    imageView.setFitWidth(38);
                    imageView.setFitHeight(38);
                }
                this.setGraphic(imageView);
                grid.increaseFlaggedCells(1);

                if(grid.getSupertries() < 5 && isSupermine){
                    for(int i=0; i<grid.getSize(); i++){
                        grid.getBoard()[i][y].setRevealed(true, false);
                        grid.getBoard()[x][i].setRevealed(true, false);
                    }
                }
            }
            else {
                this.setGraphic(null);
                grid.increaseFlaggedCells(-1);
            }
        }
    }

    /**
     * Sets the Cell as being a mine
     */
    public void setMine() {
        isMine = true;
    }

    /**
     * Sets the Cell as being a super-mine
     */
    public void setSupermine() {
        isSupermine = true;
    }

    /**
     * Sets the number of neighbouring mines of the Cell
     * @param neighbourMines the number of neighbouring Cells that are mines
     */
    public void setNeighbourMines(int neighbourMines) {
        this.neighbourMines = neighbourMines;
    }


}