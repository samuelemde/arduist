package path;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.List;

/**
 * This class actually draws the path to the canvas.
 */
public class DrawBot {
	private Canvas canvas;
	private List<Position> path;
	private int size;
	private GraphicsContext gc;
	private double vBorder;
	private double hBorder;
	private static int distance;

	public DrawBot(Canvas canvas, List<Position> path) {
		this.canvas = canvas;
		this.path = path;
		size = path.size();
		gc = canvas.getGraphicsContext2D();
		vBorder = ImageLoader.getVBorder();
		hBorder = ImageLoader.getHBorder();
	}

	public void draw() {
		distance = 0;
		gc.setFill(Color.WHITE);
		gc.fillRect(0,0, canvas.getWidth(), canvas.getHeight());
		System.out.println(size);
		for (int i = 0; i < size-1; i++) {
			drawLine(path.get(i), path.get(i+1));
			int x = Math.abs(path.get(i).getX()-path.get(i+1).getX());
			int y = Math.abs(path.get(i).getY()-path.get(i+1).getY());
			int temp =(int) Math.sqrt(x*x + y*y);
			distance += temp;
		}
	}

	private void drawLine(Position a, Position b) {
		gc = canvas.getGraphicsContext2D();
		gc.strokeLine(a.getX()+hBorder, a.getY()+vBorder, b.getX()+hBorder, b.getY()+vBorder);
	}

	public static int getDistance() {
		return distance;
	}

	public static void setDistance(int distance) {
		DrawBot.distance = distance;
	}
}
