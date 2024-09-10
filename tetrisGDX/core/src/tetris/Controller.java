package tetris;


public class Controller implements ModelListener, GameEventsListener {

	private final TetrisModel model;
	private final View view;

	public Controller(TetrisModel model, View view) {
		this.model = model;
		this.view = view;
		model.addListener(this);
	}

	@Override
	public void onChange(TetrisModel model) {
		view.draw(model);
	}

	@Override
	public void scoreHasChanged(TetrisModel model) {
		view.showScore(model.state.getScore());
	}

	@Override
	public void levelHasChanged(TetrisModel model) {
		view.showLevel(model.state.getLevel());
	}

	@Override
	public void gameOver(TetrisModel model) {
		view.showGameOver();
	}

	@Override
	public void slideDown() {
		model.slideDown();
	}

	@Override
	public void moveLeft() {
		model.moveLeft();
	}

	@Override
	public void moveRight() {
		model.moveRight();
	}

	@Override
	public void rotate() {
		model.rotate();
	}

	@Override
	public void drop() {
		model.drop();
	}

	@Override
	public void pause() {
		model.pause();
	}

	@Override
	public void gameOver() {
		model.gameOver();
	}

	public void restart() {
		model.restartGame();
	}

	public void quitGame() {
		System.exit(0);
	}
}