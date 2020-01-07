package path;

import gui.MainFXMLController;

public class Position {
	private int x;
	private int y;

	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public String write() {
		StringBuilder sb = new StringBuilder();
		double factorInterleave = 1;
//		if (MainFXMLController.interleave) factorInterleave = 2;
		sb.append(this.getX() / factorInterleave + Math.round(ImageLoader.getHBorder()));
		sb.append("/");
		sb.append(this.getY() / factorInterleave + Math.round(ImageLoader.getVBorder()));
		return sb.toString();
	}

	public static Position read(String s) {
		String[] coord = s.split("/");
		return new Position(Integer.parseInt(coord[0]), Integer.parseInt(coord[1]));
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}


}

