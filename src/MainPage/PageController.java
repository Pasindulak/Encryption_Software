package MainPage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PageController implements Initializable {

    /**
     * ------Encrypt and Decrypt buttons' methos-------------
     */
    public static AnchorPane mainPane;
    public static AnchorPane bodyPane;  // parent pain
    final int epageHeight = 430;
    @FXML
    Button encrypt;
    @FXML
    Button decrypt;
    @FXML
    HBox header;
    @FXML
    AnchorPane body;
    @FXML
    AnchorPane mPane;
    @FXML
    BorderPane bPane;
    /**
     * ----method for drag feature to the window------------
     */
    double x;
    double y;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dragOn();
    }

    @FXML
    private void minimize(MouseEvent e) {
        ((Stage) header.getScene().getWindow()).setIconified(true);
    }

    @FXML
    private void shutdown(MouseEvent e) {
        ((Stage) header.getScene().getWindow()).close();
    }

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
    private void encryptBtn(ActionEvent event) {
//        AnchorPane pane = FXMLLoader.load(getClass().getResource("/encryptPage/ePage.fxml"));
//        mainPane = mPane;
//        bodyPane = body;    // parent pain
//        body.getChildren().setAll(pane);
        System.out.println("You clicked me!");
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("encryptPage", "ePage");
        bPane.setCenter(view);
    }

    @FXML
    private void decryptBtn(ActionEvent event) {
//        AnchorPane pane = FXMLLoader.load(getClass().getResource("/decryptPage/dPage.fxml"));
//        mainPane = mPane;
//        bodyPane = body;
//        body.getChildren().setAll(pane);
        System.out.println("You clicked me!");
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("decryptPage", "dPage");
        bPane.setCenter(view);
    }
}
