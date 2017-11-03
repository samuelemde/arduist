package gui;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import javafx.stage.FileChooser;
import path.DrawBot;
import path.ImageLoader;
import path.PathHandlerDots;

import javax.swing.*;
import java.io.File;

public class MainFXMLController {

	private String method;

	@FXML
	private VBox paramsDots;

	@FXML
	private VBox paramsLine;

	@FXML
	private VBox paramsRandom;

	@FXML
	private Canvas imageCanvas;

	@FXML
	private JFXComboBox dropDownMenu;

	@FXML
	private Canvas drawCanvas;

	@FXML
	private TabPane tabPane;

	@FXML
	private Slider sliderGray;

	@FXML
	private Label grayLabel;

	@FXML
	private Slider sliderBlack;

	@FXML
	private Label blackLabel;

	@FXML
	private Label distance;

	@FXML
	private VBox window;

	@FXML
	private AnchorPane windowAnchor;



	@FXML
	public void initialize() {

		ObservableList<String> items = FXCollections.observableArrayList("Dots", "Lines", "Random");
		dropDownMenu.setItems(items);
		sliderGray.setValue(PathHandlerDots.getSlider());
		grayLabel.setText(String.format("%.2f",sliderGray.getValue()));
		sliderBlack.setValue(PathHandlerDots.getBlack());
		blackLabel.setText(String.format("%.2f",sliderBlack.getValue()));
	}

	@FXML
	public void LoadImage(ActionEvent event) {
		FileChooser fc = new FileChooser();
		File file = fc.showOpenDialog(MainGUI.getStage());
		if (file != null) {
			ImageLoader.load(file, imageCanvas);
			tabPane.getSelectionModel().select(0);
		}
	}

	@FXML
	public void dropDownAction(ActionEvent event) {
		method = dropDownMenu.getValue().toString();
		switch (method) {
			case "Dots":
				paramsDots.setVisible(true);
				break;
			case "Lines":
				paramsDots.setVisible(false);
				break;
			case "Random":
				paramsDots.setVisible(false);
				break;
		}
	}

	@FXML
	public void previewButtonAction(ActionEvent event) {
		switch (method) {
			case "Dots":
				new DrawBot(drawCanvas, PathHandlerDots.calcPath()).draw();
				tabPane.getSelectionModel().select(1);
				distance.setText(String.valueOf(DrawBot.getDistance()));
				break;
			case "Lines":

				break;
			case "Random":

				break;
		}
	}

	public void exportButtonAction(ActionEvent event) {
		FileChooser fc = new FileChooser();
		File file = fc.showSaveDialog(MainGUI.getStage());
		if (file != null) {
			switch (method) {
				case "Dots":
					PathHandlerDots.export(file.getAbsolutePath()+".txt");
					break;
				case "Lines":

					break;
				case "Random":

					break;
			}
		}
	}

	public void sliderGrayAction(MouseEvent event) {
		PathHandlerDots.setSlider(sliderGray.getValue());

	}

	public void sliderGrayLabelAction(MouseEvent event) {
		grayLabel.setText(String.format("%.2f",sliderGray.getValue()));
		PathHandlerDots.setSlider(sliderGray.getValue());
	}

	public void sliderBlackAction(MouseEvent event) {
		PathHandlerDots.setBlack(sliderBlack.getValue());

	}

	public void sliderBlackLabelAction(MouseEvent event) {
		blackLabel.setText(String.format("%.2f",sliderBlack.getValue()));
		PathHandlerDots.setBlack(sliderBlack.getValue());
	}



}
