package isen.java2.study;

import isen.java2.study.service.StageService;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class Application extends javafx.application.Application{

	public static void main(String[] args) {
		launch(args);
	}

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Demographic stat calculator !");
        StageService.getInstance().setPrimaryStage(primaryStage);
        showMainScreen();
    }

    public void showMainScreen() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL ressource = Application.class.getResource("service/view/demographique_main.fxml");
        System.out.println(ressource.getFile());
        loader.setLocation(ressource);
        AnchorPane mAnchorPane = loader.load();
        Scene scene = new Scene(mAnchorPane);
        StageService.getInstance().getPrimaryStage().setScene(scene);
        StageService.getInstance().getPrimaryStage().show();
    }
}
