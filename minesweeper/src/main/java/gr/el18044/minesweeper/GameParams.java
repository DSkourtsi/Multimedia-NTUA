package gr.el18044.minesweeper;

public class GameParams {
    private final int difficulty;
    private final int boardSize;
    private final int minNumMines;
    private final int maxNumMines;
    private final int minTime;
    private final int maxTime;
    private final boolean canHaveSupermine;

    public GameParams(int difficulty, int boardSize, int minNumMines, int maxNumMines, int minTime, int maxTime, boolean canHaveSupermine) {
        this.difficulty = difficulty;
        this.boardSize = boardSize;
        this.minNumMines = minNumMines;
        this.maxNumMines = maxNumMines;
        this.minTime = minTime;
        this.maxTime = maxTime;
        this.canHaveSupermine = canHaveSupermine;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public int getBoardSize() {
        return boardSize;
    }

    public int getMinNumMines() {
        return minNumMines;
    }

    public int getMaxNumMines() {
        return maxNumMines;
    }

    public int getMinTime() {
        return minTime;
    }

    public int getMaxTime() {
        return maxTime;
    }

    public boolean isCanHaveSupermine() {
        return canHaveSupermine;
    }
}
