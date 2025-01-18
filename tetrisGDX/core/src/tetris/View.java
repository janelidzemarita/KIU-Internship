package tetris;

import java.awt.*;

public class View {

	static final int BOX_SIZE = 30;
	static final int ORIGIN = 50;
	private final Graphics graphics;

	private final Font font = new Font("Arial", Font.BOLD, 12);

	public View(Graphics graphics) {
		this.graphics = graphics;
	}

	public void showScore(int score) {
        graphics.setFont(font);
		graphics.setColor(Color.white);
		graphics.fillRect(50, 10, 150, 30);

		graphics.setColor(Color.black);
		graphics.drawString("Total Score: " + score, 50, 30);
	}

	public void showLevel(int level) {
		graphics.setColor(Color.white);
		graphics.fillRect(150, 10, 200, 30);
		graphics.setFont(font);
		graphics.setColor(Color.black);
		graphics.drawString("Level: " + level, 300, 30);
	}

	public void showGameOver() {
		graphics.setColor(Color.darkGray);
		graphics.fillRect(50, 245, 300, 80); // Clear the area

		graphics.setColor(Color.red.darker());
        graphics.setFont(new Font("Arial", Font.BOLD, 40));

		graphics.drawString("GAME OVER", 80, 300);
	}


	public void draw(TetrisModel model) {
		showScore(model.state.getScore());
		showLevel(model.state.getLevel());
		drawData(new DrawDataParameter(model.state.getField(), 0, 0, true));
		drawData(new DrawDataParameter(model.state.getFigure(),
				model.state.getPosition().y(), model.state.getPosition().x(), false));
	}

	private void drawData(DrawDataParameter parameterObject) {
		for (int r = 0; r < parameterObject.fs.length; r++) {
			for (int c = 0; c < parameterObject.fs[r].length; c++) {
				if (!parameterObject.drawBackground && parameterObject.fs[r][c] == 0)
					continue;
				drawBox(r + parameterObject.row, c + parameterObject.col, parameterObject.fs[r][c]);
			}
		}
	}

	private void drawBox(int col, int row, int colorIndex) {
		graphics.drawBoxAt(ORIGIN + col * View.BOX_SIZE, ORIGIN + row * View.BOX_SIZE, colorIndex);
	}
}