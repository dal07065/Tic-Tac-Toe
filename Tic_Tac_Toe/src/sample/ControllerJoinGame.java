package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.server.AppData;

import java.io.IOException;
import java.util.ArrayList;

public class ControllerJoinGame {


    @FXML
    private Button button_joinGame;

    @FXML
    private ListView listView_openGames;


    public void initialize()
    {
        ArrayList<String> games = AppData.getAllGames();

        for(String game: games)
            listView_openGames.getItems().add(game);
    }

    @FXML
    public void refresh()
    {
        listView_openGames = new ListView();

        initialize();
    }

    public void joinGame(ActionEvent actionEvent) throws IOException, ClassNotFoundException {

        if(AppData.joinGame((String)listView_openGames.getSelectionModel().getSelectedItem()))
        {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("design/playLan.fxml"));

            Parent root = loader.load();

            Scene scenePlay = new Scene(root, 634, 446);
            scenePlay.getStylesheets().add(Main.class.getResource("design/Play.css").toExternalForm());

            Stage currentStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();

            ControllerLan controller = loader.getController();

            currentStage.setScene(scenePlay);
            currentStage.sizeToScene();
            currentStage.show();

            controller.setThisPlayer('O');
            controller.setCurrentPlayer('X');

            controller.initializeAfterLoad();

        }
        else
        {
            Main.displayAlert("Sorry", "Could not join game. Try a different game.");
        }

    }
}
