package path;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.io.File;

/**
 * This class loads, resizes and analyzes an image and creates a path.Points object, containing all the points, which then are
 * used to calculate a path by the path.PathHandlerDots.
 */
public class ImageLoader {
	private static Image image;
	private static WritableImage image2;
	private static int width;
	private static int height;
	private static double vBorder;
	private static double hBorder;
	private static PixelReader reader;
	private static PixelWriter writer;


	public static void load(File file, Canvas canvas) {
		width = (int)canvas.getWidth();
		height = (int)canvas.getHeight();
		try {
			image = new Image(file.toURI().toString(),width,height,true,true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		load(image, canvas);
	}

	public static void load(javafx.scene.image.Image image, Canvas canvas) {
		width = (int)canvas.getWidth();
		height = (int)canvas.getHeight();
		reader = image.getPixelReader();
		image2 = new WritableImage((int)image.getWidth(), (int)image.getHeight());
		writer = image2.getPixelWriter();
		for (int x = 0; x < image.getWidth(); x++) {
			for (int y = 0; y < image.getHeight(); y++) {
				int argb = reader.getArgb(x, y);
				writer.setArgb(x, y, argb);
			}
		}
		hBorder = (width - image.getWidth())/2;
		vBorder = (height - image.getHeight())/2;
		System.out.println(hBorder);
		System.out.println(vBorder);
		canvas.getGraphicsContext2D().setFill(Color.WHITE);
		canvas.getGraphicsContext2D().fillRect(0,0, canvas.getWidth(), canvas.getHeight());
		canvas.getGraphicsContext2D().drawImage(image2, hBorder, vBorder);
	}

	public static void update(Canvas canvas) {
		canvas.getGraphicsContext2D().setFill(Color.WHITE);
		canvas.getGraphicsContext2D().fillRect(0,0, canvas.getWidth(), canvas.getHeight());
		canvas.getGraphicsContext2D().drawImage(image2, hBorder, vBorder);
	}

	public static void setImg(Image image) {
		ImageLoader.image = image;
	}

	public static WritableImage getImage() {
		return image2;
	}

	public static double getVBorder() {
		return vBorder;
	}

	public static double getHBorder() {
		return hBorder;
	}

	public static PixelWriter getWriter() { return writer; }

	public static PixelReader getReader() { return reader; }

	public static WritableImage getImageCopy() {
		int height=(int)image.getHeight();
		int width=(int)image.getWidth();
		PixelReader pixelReader=image.getPixelReader();
		WritableImage writableImage = new WritableImage(width,height);
		PixelWriter pixelWriter = writableImage.getPixelWriter();

		for (int y = 0; y < height; y++){
			for (int x = 0; x < width; x++){
				Color color = pixelReader.getColor(x, y);
				pixelWriter.setColor(x, y, color);
			}
		}
		return writableImage;
	}
}
