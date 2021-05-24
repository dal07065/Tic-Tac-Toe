package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.server.AppData;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader loader = new FXMLLoader(getClass().getResource("design/login.fxml"));

        //UserDBConnection.connect();

        Parent root = loader.load();
        primaryStage.setTitle("Tic Tac Toe : Log In");

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        scene.getStylesheets().add(Main.class.getResource("design/Main.css").toExternalForm());
        primaryStage.show();

        AppData.connectToServer();

    }

    public static void main(String[] args) {
        launch(args);
    }
}


