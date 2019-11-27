package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.screen.GameScreen;
import com.mygdx.game.screen.MenuScreen;
import com.mygdx.game.screen.ScreenManager;

public class MainGame extends ApplicationAdapter {

	//Landscape screen
	public static int WIDTH = 800, HEIGHT = 480;

	private SpriteBatch batch;

	
	@Override
	public void create () {

		batch = new SpriteBatch();

		ScreenManager.setScreen(new MenuScreen());
	}

	@Override
	public void render () {


		if (ScreenManager.getCurrentScreen() != null){
			ScreenManager.getCurrentScreen().update();
		}

		if (ScreenManager.getCurrentScreen() != null){
			ScreenManager.getCurrentScreen().render(batch);
		}

	}

	@Override
	public void dispose () {

		if (ScreenManager.getCurrentScreen() != null) {
			ScreenManager.getCurrentScreen().dispose();
			batch.dispose();
		}
		batch.dispose();
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
