package main.path;

import main.io.Writer;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;


import java.util.ArrayList;
import java.util.List;

/**
 * This class is responsible of calculating a main.path, which connects all the points from the main.path.Points object.
 */
public class PathHandlerDots {
	private static int width;
	private static int height;
	private static Points points;
	private static int nrOfShades = 5;
	private static List<Position> path;
	private static double slider = 0.1;
	private static double black = 0;
	/**
	 * Analyzes the image and adds points to the boolean[][] points, depending on its greyscale value;
	 */
	private static void analyzeImage() {
		if (nrOfShades > 1) {
			Image image = ImageLoader.getImage();
			width = (int) image.getWidth();
			height = (int) image.getHeight();
			points = new Points(width, height);
			points.setSize(0);

			double factor = 1. / nrOfShades;

			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {

					Color color = image.getPixelReader().getColor(x, y);
					double rgb = (color.getRed() + color.getGreen() + color.getBlue())/3.;
					double shade = rgb / factor;

					if (rgb < 0.5) {
						if (Math.random() < (1.2 - (rgb)) - (slider * shade)-black) {
							points.add(x, y);
						}
					}
					else if (Math.random() < (1 - (rgb)) - (slider * shade)-black) {
						points.add(x, y);
					}
				}
			}
		}
	}

	/**
	 * Calculates a main.path, which connects all the points from the main.path.Points object.
	 * @return Returns a List of main.path.Position in a specific order (main.path).
	 */
	public static List<Position> calcPath() {
		analyzeImage();
		System.out.println("calculating main.path...");
		int x = 0;
		int y = 0;
		findFirst:
		while (y < height) {
			x = 0;
			while (x < width) {
				if (points.get(x, y)){
					break findFirst;
				}
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

	/**
	 * Returns a next valid main.path.Position to add to the main.path, by checking for every point in a increasing rectangle if the
	 * point is part of the main.path.Points object, which contains all points calculated by the main.path.ImageLoader.
	 * @param points The main.path.Points object containing all points calculated by the main.path.ImageLoader.
	 * @param xPos The x coordinate of the previous point.
	 * @param yPos The y coordinate of the previous point.
	 * @return One valid next main.path.Position, which is part of the
	 */
	private static Position nextPos(Points points, int xPos, int yPos) {
		int r = 2;
		List<Position> circle = getRect(points, xPos, yPos, r);
		Position next = circle.get((int) (Math.random() * circle.size()));
		points.remove(next);
		return next;
	}

	/**
	 * Returns a List of possible next Positions, by calculating a rect around the point with coordinates xPos, yPos
	 * and checking, whether the point is part of the main.path.Points object.
	 * @param points The main.path.Points object containing all points calculated by the main.path.ImageLoader.
	 * @param xPos The x coordinate of the previous point.
	 * @param yPos The y coordinate of the previous point.
	 * @param r The parameter defining the edge length of the calculated rectangle (= 2r + 1).
	 * @return Returns either a List of possible next Positions, or calls itself again with an increased r.
	 */
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

	public static void export(String filename) {
		Writer.writeToFile(path, filename);
	}

	public static void setWidth(int width) {
		PathHandlerDots.width = width;
	}

	public static void setHeight(int height) {
		PathHandlerDots.height = height;
	}

	public static void setNrOfShades(int nrOfShades) {
		PathHandlerDots.nrOfShades = nrOfShades;
	}

	public static void setSlider(double slider) {
		PathHandlerDots.slider = slider;
	}

	public static double getSlider() {
		return slider;
	}

	public static double getBlack() {
		return black;
	}

	public static void setBlack(double black) {
		PathHandlerDots.black = black;
	}

	public static Points getPoints() {
		return points;
	}
}

