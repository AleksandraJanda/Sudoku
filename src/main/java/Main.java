import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    static Parent root;
    static Scene scene;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/main.fxml"));
        root.getStylesheets().add("/main.css");
        primaryStage.setTitle("Sudoku");
        int width = 390;
        int height = 500;
        scene = new Scene(root, width, height);
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image("/icon.png"));
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
