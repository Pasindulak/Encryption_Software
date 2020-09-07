package decryptPage;

import MainPage.PageController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class dPageController implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void back(MouseEvent event) {

        PageController.bodyPane.getChildren().setAll(PageController.mainPane);
    }
}
