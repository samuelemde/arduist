package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sun.applet.Main;

import javax.swing.*;

public class MainGUI extends Application {
	private static Stage stage;

	@Override
	public void start(Stage stage) throws Exception {
		MainGUI.stage = stage;
		Parent root = FXMLLoader.load(getClass().getResource("MainFXML.fxml"));
		Scene scene = new Scene(root);
		stage.setTitle("Arduist");
		stage.setScene(scene);
		stage.setResizable(true);
		stage.show();
		MainGUI.stage.setMinHeight(stage.getHeight());
		MainGUI.stage.setMinWidth(stage.getWidth());
	}


	public static Stage getStage() {
		return stage;
	}
}
