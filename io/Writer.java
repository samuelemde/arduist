package io;

import path.ImageLoader;
import path.Points;
import path.Position;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * This class creates a file and writes all points separated by a new line character to a txt file.
 * These points are then read by the io.Reader class and used by the path.DrawBot class to draw the image.
 * The first line represents the total amount of points.
 */
public class Writer {

	private static String layout;

	public static void writeToFile(List<Position> points, String filename) {

		BufferedWriter bw = null;
		FileWriter fw = null;

		try {
			String content = buildString(points);

			fw = new FileWriter(filename);
			bw = new BufferedWriter(fw);
			bw.write(Points.size()+"\n");
			switch (layout) {
				case "50x50":
					bw.write("100/100\n");
					break;
				case "A2":
					bw.write("100/140\n");
					break;
				case "A3":
					bw.write("140/202\n");
					break;
				case "A4":
					bw.write("202/245\n");
					break;
			}
			bw.write(content);

		} catch (IOException e) {
			e.printStackTrace();

		} finally {
			try {
				if (bw != null)
					bw.close();
				if (fw != null)
					fw.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	private static String buildString(List<Position> points) {
		StringBuilder sb = new StringBuilder();
		for (Position p : points) {
			sb.append(p.write());
			sb.append("\n");
		}
		return sb.toString();
	}

	public static void setLayout(String layout) {
		Writer.layout = layout;
	}
}