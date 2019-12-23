package path;

public class Points {

	private boolean[][] points;
	private static int size = 0;
	private int xSize;
	private int ySize;
	private int nrOfPointsLeft = 0;

	public Points(int xSize, int ySize) {
			this.xSize = xSize;
			this.ySize = ySize;
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
			if(x < 0 || y <0 || x >= xSize || y >= ySize) return false;
			return points[x][y];

	}

	public Position getRandom(){
			for(int x = 0; x < xSize; x++){
					for(int y = 0; y < ySize; y++){
							if(points[x][y]) return new Position(x,y);
					}
			}
			return null;
	}

	public static int size() {
			return size;
	}

	public void setSize(int size){
		Points.size = size;
	}

	public int getNrOfPointsLeft() {
		return nrOfPointsLeft;
	}

	public Position getFirst() {
		int x = 0;
		int y = 0;
		while (y < points[0].length) {
			x = 0;
			while (x < points.length) {
				if (this.get(x, y)){
					break;
				}
				x++;
			}
			y++;
		}
		return new Position(x,y);
	}
}
