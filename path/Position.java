package path;

public class Position {
	private int x;
	private int y;
	private static int xOffset;
	private static int yOffset;

	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public String write() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.getX()+xOffset+Math.round(ImageLoader.getHBorder()));
		sb.append("/");
		sb.append(this.getY()+yOffset+Math.round(ImageLoader.getVBorder()));
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

	public static void setFormat(int xOffset, int yOffset) {
		Position.xOffset = xOffset;
		Position.yOffset = yOffset;
	}

	public static int[] getOffsets() {
		return new int[]{xOffset, yOffset};
	}


}

