package sample;


import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;
import message.JoinGameAsViewerResponseMessage;
import sample.server.AppData;

import java.io.IOException;
import java.util.ArrayList;

public class ControllerGameServer {

    @FXML
    private Button button_watchGame;
    @FXML
    private Button button_gameDetails;
    @FXML
    private Button buttonBack;
    @FXML
    private ListView listView_activeGames;

    public void initialize()
    {
        ArrayList<String> games = AppData.getAllActiveGames();

        for(String game: games)
            listView_activeGames.getItems().add(game);
    }

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

    public void switchToWatchGame(ActionEvent actionEvent) throws IOException {
        // get the selected object from the list of active games
        JoinGameAsViewerResponseMessage msg = (JoinGameAsViewerResponseMessage) AppData.joinGameAsViewer((String)listView_activeGames.getSelectionModel().getSelectedItem());

        if(!msg.isSuccess())
        {
            Main.displayAlert("Failed", "Game: " + (String)listView_activeGames.getSelectionModel().getSelectedItem() + " could not be accessed.");
        }
        else
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("design/playWatch.fxml"));

            Parent root = loader.load();

            Scene scenePlay = new Scene(root, 634, 446);
            scenePlay.getStylesheets().add(Main.class.getResource("design/Play.css").toExternalForm());

            Stage currentStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();

            ControllerWatch controller = loader.getController();

            currentStage.setScene(scenePlay);
            currentStage.sizeToScene();
            currentStage.show();

            controller.setThisPlayer('X');
            controller.setCurrentPlayer(msg.getCurrentPlayer());

            controller.initializeAfterLoad(msg);

        }



    }

}
