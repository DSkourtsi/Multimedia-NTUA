package gr.el18044.minesweeper;

public class DifficultySettings {
    private GameParams settings;

    public DifficultySettings(int difficulty) {
        if (difficulty == 1) {
            settings = new GameParams(1, 9,9,11,120,180,false);
        } else if (difficulty == 2) {
            settings = new GameParams(2, 16, 35, 45, 240, 360, true);
        }
    }
    public GameParams getSettings() {
        return settings;
    }
}
