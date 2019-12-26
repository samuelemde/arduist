package path;

import io.Writer;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by joelg on 26.11.2017.
 */
public class PathHandlerContour {
	private static int width;
	private static int height;
	private static Points points;
	private static List<Position> path;
	private static int[][] darkArea;
	private static int darkValue = 122;
	private static Canvas canvas;
	private static int blur = 5;

	private static void analyzeImage() {
		Image image = ImageLoader.getImageCopy();

		width = (int) image.getWidth();
		height = (int) image.getHeight();
		int radius = blur;
		int size = radius * 2 + 1;
		float weight = 1.0f / (size * size);
		float[] data = new float[size * size];

		for (int i = 0; i < data.length; i++) {
			data[i] = weight;
		}

		Kernel kernel = new Kernel(size, size, data);
		ConvolveOp op = new ConvolveOp(kernel, ConvolveOp.EDGE_ZERO_FILL, null);
		BufferedImage bImg = SwingFXUtils.fromFXImage(image, null);
		bImg = op.filter(bImg, null);
		image = SwingFXUtils.toFXImage(bImg, null);

//		refreshImage(image);

		points = new Points(width, height);
		points.setSize(0);

		WritableImage wi = new WritableImage(width, height);
		int i = 0;
		darkArea = new int[width][height];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				Color color = image.getPixelReader().getColor(x, y);
				darkArea[x][y] = ((int) ((color.getRed() * 255 + color.getGreen() * 255 + color.getBlue() * 255) / 3)) > darkValue ? 1 : 0;
				if (((int) ((color.getRed() * 255 + color.getGreen() * 255 + color.getBlue() * 255) / 3)) > darkValue + 70)
					darkArea[x][y] = 2;
				if (((int) ((color.getRed() * 255 + color.getGreen() * 255 + color.getBlue() * 255) / 3)) > darkValue + 120)
					darkArea[x][y] = 3;
				//								if (darkArea[x][y]) wi.getPixelWriter().setColor(x, y, Color.WHITE);
				//								else wi.getPixelWriter().setColor(x, y, Color.BLACK);
			}
		}


		for (int y = 1; y < height - 2; y++) {
			for (int x = 1; x < width - 2; x++) {
				if (isContour(x, y)) {
					//										if(isContour(x,y))wi.getPixelWriter().setColor(x,y,Color.BLACK);else wi.getPixelWriter().setColor(x,y,Color.WHITE);
					points.add(x, y);
				}
			}
		}

//				for (int y = 0; y < height; y++) {
//						for (int x = 0; x < width; x++) {
//								Color color = image.getPixelReader().getColor(x, y);
//								darkArea[x][y] = ((int) ((color.getRed() * 255 + color.getGreen() * 255 + color.getBlue() * 255) / 3)) > darkValue+40;
//								//								if (darkArea[x][y]) wi.getPixelWriter().setColor(x, y, Color.WHITE);
//								//								else wi.getPixelWriter().setColor(x, y, Color.BLACK);
//						}
//				}


//				for (int y = 1; y < height - 2; y++) {
//						for (int x = 1; x < width - 2; x++) {
//								if (isContour(x, y)) {
//										//										if(isContour(x,y))wi.getPixelWriter().setColor(x,y,Color.BLACK);else wi.getPixelWriter().setColor(x,y,Color.WHITE);
//										points.add(x, y);
//								}
//						}
//				}
//						image = wi;
//
//				refreshImage(image);
	}


	public static List<Position> calcPath() {
		analyzeImage();
		System.out.println("calculating path...");
		int x = 0;
		int y = 0;
		findFirst:
		while (y < height) {
			while (x < width) {
				if (points.get(x, y)) break findFirst;
				x++;
			}
			y++;
		}
		path = new ArrayList<>();
		Position p = new Position(x, y);
		path.add(p);
		while (points.getNrOfPointsLeft() > 0) {
			p = nextPos(points, p.getX(), p.getY());
			path.add(p);
		}
		System.out.println("finished calculation!");
		return path;
	}

	private static Position nextPos(Points points, int xPos, int yPos) {
//				int r = 1;
//				List<Position> circle = getRect(points, xPos, yPos, r);
//				Position next = circle.get((int) (Math.random() * circle.size()));
//				points.remove(next);
//				return next;
		if (points.get(xPos + 1, yPos)) {
			points.remove(xPos + 1, yPos);
			return new Position(xPos + 1, yPos);
		}
		if (points.get(xPos + 1, yPos + 1)) {
			points.remove(xPos + 1, yPos + 1);
			return new Position(xPos + 1, yPos + 1);
		}
		if (points.get(xPos, yPos + 1)) {
			points.remove(xPos, yPos + 1);
			return new Position(xPos, yPos + 1);
		}
		if (points.get(xPos - 1, yPos + 1)) {
			points.remove(xPos - 1, yPos + 1);
			return new Position(xPos - 1, yPos + 1);
		}
		if (points.get(xPos - 1, yPos)) {
			points.remove(xPos - 1, yPos);
			return new Position(xPos - 1, yPos);
		}
		if (points.get(xPos - 1, yPos - 1)) {
			points.remove(xPos - 1, yPos - 1);
			return new Position(xPos - 1, yPos - 1);
		}
		if (points.get(xPos, yPos - 1)) {
			points.remove(xPos, yPos - 1);
			return new Position(xPos, yPos - 1);
		}
		if (points.get(xPos + 1, yPos - 1)) {
			points.remove(xPos + 1, yPos - 1);
			return new Position(xPos + 1, yPos - 1);
		}
		int r = 1;
		List<Position> circle = getRect(points, xPos, yPos, r);
		Position next = circle.get((int) (Math.random() * circle.size()));
		points.remove(next);
		return next;
//				Position next = points.getRandom();
//				points.remove(next);
//				return next;

	}

	private static List<Position> getRect(Points points, int xPos, int yPos, int r) {
		List<Position> list = new ArrayList<>();
		for (int y = Math.max(0, yPos - r); y < Math.min(height, yPos + r); y++) {
			for (int x = Math.max(0, xPos - r); x < Math.min(width, xPos + r); x++) {
				if (points.get(x, y)) {
					list.add(new Position(x, y));
				}
			}
		}
		return list.size() > 0 ? list : getRect(points, xPos, yPos, r + 2);
	}

	private static boolean isContour(int x, int y) {
		if (darkArea[x][y] == 1) {
			int neighbours = 0;
			if (darkArea[x + 1][y] != 1) neighbours++;
			if (darkArea[x][y - 1] != 1) neighbours++;
			if (darkArea[x - 1][y] != 1) neighbours++;
			if (darkArea[x][y + 1] != 1) neighbours++;
			return neighbours >= 1;
		}
		if (darkArea[x][y] == 2) {
			int neighbours = 0;
			if (darkArea[x + 1][y] != 2) neighbours++;
			if (darkArea[x][y - 1] != 2) neighbours++;
			if (darkArea[x - 1][y] != 2) neighbours++;
			if (darkArea[x][y + 1] != 2) neighbours++;
			return neighbours >= 1;
		}
		return false;
	}

	public static void refreshImage(javafx.scene.image.Image image) {
		ImageLoader.load(image, canvas);
	}

	public static void export(String filename) {
		Writer.writeToFile(path, filename);
	}

	public static void setWidth(int width) {
		PathHandlerContour.width = width;
	}

	public static void setHeight(int height) {
		PathHandlerContour.height = height;
	}


	public static Points getPoints() {
		return points;
	}

	public static double getPrecision() {
		return darkValue;
	}

	public static void setPrecision(int precision) {
		PathHandlerContour.darkValue = precision;
	}

	public static void setCanvas(Canvas canvas) {
		PathHandlerContour.canvas = canvas;
	}
}
