package path;

public class PathType {

	enum Method {
		LINE, RANDOM, DOTS
	}

	private static Method pathType = Method.DOTS;

	public static void calcPath() {
		switch (pathType) {
			case DOTS:
				PathHandlerDots.calcPath();
				break;
			case LINE:
				break;
			case RANDOM:
				break;
		}
	}
}
