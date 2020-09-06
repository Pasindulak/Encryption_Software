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
        System.out.println(selectedButton);
        if(selectedButton.equals("File")) {
            FileChooser fileChooser = new FileChooser();

            fileChooser.showOpenDialog(path.getScene().getWindow());

        }else {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.showDialog(path.getScene().getWindow());
        }
    }



}
