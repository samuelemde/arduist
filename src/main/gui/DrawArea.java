package main.gui;

import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import main.path.ImageLoader;

public class DrawArea {
	private static int brushSize = 30;
	private static double opacity = 0;
	private WritableImage image;
	private GraphicsContext gc;
	private int clickedX, clickedY;
	private PixelWriter writer;
	private Canvas canvas;
	Color c = new Color(1,1,1,1);

	public DrawArea(Canvas canvas) {
		this.canvas = canvas;
		image = ImageLoader.getImage();
		writer = ImageLoader.getImage().getPixelWriter();
		canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {

				drawCircle((int)(e.getX()-ImageLoader.getHBorder()), (int)(e.getY()-ImageLoader.getVBorder()));
			}
		});

		canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				drawCircle((int)(e.getX()-ImageLoader.getHBorder()), (int)(e.getY()-ImageLoader.getVBorder()));
			}
		});

	}

	private void drawCircle(int x, int y) {
		for (int x2 = -brushSize; x2 < brushSize; x2++) {
			if (x+x2 >= 0 && x+x2 < image.getWidth()) {
				for (int y2 = -brushSize; y2 < brushSize; y2++) {
					if (y+y2 >= 0 && y+y2 < image.getHeight()) {
						double r = Math.sqrt(Math.pow(x2, 2) + Math.pow(y2, 2));
						if (r < brushSize) {
							if (Math.random() < (1 - (r / brushSize)+opacity)) {
								System.out.println(r);
								writer.setColor(x + x2, y + y2, c);
								ImageLoader.update(canvas);
							}
						}
					}
				}
			}
		}
	}

	public static void setBrushSize(int slider) {
		brushSize = slider;
	}

	public static void setOpacity(double slider) {
		opacity = slider;
	}
}
