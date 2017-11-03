package io;

import path.Position;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The class reads out a txt file and converts it into a list of Positions.
 */
public class Reader {
	private static int size;
	private static int width;
	private static int height;

	public static List<Position> readFromFile(String path) {
		List<Position> points = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			String currentLine;
			size = Integer.parseInt(br.readLine());
			width = Integer.parseInt(br.readLine());
			height = Integer.parseInt(br.readLine());
			while ((currentLine = br.readLine()) != null) {
				points.add(Position.read(currentLine));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return points;
	}

	public static int getSize() {
		return size;
	}

	public static int getWidth() {
		return width;
	}

	public static int getHeight() {
		return height;
	}
}