package encryptPage;

import MainPage.PageController;
import aes.AESEngine;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import javax.crypto.Cipher;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class ePageController implements Initializable {

    @FXML
    ToggleGroup option;
    @FXML
    TextField path;
    @FXML
    CheckBox SP;
    @FXML
    TextField passT;
    @FXML
    TextField cPassT;
    @FXML
    PasswordField passP;
    @FXML
    PasswordField cPassP;
    @FXML
    ComboBox<String> keyBox;
    @FXML
    ComboBox<String> hashBox;
    @FXML
    CheckBox def;
    @FXML
    Button encBTN;
    @FXML
    Button canBTN;
    @FXML
    ProgressBar pBar;
    /**
     * ---------implementation of combobox-----------
     */
    String[] keyTypes = {"128 bits", "192 bits", "256 bits"};
    String[] hashTypes = {"MD5", "SHA-1", "SHA-256", "SHA-384", "SHA-512"};

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addToggleListner();
        showPass();
        setkeyTypes();
        sethashTypes();
        setDefault();
        defaultListner();
    }

    @FXML
    private void back(MouseEvent event) {

        PageController.bodyPane.getChildren().setAll(PageController.mainPane);
    }

    @FXML
    private void browse(ActionEvent event) {
        String selectedButton = ((ToggleButton) option.getSelectedToggle()).getText();
        if (selectedButton.equals("File")) {
            FileChooser fileChooser = new FileChooser();
            try {
                path.setText(fileChooser.showOpenDialog(path.getScene().getWindow()).getPath());
            } catch (Exception e) {

            }

        } else {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            try {
                path.setText(directoryChooser.showDialog(path.getScene().getWindow()).getPath());
            } catch (Exception e) {

            }
        }
    }

    /**
     * -------method for toggle button to clear path text------------
     */


    private void addToggleListner() {
        option.selectedToggleProperty().addListener((ov, old, n) -> {
            if (n != null) {
                path.setText("");
            } else {
                String temp = path.getText();
                option.selectToggle(old);
                path.setText(temp);
            }
        });
    }//addToggle

    /**
     * ------------show password method------------
     */

    private void showPass() {
        passT.textProperty().bind(passP.textProperty());
        cPassT.textProperty().bind(cPassP.textProperty());
        SP.selectedProperty().addListener((ov, o, n) -> {
            if (n) {
                passP.setVisible(false);
                cPassP.setVisible(false);
                passT.setVisible(true);
                cPassT.setVisible(true);
                passT.textProperty().unbind();
                passP.textProperty().bind(passT.textProperty());
                cPassT.textProperty().unbind();
                cPassP.textProperty().bind(cPassT.textProperty());
            } else {
                passP.setVisible(true);
                cPassP.setVisible(true);
                passT.setVisible(false);
                cPassT.setVisible(false);
                passP.textProperty().unbind();
                passT.textProperty().bind(passP.textProperty());
                cPassP.textProperty().unbind();
                cPassT.textProperty().bind(cPassP.textProperty());
            }
        });
    }

    private void setkeyTypes() {
        keyBox.getItems().setAll(keyTypes);
    }

    private void sethashTypes() {
        hashBox.getItems().setAll(hashTypes);
    }

    private void setDefault() {
        hashBox.getSelectionModel().select(2);
        keyBox.getSelectionModel().select(2);
        hashBox.setDisable(true);
        keyBox.setDisable(true);
    }

    /**
     * ----------default checkbox implementation----------------
     */
    private void defaultListner() {
        def.selectedProperty().addListener((o, ov, nv) -> {
            if (nv) {
                setDefault();
            } else {
                hashBox.setDisable(false);
                keyBox.setDisable(false);
            }
        });

    }

    /**
     * ----------method for encrypt button-----------
     */

    @FXML
    private void encrypt(ActionEvent e) {
        canBTN.setDisable(false);
        encBTN.setDisable(true);
        pBar.setVisible(true);
        AESEngine aesEngine = new AESEngine(passT.getText(),
                hashBox.getValue(),
                Integer.parseInt(keyBox.getValue().substring(0, 3)) / 8,
                Cipher.ENCRYPT_MODE);
        File file = new File(path.getText());
        aesEngine.crypt(file);
        canBTN.setDisable(true);
        encBTN.setDisable(false);
        pBar.setVisible(false);
    }
}
