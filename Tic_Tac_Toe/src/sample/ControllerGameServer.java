package sample;


import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class ControllerGameServer {

    @FXML
    private Button button_watchGame;
    @FXML
    private Button button_gameDetails;
    @FXML
    private Button buttonBack;

    public void buttonBackClicked(ActionEvent event) throws IOException {

        String id = ((Button)(event.getSource())).getId();

        if(id.equalsIgnoreCase("buttonBack"))
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("design/main.fxml"));

            Parent root = loader.load();

            Scene scene = new Scene(root);
            scene.getStylesheets().add(Main.class.getResource("design/Main.css").toExternalForm());

            Stage currentStage = (Stage)((Node)event.getSource()).getScene().getWindow();
            currentStage.setTitle("Tic Tac Toe");
            currentStage.setScene(scene);
            currentStage.setResizable(false);
            currentStage.show();
        }
    }

    public void switchToGameDetails(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("design/gameServerUI.fxml"));

        Parent root = loader.load();

        Scene scene = new Scene(root);
        scene.getStylesheets().add(Main.class.getResource("design/Main.css").toExternalForm());

        Stage currentStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        currentStage.setTitle("Tic Tac Toe");

        currentStage.setScene(scene);
        currentStage.setResizable(false);
        currentStage.show();


    }

    public void switchToWatchGame(ActionEvent event) {



    }

}
