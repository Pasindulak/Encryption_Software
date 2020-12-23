package decryptPage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class PromptController implements Initializable {

    @FXML
    private ComboBox<String> keyBox;

    @FXML
    private ComboBox<String> hashBox;

    static String keyType;
    static String hashType;

    String[] keyTypes = {"128 bits", "192 bits", "256 bits"};
    String[] hashTypes = {"MD5", "SHA-1", "SHA-256", "SHA-384", "SHA-512"};


    @Override
    public void initialize(URL location, ResourceBundle resources) {
     keyBox.getItems().addAll(keyTypes);
     hashBox.getItems().addAll(hashTypes);
     keyBox.setValue("128 bits");
     hashBox.setValue("MD5");
    }

    @FXML
    private void ok(ActionEvent event){
        keyType = keyBox.getValue();
        hashType = hashBox.getValue();
        Stage stage = ((Stage)keyBox.getScene().getWindow());
        stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
    }
}
