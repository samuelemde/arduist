package io;

import path.ImageLoader;
import path.Points;
import path.Position;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * This class creates a file and writes the path to a txt file.
 * The first line represents the total amount of points, the second and third are the width and height of
 * the analyzed image. These infos are then read by the io.Reader class and used by the path.DrawBot class to draw the image.
 */
public class Writer {

	public static void writeToFile(List<Position> points, String filename) {

		BufferedWriter bw = null;
		FileWriter fw = null;

		try {
			String content = buildString(points);

			fw = new FileWriter(filename);
			bw = new BufferedWriter(fw);
			bw.write(Points.size()+"\n");
			bw.write(ImageLoader.getImage().getWidth()+"\n");
			bw.write(ImageLoader.getImage().getHeight()+"\n");
			bw.write(content);

			System.out.println("Done");

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

}