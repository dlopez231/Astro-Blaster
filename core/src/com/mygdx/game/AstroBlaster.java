package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.screens.MenuScreen;
import com.mygdx.game.screens.ScreenManager;

public class AstroBlaster extends ApplicationAdapter {

	public static final int WIDTH = 800;
	public static final int HEIGHT = 480;

	public static final String TITLE = "Astro Blaster";


	private ScreenManager sm;
	private SpriteBatch batch;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		sm = new ScreenManager();

		Gdx.gl.glClearColor(0, 0, 0, 0);


		sm.pushScreen(new MenuScreen(sm));
	}

	@Override
	public void render () {

		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		sm.update(Gdx.graphics.getDeltaTime());
		sm.render(batch);


	}

}
