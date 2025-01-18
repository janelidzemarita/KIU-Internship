package tetris;

public interface GameEventsListener {

	void slideDown();
	void moveLeft();
	void moveRight();
	void rotate();
	void drop();
	void pause();
	void gameOver();
}