package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.screen.GameScreen;
import com.mygdx.game.screen.ScreenManager;

public class MainGame extends ApplicationAdapter {

	//Portrait screen
	public static int WIDTH = 480, HEIGHT = 800;

	private SpriteBatch batch;

	
	@Override
	public void create () {

		batch = new SpriteBatch();

		ScreenManager.setScreen(new GameScreen());
	}

	@Override
	public void render () {

		//Set screen to white
		Gdx.gl.glClearColor(1, 1, 1, 1);
		//Helps with buffer
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		if (ScreenManager.getCurrentScreen() != null){
			ScreenManager.getCurrentScreen().update();
		}

		if (ScreenManager.getCurrentScreen() != null){
			ScreenManager.getCurrentScreen().render(batch);
		}

	}

	//Called when app close
	@Override
	public void dispose () {

		if (ScreenManager.getCurrentScreen() != null) {
			ScreenManager.getCurrentScreen().dispose();
			batch.dispose();
		}
	}

	@Override
	public void resize (int width, int height) {

		if (ScreenManager.getCurrentScreen() != null){
			ScreenManager.getCurrentScreen().resize(width, height);
		}

	}

	@Override
	public void pause(){

		if (ScreenManager.getCurrentScreen() != null){
			ScreenManager.getCurrentScreen().pause();
		}

	}

	@Override
	public void resume(){

		if (ScreenManager.getCurrentScreen() != null){
			ScreenManager.getCurrentScreen().resume();
		}

	}
}
