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
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import path.DrawBot;
import path.ImageLoader;
import path.PathHandlerDots;
import path.PathHandlerLines;

import java.io.File;

public class MainFXMLController {

	private String method;

	@FXML
	private VBox paramsDots;

	@FXML
	private VBox paramsLines;

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
	private Slider sliderDotsGray;

	@FXML
	private Label grayLabelDots;

	@FXML
	private Slider sliderDotsBlack;

	@FXML
	private Label blackLabelDots;

	@FXML
	private Slider sliderLinesGray;

	@FXML
	private Label grayLabelLines;

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
		sliderDotsGray.setValue(PathHandlerDots.getSlider());
		grayLabelDots.setText(String.format("%.2f", sliderDotsGray.getValue()));
		sliderDotsBlack.setValue(PathHandlerDots.getBlack());
		blackLabelDots.setText(String.format("%.2f", sliderDotsBlack.getValue()));
		sliderDotsGray.setValue(PathHandlerLines.getNrOfShades());
		grayLabelDots.setText(String.format("%.2f", sliderLinesGray.getValue()));
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

				paramsLines.setVisible(false);
//				paramsRandom.setVisible(false);
				paramsDots.setVisible(true);
				break;
			case "Lines":
				paramsDots.setVisible(false);
//				paramsRandom.setVisible(false);
				paramsLines.setVisible(true);
				break;
			case "Random":
				paramsDots.setVisible(false);
				paramsLines.setVisible(false);
//				paramsRandom.setVisible(true);
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
				new DrawBot(drawCanvas, PathHandlerLines.calcPath()).draw();
				tabPane.getSelectionModel().select(1);
				distance.setText(String.valueOf(DrawBot.getDistance()));
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
		PathHandlerDots.setSlider(sliderDotsGray.getValue());

	}

	public void sliderGrayLabelAction(MouseEvent event) {
		grayLabelDots.setText(String.format("%.2f", sliderDotsGray.getValue()));
		PathHandlerDots.setSlider(sliderDotsGray.getValue());
	}

	public void sliderBlackAction(MouseEvent event) {
		PathHandlerDots.setBlack(sliderDotsBlack.getValue());

	}

	public void sliderBlackLabelAction(MouseEvent event) {
		blackLabelDots.setText(String.format("%.2f", sliderDotsBlack.getValue()));
		PathHandlerDots.setBlack(sliderDotsBlack.getValue());
	}

	public void sliderGrayLinesAction(MouseEvent event) {
		PathHandlerLines.setNrOfShades((int)sliderLinesGray.getValue());

	}

	public void sliderGrayLinesLabelAction(MouseEvent event) {
		grayLabelLines.setText(String.format("%.2f", sliderLinesGray.getValue()));
		PathHandlerLines.setNrOfShades((int)sliderLinesGray.getValue());
	}



}
