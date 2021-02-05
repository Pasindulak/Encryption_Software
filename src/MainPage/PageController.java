package MainPage;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PageController implements Initializable {

    @FXML
    Label encrypt;
    @FXML
    Label decrypt;
    @FXML
    HBox header;
    @FXML
    AnchorPane body;
    @FXML
    AnchorPane mPane;
    @FXML
    BorderPane bPane;

    private boolean isEncrypt;
    private boolean isDecrypt;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dragOn();
        encrypt.setOnMouseEntered(e-> encrypt.setStyle("-fx-background-color: #f4f4f4;"));
        encrypt.setOnMouseExited(e-> {if(!isEncrypt)encrypt.setStyle("-fx-background-color: #d1d5e6;");});
        decrypt.setOnMouseEntered(e-> decrypt.setStyle("-fx-background-color: #f4f4f4;"));
        decrypt.setOnMouseExited(e-> {if(!isDecrypt)decrypt.setStyle("-fx-background-color: #d1d5e6;");});
        try {
            encryptBtn(null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void minimize(MouseEvent e) {
        ((Stage) header.getScene().getWindow()).setIconified(true);
    }

    @FXML
    private void shutdown(MouseEvent e) {
        ((Stage) header.getScene().getWindow()).close();
    }

    /**
     * ----method for drag feature to the window------------
     */
    double x;
    double y;
    private void dragOn() {
        header.setOnMousePressed(event -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });
        header.setOnMouseDragged(event -> {
            Stage stage = (Stage) header.getScene().getWindow();
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
        });
    }

    @FXML
    private void encryptBtn(MouseEvent event) throws IOException {
        isEncrypt = true;
        isDecrypt = false;
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/encryptPage/ePage.fxml"));
        body.getChildren().setAll(pane);
        encrypt.setStyle("-fx-background-color: #f4f4f4;");
        decrypt.setStyle("-fx-background-color: #d1d5e6;");

    }

    @FXML
    private void decryptBtn(MouseEvent event) throws IOException {
        isDecrypt = true;
        isEncrypt = false;
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/decryptPage/dPage.fxml"));
        body.getChildren().setAll(pane);
        decrypt.setStyle("-fx-background-color: #f4f4f4;");
        encrypt.setStyle("-fx-background-color: #d1d5e6;");
    }
}
