package kiu.tetris;

import com.badlogic.gdx.ScreenAdapter;

public class TetrisScreen extends ScreenAdapter {
	
	TetrisStage stage;

	@Override
	public void show() {
		stage = new TetrisStage();
		stage.init();
	}

	@Override
	public void render(float delta) {
        stage.act(delta);
        stage.draw();
	}

	@Override
	public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
	}
}
