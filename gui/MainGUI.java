package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import javax.swing.*;

public class MainGUI extends Application {
	private static Stage stage;

	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		Parent root = FXMLLoader.load(getClass().getResource("MainFXML.fxml"));
		Scene scene = new Scene(root);
		stage.setTitle("Arduist");
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();
	}

	public static Stage getStage() {
		return stage;
	}

//	public static void main(String[] args) {
//		launch(args);
//	}
}
