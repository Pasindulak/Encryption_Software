package decryptPage;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

import java.net.URL;
import java.util.ResourceBundle;

public class PromptController implements Initializable {

    @FXML
    private ComboBox<String> keyBox;

    @FXML
    private ComboBox<String> hashBox;

    String[] keyTypes = {"128 bits", "192 bits", "256 bits"};
    String[] hashTypes = {"MD5", "SHA-1", "SHA-256", "SHA-384", "SHA-512"};


    @Override
    public void initialize(URL location, ResourceBundle resources) {
     keyBox.getItems().addAll(keyTypes);
     hashBox.getItems().addAll(hashTypes);
    }
}
