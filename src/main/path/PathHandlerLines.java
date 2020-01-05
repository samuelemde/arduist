package main.path;

import main.io.Writer;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class PathHandlerLines {
	private static List<Position> path;
	private static int[][] pixelCategory;
	private static int nrOfShades = 4;
	private static Points points;
	private static int width;
	private static int height;
	private static int x;
	private static int y;
	private static boolean directionUp = true;
	private static boolean directionRight = true;

	private static void analyzeImage() {
		if (nrOfShades > 1) {
			width = (int) ImageLoader.getImage().getWidth();
			height = (int) ImageLoader.getImage().getHeight();
			Image image = ImageLoader.getImage();
			points = new Points(width, height);
			pixelCategory = new int[width][height];

			double factor = 255 / nrOfShades;

			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					Color color = image.getPixelReader().getColor(x, y);
					int shade = (int) ((color.getRed() * 255 + color.getGreen() * 255 + color.getBlue() * 255) / (3 * factor));
					int rgb = (int) ((shade) * factor);

					pixelCategory[x][y] = (int) Math.round((rgb / 255.) / (1. / nrOfShades));
				}
			}
		}
	}

	public static List<Position> calcPath() {
		analyzeImage();
		System.out.println("calculating main.path...");
		x = 2;
		y = 4;
		path = new ArrayList<>();
		Position p = new Position(x, y);
		points.setSize(0);
		path.add(p);
		while (true) {
			p = nextPos(p.getX(), p.getY());
			if (p == null) break;
			path.add(p);
			directionUp = !directionUp;
			points.setSize(Points.size() + 2);
		}
		System.out.println("finished calculation!");
		return path;
	}

	private static Position nextPos(int x, int y) {
		int numberofTrianglesPerRow = (int) (ImageLoader.getImage().getWidth() / 2);
		if (x < 2 || x > (numberofTrianglesPerRow * 2) - 3) {
			directionRight = !directionRight;
			if (y + 15 < ImageLoader.getImage().getHeight() - 1) {
				path.add(new Position(x, y + 15));
				if (!directionRight) {
					x--;
				} else {
					x++;
				}
				return new Position(x, y + 5);
			} else {
				return null;
			}
		}
		int category = getFieldCategorie(new Position(x, y));
		if (directionUp && directionRight) {
			path.add(new Position(x + 1, y - category));
			return new Position(x + 2, y);
		}
		if (directionUp && !directionRight) {
			path.add(new Position(x - 1, y - category));
			return new Position(x - 2, y);
		}
		if (!directionUp && directionRight) {
			path.add(new Position(x + 1, y + category));
			return new Position(x + 2, y);
		}
		if (!directionUp && !directionRight) {
			path.add(new Position(x - 1, y + category));
			return new Position(x - 2, y);
		}
		return new Position(x, y);
	}

	private static int getCategorie(Position p) {
		return pixelCategory[p.getX()][p.getY()];
	}

	private static int getFieldCategorie(Position p) {
		int category = 0;
		for (int x = p.getX() - 1; x < p.getX() + 2; x++) {
			for (int y = p.getY() - 1; y < p.getY() + 2; y++) {
				category += getCategorie(new Position(x, y));
			}
		}
		return 3 - (category / 9);
	}

	public static void setNrOfShades(int nrOfShades) {
		PathHandlerLines.nrOfShades = nrOfShades;
	}

	public static int getNrOfShades() {
		return nrOfShades;
	}

	public static void export(String filename) {
		Writer.writeToFile(path, filename);
	}
}
