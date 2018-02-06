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
import path.*;

import javax.swing.*;
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
	private Slider sliderBrushSize;
	@FXML
	private Label labelBrushSize;
	@FXML
	private Slider sliderBrushOpacity;
	@FXML
	private Label labelBrushOpacity;


	@FXML
	private Slider sliderDotsGray;
	@FXML
	private Label grayLabelDots;
	@FXML
	private Slider sliderBlack;
	@FXML
	private Label blackLabel;


	@FXML
	private Slider sliderLinesGray;
	@FXML
	private Label grayLabelLines;


	@FXML
	private Slider sliderRandomGray;
	@FXML
	private Label grayLabelRandom;
	@FXML
	private Slider sliderGridSize;
	@FXML
	private Label gridSizeLabel;
	@FXML
	private Slider sliderLinesPerGrid;
	@FXML
	private Label linesPerGridLabel;
	@FXML
	private Slider sliderRadiusOne;
	@FXML
	private Label radiusOneLabel;
	@FXML
	private Slider sliderRadiusTwo;
	@FXML
	private Label radiusTwoLabel;
	@FXML
	private Slider sliderRadiusThree;
	@FXML
	private Label radiusThreeLabel;
	@FXML
	private Slider sliderRadiusFour;
	@FXML
	private Label radiusFourLabel;


	@FXML
	private Label distance;
	@FXML
	private VBox window;
	@FXML
	private AnchorPane windowAnchor;

	@FXML
	private JComponent comp;

	private int x, y;


	@FXML
	public void initialize() {

		ObservableList<String> items = FXCollections.observableArrayList("Dots", "Lines", "Random");
		dropDownMenu.setItems(items);
		sliderDotsGray.setValue(PathHandlerDots.getSlider());
		grayLabelDots.setText(String.format("%.2f", sliderDotsGray.getValue()));
		sliderBlack.setValue(PathHandlerDots.getBlack());
		blackLabel.setText(String.format("%.2f", sliderBlack.getValue()));
		sliderLinesGray.setValue(PathHandlerLines.getNrOfShades());
		grayLabelLines.setText(String.format("%.0f", sliderLinesGray.getValue()));
		sliderRandomGray.setValue(PathHandlerRandom.getNrOfShades());
		grayLabelRandom.setText(String.format("%.0f", sliderRandomGray.getValue()));
		sliderGridSize.setValue(PathHandlerRandom.getGridSize());
		gridSizeLabel.setText(String.format("%.0f", sliderGridSize.getValue()));
		sliderLinesPerGrid.setValue(PathHandlerRandom.getLinesPerGridPoint());
		linesPerGridLabel.setText(String.format("%.0f", sliderLinesPerGrid.getValue()));
		sliderRadiusOne.setValue(PathHandlerRandom.getRadiusShadeOne());
		radiusOneLabel.setText(String.format("%.0f", sliderRadiusOne.getValue()));
		sliderRadiusTwo.setValue(PathHandlerRandom.getRadiusShadeTwo());
		radiusTwoLabel.setText(String.format("%.0f", sliderRadiusTwo.getValue()));
		sliderRadiusThree.setValue(PathHandlerRandom.getRadiusShadeThree());
		radiusThreeLabel.setText(String.format("%.0f", sliderRadiusThree.getValue()));
		sliderRadiusFour.setValue(PathHandlerRandom.getRadiusShadeFour());
		radiusFourLabel.setText(String.format("%.0f", sliderRadiusFour.getValue()));

	}

	@FXML
	public void LoadImage(ActionEvent event) {
		FileChooser fc = new FileChooser();
		File file = fc.showOpenDialog(MainGUI.getStage());
		if (file != null) {
			ImageLoader.load(file, imageCanvas);
			tabPane.getSelectionModel().select(0);
			addDrawArea(imageCanvas);
		}
	}

	@FXML
	public void dropDownAction(ActionEvent event) {
		method = dropDownMenu.getValue().toString();
		switch (method) {
			case "Dots":
				paramsLines.setVisible(false);
				paramsRandom.setVisible(false);
				paramsDots.setVisible(true);
				break;
			case "Lines":
				paramsDots.setVisible(false);
				paramsRandom.setVisible(false);
				paramsLines.setVisible(true);
				break;
			case "Random":
				paramsDots.setVisible(false);
				paramsLines.setVisible(false);
				paramsRandom.setVisible(true);
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
				new DrawBot(drawCanvas, PathHandlerRandom.calcPath()).draw();
				tabPane.getSelectionModel().select(1);
				distance.setText(String.valueOf(DrawBot.getDistance()));
				break;
		}
	}

	public void exportButtonAction(ActionEvent event) {
		FileChooser fc = new FileChooser();
		File file = fc.showSaveDialog(MainGUI.getStage());
		if (file != null) {
			switch (method) {
				case "Dots":
					PathHandlerDots.export(file.getAbsolutePath() + ".txt");
					break;
				case "Lines":
					PathHandlerLines.export(file.getAbsolutePath() + ".txt");
					break;
				case "Random":
					PathHandlerRandom.export(file.getAbsolutePath() + ".txt");
					break;
			}
		}
	}

	public void sliderBrushSizeAction(MouseEvent event) {
		DrawArea.setBrushSize((int)sliderBrushSize.getValue());
	}

	public void sliderBrushSizeLabelAction(MouseEvent event) {
		labelBrushSize.setText(String.format("%d", (int)sliderBrushSize.getValue()));
		DrawArea.setBrushSize((int)sliderBrushSize.getValue());
	}

	public void sliderBrushOpacityAction(MouseEvent event) {
		DrawArea.setOpacity((int)sliderBrushOpacity.getValue());
	}

	public void sliderBrushOpacityLabelAction(MouseEvent event) {
		labelBrushOpacity.setText(String.format("%.2f", sliderBrushOpacity.getValue()));
		DrawArea.setOpacity((int)sliderBrushOpacity.getValue());
	}


	public void sliderGrayAction(MouseEvent event) {
		PathHandlerDots.setSlider(sliderDotsGray.getValue());

	}
	public void sliderGrayLabelAction(MouseEvent event) {
		grayLabelDots.setText(String.format("%.2f", sliderDotsGray.getValue()));
		PathHandlerDots.setSlider(sliderDotsGray.getValue());
	}
	public void sliderBlackAction(MouseEvent event) {
		PathHandlerDots.setBlack(sliderBlack.getValue());

	}
	public void sliderBlackLabelAction(MouseEvent event) {
		blackLabel.setText(String.format("%.2f", sliderBlack.getValue()));
		PathHandlerDots.setBlack(sliderBlack.getValue());
	}

	public void sliderGrayLinesAction(MouseEvent event) {
		PathHandlerLines.setNrOfShades((int) sliderLinesGray.getValue());

	}
	public void sliderGrayLinesLabelAction(MouseEvent event) {
		grayLabelLines.setText(String.format("%.0f", sliderLinesGray.getValue()));
		PathHandlerLines.setNrOfShades((int) sliderLinesGray.getValue());
	}

	public void sliderGrayRandomAction(MouseEvent event) {
		PathHandlerRandom.setNrOfShades((int) sliderLinesGray.getValue());

	}
	public void sliderGrayRandomLabelAction(MouseEvent event) {
		grayLabelRandom.setText(String.format("%.0f", sliderRandomGray.getValue()));
		PathHandlerRandom.setNrOfShades((int) sliderRandomGray.getValue());
	}
	public void sliderGridSizeAction(MouseEvent event) {
		PathHandlerRandom.setGridSize((int) sliderGridSize.getValue());
	}
	public void sliderGridSizeLabelAction(MouseEvent event) {
		gridSizeLabel.setText(String.format("%.0f", sliderGridSize.getValue()));
		PathHandlerRandom.setGridSize((int) sliderGridSize.getValue());
	}
	public void sliderLinesPerGridAction(MouseEvent event) {
		PathHandlerRandom.setLinesPerGridPoint((int) sliderLinesPerGrid.getValue());

	}
	public void sliderLinesPerGridLabelAction(MouseEvent event) {
		linesPerGridLabel.setText(String.format("%.0f", sliderLinesPerGrid.getValue()));
		PathHandlerRandom.setLinesPerGridPoint((int) sliderLinesPerGrid.getValue());
	}
	public void sliderRadiusOneAction(MouseEvent event) {
		PathHandlerRandom.setRadiusShadeOne((int) sliderRadiusOne.getValue());

	}
	public void sliderRadiusOneLabelAction(MouseEvent event) {
		radiusOneLabel.setText(String.format("%.0f", sliderRadiusOne.getValue()));
		PathHandlerRandom.setRadiusShadeOne((int) sliderRadiusOne.getValue());
	}
	public void sliderRadiusTwoAction(MouseEvent event) {
		PathHandlerRandom.setRadiusShadeTwo((int) sliderRadiusTwo.getValue());

	}
	public void sliderRadiusTwoLabelAction(MouseEvent event) {
		radiusTwoLabel.setText(String.format("%.0f", sliderRadiusTwo.getValue()));
		PathHandlerRandom.setRadiusShadeTwo((int) sliderRadiusTwo.getValue());
	}
	public void sliderRadiusThreeAction(MouseEvent event) {
		PathHandlerRandom.setRadiusShadeThree((int) sliderRadiusThree.getValue());

	}
	public void sliderRadiusThreeLabelAction(MouseEvent event) {
		radiusThreeLabel.setText(String.format("%.0f", sliderRadiusThree.getValue()));
		PathHandlerRandom.setRadiusShadeThree((int) sliderRadiusThree.getValue());
	}
	public void sliderRadiusFourAction(MouseEvent event) {
		PathHandlerRandom.setRadiusShadeFour((int) sliderRadiusFour.getValue());

	}
	public void sliderRadiusFourLabelAction(MouseEvent event) {
		radiusFourLabel.setText(String.format("%.0f", sliderRadiusFour.getValue()));
		PathHandlerRandom.setRadiusShadeFour((int) sliderRadiusFour.getValue());
	}

	public void addDrawArea(Canvas canvas) {
		DrawArea drawArea = new DrawArea(canvas);
	}

}
