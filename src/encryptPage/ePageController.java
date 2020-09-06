package encryptPage;

import MainPage.PageController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import java.net.URL;
import java.util.ResourceBundle;

public class ePageController implements Initializable {

    @FXML
    ToggleGroup option;
    @FXML
    TextField path;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    @FXML
    private void back(MouseEvent event) {

        PageController.bodyPane.getChildren().setAll(PageController.mainPane);
    }

    @FXML
    private void browse(ActionEvent event) {
        String selectedButton = ((ToggleButton)option.getSelectedToggle()).getText();
        if(selectedButton.equals("File")) {
            FileChooser fileChooser = new FileChooser();
            try {
                path.setText(fileChooser.showOpenDialog(path.getScene().getWindow()).getPath());
            }catch (Exception e){

            }

        }else {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            try {
                path.setText(directoryChooser.showDialog(path.getScene().getWindow()).getPath());
            }catch (Exception e){

            }
        }
    }

}
