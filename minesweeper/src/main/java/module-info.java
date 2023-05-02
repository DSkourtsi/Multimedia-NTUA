module minesweeper {
    requires javafx.controls;
    requires javafx.fxml;

    opens gr.el18044.minesweeper to javafx.fxml;
    exports gr.el18044.minesweeper;
    exports gr.el18044.minesweeper.utils;
    opens gr.el18044.minesweeper.utils to javafx.fxml;
    exports gr.el18044.minesweeper.exceptions;
    opens gr.el18044.minesweeper.exceptions to javafx.fxml;
}