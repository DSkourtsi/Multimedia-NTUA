package gr.el18044.minesweeper;

public record Scenario(int numMines, int totalTime, boolean hasSupermine, GameParams params) {

    public boolean isValid() {
        if (params.getDifficulty() != 1 && params.getDifficulty() != 2) return false;
        if (hasSupermine && !params.isCanHaveSupermine()) return false;
        if (numMines < params.getMinNumMines() || numMines > params.getMaxNumMines()) return false;
        return (totalTime >= params.getMinTime() && totalTime <= params.getMaxTime());
    }
}
