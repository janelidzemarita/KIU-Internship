package kiu.tetris;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import tetris.Controller;
import tetris.Graphics;
import tetris.TetrisModel;
import tetris.View;

import java.awt.*;

public class TetrisStage extends Stage implements Graphics {

	static final Color[] COLORS = {
			Color.DARK_GRAY, Color.RED, Color.GREEN,
			Color.BLUE, Color.CYAN, Color.YELLOW,
			Color.MAGENTA, Color.MAROON};

	private final ShapeRenderer shape;
	private View view;
	private TetrisModel model;
	private Controller controller;
	private BitmapFont font = new BitmapFont();

	public TetrisStage() {
		OrthographicCamera camera = new OrthographicCamera();
		camera.setToOrtho(true);
		setViewport(new ScreenViewport(camera));
		shape = new ShapeRenderer();
		Gdx.graphics.setWindowedMode(400, 700);
	}

	public void init() {
		model = new TetrisModel();
		view = new View(this);

		controller = new Controller(model, view);

		model.addListener(controller);

		Timer.schedule(new Timer.Task() {
			@Override
			public void run() {
				controller.slideDown();
			}
		}, 1.0f, 1.0f);

		Gdx.input.setInputProcessor(this);

		addListener(new InputListener() {

			@Override
			public boolean keyDown(InputEvent event, int keycode) {
				switch (keycode) {
					case Input.Keys.LEFT:
						controller.moveLeft();
						break;
					case Input.Keys.RIGHT:
						controller.moveRight();
						break;
					case Input.Keys.SPACE:
						controller.drop();
						break;
					case Input.Keys.UP:
						controller.rotate();
						break;
					default:
						break;
				}
				return true;
			}

		});

	}

	@Override
	public void draw() {
		view.draw(model);
	}

	@Override
	public void drawBoxAt(int x, int y, int size, int colorIndex) {
		shape.begin(ShapeType.Filled);
		shape.setColor(COLORS[colorIndex]);
		shape.rect(x, y, size, size);
		shape.end();
	}

	@Override
	public void drawBoxAt(int i, int j, int value) {
		int size = 30; // Example block size
		drawBoxAt(j, i, size, value);
	}

	@Override
	public void fillRect(int x, int y, int width, int height) {
		shape.begin(ShapeType.Filled);
		shape.rect(x, y, width, height);
		shape.end();
	}

	@Override
	public void drawString(String s, int x, int y) {
		getBatch().begin();
		font.draw(getBatch(), s, x, y);
		getBatch().end();
	}

	@Override
	public void setColor(java.awt.Color awtColor) {
		float r = awtColor.getRed() / 255f;
		float g = awtColor.getGreen() / 255f;
		float b = awtColor.getBlue() / 255f;
		float a = awtColor.getAlpha() / 255f;
		shape.setColor(new Color(r, g, b, a));
	}

	@Override
	public void setFont(Font awtFont) {
		// In LibGDX, we can't use java.awt.Font directly, so we need to load a LibGDX font

		// Set it to a default LibGDX font (assuming the font name is "arial.fnt")
		font.dispose();  // Dispose of the previous font to avoid memory leaks.

		// Example: Load a new font (you need the .fnt file for this)
		font = new BitmapFont(Gdx.files.internal("arial.fnt")); // Replace "arial.fnt" with the actual path of your font file
	}
}