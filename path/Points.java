package path;

public class Points {

	private boolean[][] points;
	private static int size = 0;
	private int nrOfPointsLeft = 0;

	public Points(int xSize, int ySize) {
		points = new boolean[xSize][ySize];
	}

	public void add(int x, int y) {
		points[x][y] = true;
		size++;
		nrOfPointsLeft++;
	}

	public void remove(int x, int y) {
		points[x][y] = false;
		nrOfPointsLeft--;
	}

	public void remove(Position p) {
		points[p.getX()][p.getY()] = false;
		nrOfPointsLeft--;
	}

	public boolean get(int x, int y) {
		return points[x][y];

	}

	public static int size(){
		return size;
	}

	public void setSize(int size){
		this.size = size;
	}

	public int getNrOfPointsLeft() {
		return nrOfPointsLeft;
	}
}
