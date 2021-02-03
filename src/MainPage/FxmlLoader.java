package MainPage;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.FileNotFoundException;
import java.net.URL;

public class FxmlLoader {
    private Pane view;


public Pane getPage(String fName, String fileName){

    try {
        URL fileUrl = Page.class.getResource("/" + fName + "/" + fileName + ".fxml");
        if (fileUrl == null) {
            throw new java.io.FileNotFoundException("FXML file can't be found");
        }

        view = new FXMLLoader().load(fileUrl);

    } catch (Exception e) {
        System.out.println("No Page" + fileName + "please check FxmlLoader");
    }


    return view;
}
}
