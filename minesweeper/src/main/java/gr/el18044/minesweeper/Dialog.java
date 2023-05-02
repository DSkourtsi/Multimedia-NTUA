package gr.el18044.minesweeper;

import javafx.scene.control.Alert;

public class Dialog {

    public static void showErrDialog(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(header);
        if (content != null){
            alert.setContentText(content);
        }
        alert.showAndWait();
    }

    public static void showInfoDialog(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(header);
        alert.setHeaderText(header);
        if (content != null){
            alert.setContentText(content);
        }
        alert.showAndWait();
    }
}
