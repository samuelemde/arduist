package path;

import io.Writer;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PathHandlerRandom {
	private static List<Position> path;
	private static int[][] pixelCategory;
	private static int nrOfShades = 3;
	private static int gridSize = 33;
	private static int linesPerGridPoint = 3000;
	private static int radiusShadeOne = 3;
	private static int radiusShadeTwo = 10;
	private static int radiusShadeThree = 50;
	private static int radiusShadeFour = 200;
	private static int width;
	private static int height;
	private static int x;
	private static int y;
	private static int counter = 0;
	private static boolean finished = false;

	private static Points points;




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
		System.out.println("calculating path...");
		x = 0;
		y = 0;
		path = new ArrayList<>();
		Position p = new Position(x, y);
		points.setSize(0);
		path.add(p);
		while (!finished) {
			p = nextPos(p.getX(), p.getY());
			path.add(p);
			points.setSize(Points.size() + 1);
		}
		System.out.println("finished calculation!");
		finished = false;
		return path;
	}

	private static Position nextPos(int xPos, int yPos) {
		Position begin = new Position(xPos, yPos);
		Random random = new Random();
		counter++;
		if (counter > linesPerGridPoint) {
			x++;
			if (x > gridSize) {
				x = 0;
				y++;
			}
			if (y > gridSize) {
				finished = true;
				return begin;
			}
			counter = 0;
			if(getCategory(new Position((width-1)/gridSize*x,(height-1)/gridSize*y)) == nrOfShades-1) return begin;
			return new Position((width - 1) / gridSize * x, (height - 1) / gridSize * y);
		}
		int category = getCategory(begin);
		int radius = 10;
		switch (category) {
			case 0:
				radius = radiusShadeOne;
				counter++;
				break;
			case 1:
				radius = radiusShadeTwo;
				counter = counter + 20;
				break;
			case 2:
				radius = radiusShadeThree;
				counter = counter + 100;
				break;
			case 3:
				radius = radiusShadeFour;
				counter = counter + 100;
				break;
		}

		int i = 0;
		Position newPosition;
		do {
			i++;
			boolean positive = random.nextBoolean();
			int xValue = random.nextInt(radius);
			int x = begin.getX();
			if (positive) {
				x += xValue;
				if (x >= width) x = x - width;
			} else {
				x -= xValue;
				if (x < 0) x += (xValue * 2);
			}

			positive = random.nextBoolean();
			int yValue = random.nextInt(radius);
			int y = begin.getY();
			if (positive) {
				y += yValue;
				if (y >= height) y = y - height;
			} else {
				y -= yValue;
				if (y < 0) y += (yValue * 2);
			}
		//	System.out.println("a");
			newPosition = new Position(x, y);
		} while (i<2 && getCategory(newPosition) != 0);
		//System.out.println("b");
		return newPosition;
	}

	private static int getCategory(Position p) {
		return pixelCategory[p.getX()][p.getY()];
	}

	public void setGridsize(int gridsize) {
		this.gridSize = gridsize;
	}

	public static void setNrOfShades(int nrOfShades) {
		PathHandlerRandom.nrOfShades = nrOfShades;
	}

	public static void setGridSize(int gridSize) {
		PathHandlerRandom.gridSize = gridSize;
	}

	public static void setLinesPerGridPoint(int linesPerGridPoint) {
		PathHandlerRandom.linesPerGridPoint = linesPerGridPoint;
	}

	public static void setRadiusShadeOne(int radiusShadeOne) {
		PathHandlerRandom.radiusShadeOne = radiusShadeOne;
	}

	public static void setRadiusShadeTwo(int radiusShadeTwo) {
		PathHandlerRandom.radiusShadeTwo = radiusShadeTwo;
	}

	public static void setRadiusShadeThree(int radiusShadeThree) {
		PathHandlerRandom.radiusShadeThree = radiusShadeThree;
	}

	public static void setRadiusShadeFour(int radiusShadeFour) {
		PathHandlerRandom.radiusShadeFour = radiusShadeFour;
	}

	public static int getNrOfShades() {
		return nrOfShades;
	}

	public static int getGridSize() {
		return gridSize;
	}

	public static int getLinesPerGridPoint() {
		return linesPerGridPoint;
	}

	public static int getRadiusShadeOne() {
		return radiusShadeOne;
	}

	public static int getRadiusShadeTwo() {
		return radiusShadeTwo;
	}

	public static int getRadiusShadeThree() {
		return radiusShadeThree;
	}

	public static int getRadiusShadeFour() {
		return radiusShadeFour;
	}

	public static void export(String filename) {
		Writer.writeToFile(path, filename);
	}
}
