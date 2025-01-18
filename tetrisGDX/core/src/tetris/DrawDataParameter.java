package tetris;

public class DrawDataParameter {
	int[][] fs;
	int row;
	int col;
	boolean drawBackground;

	public DrawDataParameter(int[][] fs, int row, int col, boolean drawBackground) {
		this.fs = fs;
		this.row = row;
		this.col = col;
		this.drawBackground = drawBackground;
	}
}