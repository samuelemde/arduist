package main.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainGUI extends Application {
	private static Stage stage;

	@Override
	public void start(Stage stage) throws Exception {
		MainGUI.stage = stage;
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
}
