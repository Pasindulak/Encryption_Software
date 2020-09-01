package MainPage;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class PageController implements Initializable {

    @FXML
    Button encrypt;
    @FXML
    Button decrypt;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void minimize(MouseEvent e){
        ((Stage) encrypt.getScene().getWindow()).setIconified(true);
    }
    @FXML
    private void shutdown(MouseEvent e){
        ((Stage) encrypt.getScene().getWindow()).close();
    }
}
