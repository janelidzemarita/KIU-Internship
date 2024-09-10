package tetris;

public interface ModelListener {

	void onChange(TetrisModel model);

	void scoreHasChanged(TetrisModel model);

	void levelHasChanged(TetrisModel model);

	void gameOver(TetrisModel model);
}