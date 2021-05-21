package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import sample.server.AppData;

import java.util.Optional;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader loader = new FXMLLoader(getClass().getResource("design/login.fxml"));

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

    public static void displayAlert(String Title, String Content)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(Title);
        alert.setHeaderText(null);
        alert.setContentText(Content);
        alert.showAndWait();
    }

    public static void displayQuestionAlert(String title, String content)
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText("Look, a Confirmation Dialog");
        alert.setContentText(content);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK){

        }
    }

}


