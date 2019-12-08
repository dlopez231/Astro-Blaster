package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.screens.MenuScreen;
import com.mygdx.game.screens.ScreenManager;

//  Game uses ApplicationAdapter, used for when an app is
//	created and rendering
public class AstroBlaster extends ApplicationAdapter {

	// 800 x 480 is the most used screen size for landscape most
	public static final int WIDTH = 800;
	public static final int HEIGHT = 480;

	// Title of the game
	public static final String TITLE = "Astro Blaster";

	// ScreenManager will help manage screens
	private ScreenManager sm;

	// Helps draw the sprites for us
	private SpriteBatch batch;

	@Override
	public void create () {

		// Initialize variables
		sm = new ScreenManager();
		batch = new SpriteBatch();

		// Set default color of app is black
		Gdx.gl.glClearColor(0, 0, 0, 0);

		// First screen the app will display
		sm.pushScreen(new MenuScreen(sm));
	}

	@Override
	public void render () {

		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// Update constantly runs
		sm.update(Gdx.graphics.getDeltaTime());

		// Render whats want to be drawn
		sm.render(batch);


	}

}
