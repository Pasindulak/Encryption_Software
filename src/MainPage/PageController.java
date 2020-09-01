package MainPage;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class PageController implements Initializable {

    @FXML
    Button encrypt;
    @FXML
    Button decrypt;
    @FXML
    HBox header;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dragOn();
    }

    @FXML
    private void minimize(MouseEvent e){
        ((Stage) encrypt.getScene().getWindow()).setIconified(true);
    }
    @FXML
    private void shutdown(MouseEvent e){
        ((Stage) encrypt.getScene().getWindow()).close();
    }

             /**----method for drag feature to the window------------*/
        double x;
        double y;
    private void dragOn(){
        header.setOnMousePressed(event -> {
           x= event.getSceneX();
           y = event.getSceneY();
        });
        header.setOnMouseDragged(event -> {
           Stage stage = (Stage) header.getScene().getWindow();
           stage.setX(event.getScreenX()-x);
           stage.setY(event.getScreenY()-y);
        });
    }
}
