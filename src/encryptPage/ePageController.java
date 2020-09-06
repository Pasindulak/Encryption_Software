package encryptPage;

import MainPage.PageController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
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


    @Override
    public void initialize(URL location, ResourceBundle resources) {
            addToggleListner();
            showPass();
            setkeyType();
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
    /**-------method for toggle button to clear path text------------*/


    private void addToggleListner(){
          option.selectedToggleProperty().addListener((ov , old , n) -> {
              if(n!=null) {
                  path.setText("");
              }
              else{
                  String temp = path.getText();
                  option.selectToggle(old);
                  path.setText(temp);
              }
          });
    }//addToggle

    /**------------show password method------------*/

    private void showPass(){
        passT.textProperty().bind(passP.textProperty());
        cPassT.textProperty().bind(cPassP.textProperty());
       SP.selectedProperty().addListener((ov,o,n)->{
           if(n){
               passP.setVisible(false);
               cPassP.setVisible(false);
               passT.setVisible(true);
               cPassT.setVisible(true);
              passT.textProperty().unbind();
               passP.textProperty().bind(passT.textProperty());
               cPassT.textProperty().unbind();
               cPassP.textProperty().bind(cPassT.textProperty());
           }else{
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

    /**---------implementation of combobox----------- */
 String [] keyTypes = {"128 bits","192 bits","256 bits"};
    private void setkeyType(){
        keyBox.getItems().setAll(keyTypes);
        keyBox.getSelectionModel().select(0);
    }
}
