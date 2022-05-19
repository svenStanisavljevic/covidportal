package main.java.sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main extends Application {

    public static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static Stage glavniStage;
    @Override
    public void start(Stage primaryStage) throws Exception{
        logger.info("Pokretanje programa");
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("pocetniEkran.fxml"));
        glavniStage = primaryStage;
        primaryStage.setTitle("Covid Portal");
        primaryStage.setScene(new Scene(root, 1920, 1080));
        primaryStage.getIcons().add(new Image("bae2.jpg"));
        primaryStage.show();
        glavniStage.setOnCloseRequest(e -> {
            System.exit(0);
        });
    }

    public static Stage getMainStage() {
        return glavniStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
