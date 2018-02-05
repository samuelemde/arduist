package path;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import java.io.File;

/**
 * This class loads, resizes and analyzes an image and creates a path.Points object, containing all the points, which then are
 * used to calculate a path by the path.PathHandlerDots.
 */
public class ImageLoader {
	private static Image image;
	private static int width = 565;
	private static int height = 800;
	private static double vBorder;
	private static double hBorder;


	public static void load(File file, Canvas canvas) {
		try {
			image = new Image(file.toURI().toString(),width,height,true,true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		hBorder = (width - image.getWidth())/2;
		vBorder = (height - image.getHeight())/2;
		System.out.println(hBorder);
		System.out.println(vBorder);
		canvas.getGraphicsContext2D().clearRect(0,0, canvas.getWidth(), canvas.getHeight());
		canvas.getGraphicsContext2D().drawImage(image, hBorder, vBorder);
	}

	public static void setImg(Image image) {
		ImageLoader.image = image;
	}

	public static Image getImage() {
		return image;
	}

	public static double getVBorder() {
		return vBorder;
	}

	public static double getHBorder() {
		return hBorder;
	}
}
