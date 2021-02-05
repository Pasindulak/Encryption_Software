package decryptPage;

import MainPage.PageController;
import aes.AESEngine;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.*;

import javax.crypto.Cipher;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class dPageController implements Initializable {


    @FXML
    ToggleGroup option;
    @FXML
    TextField path;
    @FXML
    CheckBox SP;
    @FXML
    TextField passT;
    @FXML
    PasswordField passP;
    @FXML
    Button decBTN;
    @FXML
    Button canBTN;
    @FXML
    ProgressBar pBar;
    @FXML
    Label pWarn;
    @FXML
    Label status;
    /**
     * ---------implementation of combobox-----------
     */
    String[] keyTypes = {"128 bits", "192 bits", "256 bits"};
    String[] hashTypes = {"MD5", "SHA-1", "SHA-256", "SHA-384", "SHA-512"};

    private Thread thread;

    @Override


    public void initialize(URL location, ResourceBundle resources) {
        showPass();
        addToggleListner();
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
        SP.selectedProperty().addListener((ov, o, n) -> {
            if (n) {
                passP.setVisible(false);
                passT.setVisible(true);
                passT.textProperty().unbind();
                passP.textProperty().bind(passT.textProperty());
            } else {
                passP.setVisible(true);
                passT.setVisible(false);
                passP.textProperty().unbind();
                passT.textProperty().bind(passP.textProperty());
            }
        });
    }

    @FXML
    private void decrypt(ActionEvent event) {
        Thread thread = new Thread(()->{
            if (checkInputs()) {
                Platform.runLater(()->{
                    pWarn.setText("");
                    pBar.setVisible(true);
                });
                File file = new File(path.getText());
                String keySize;
                String hashType;
                AESEngine aesEngine;
                if (AESEngine.checkForSave(file)) {
                    byte[] meta = AESEngine.getSavedData(file);
                    keySize = keyTypes[meta[0] - 48];
                    hashType = hashTypes[meta[1] - 48];
                    aesEngine = new AESEngine(
                            passT.getText(), hashType,
                            Integer.parseInt(keySize.substring(0, 3)) / 8,
                            Cipher.DECRYPT_MODE
                    );
                    aesEngine.setSaved(true);
                    crypt(aesEngine,file);
                }else{
                    final Stage[] stage = new Stage[1];
                    Platform.runLater(()->{
                        Pane root = null;
                        try {
                            root = FXMLLoader.load(getClass().getResource("prompt.fxml"));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Scene scene = new Scene(root);
                        stage[0] = new Stage();
                        stage[0].setScene(scene);
                        stage[0].initStyle(StageStyle.UTILITY);
                        stage[0].initModality(Modality.APPLICATION_MODAL);
                        stage[0].setResizable(false);
                        stage[0].show();
                        stage[0].setOnCloseRequest(new EventHandler<WindowEvent>() {
                            @Override
                            public void handle(WindowEvent event) {
                                AESEngine aesEngine = new AESEngine(
                                        passT.getText(),PromptController.hashType,
                                        Integer.parseInt(PromptController.keyType.substring(0, 3)) / 8,
                                        Cipher.DECRYPT_MODE
                                );
                                aesEngine.setSaved(false);
                                canBTN.setDisable(false);
                                decBTN.setDisable(true);
                                pBar.setVisible(true);
                                crypt(aesEngine,file);
                            }
                        });

                    });


                }


            }
        });
        this.thread = thread;
        thread.start();
    }
    public void crypt(AESEngine aesEngine,File file){
        Task<Void> sleeper = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                }
                return null;
            }
        };
        sleeper.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {

                boolean stat = aesEngine.crypt(file);
                Platform.runLater(()->{
                    if(stat){
                        status.setStyle("-fx-text-fill:green");
                        status.setText("Decrypted!");
                        pBar.setProgress(1.0);
                        pBar.getStyleClass().add("success");
                    }else{
                        status.setStyle("-fx-text-fill:red");
                        status.setText("Failed!");
                        pBar.setProgress(1.0);
                        pBar.getStyleClass().add("danger");
                    }
                    canBTN.setDisable(true);
                    decBTN.setDisable(false);
                });
            }
        });
        new Thread(sleeper).start();


    }

    @FXML
    private void cancel(ActionEvent event){
        thread.stop();
    }

    private boolean checkInputs() {
        boolean ret = true;
        if (path.getText().equals("") || path.getText() == null) {
            ret = false;
            path.setPromptText("Please Select a file or folder to Encrypt");
        }
        if (passT.getText().equals("") || passT.getText() == null) {
            ret = false;
            pWarn.setText("Password Field cannot be\nEmpty!");
        }
        return ret;
    }


}
