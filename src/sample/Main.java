package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutput;
import java.net.Socket;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader loader = new FXMLLoader(getClass().getResource("design/main.fxml"));

        Parent root = loader.load();
        primaryStage.setTitle("Tic Tac Toe");

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        scene.getStylesheets().add(Main.class.getResource("design/Main.css").toExternalForm());
        primaryStage.show();

        ControllerMain controllerMain = loader.getController();

        // Create a socket to connect to the server
//        Socket socket = new Socket("localhost", 8000);

//        controllerMain.setSocket(socket);

    }

    public static void main(String[] args) {
        launch(args);
    }
}


