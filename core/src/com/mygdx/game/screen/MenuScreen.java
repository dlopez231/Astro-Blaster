package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.MainGame;
import com.mygdx.game.camera.OrthoCamera;

public class MenuScreen extends Screen {

    private OrthoCamera camera;

    Texture title;
    Texture playButton;
//  Texture tutorialButton;
    Texture scoreboardButton;
    Texture exitButton;

    //For testing purposes
    private String test;
    BitmapFont testBM;

    @Override
    public void create() {

        camera = new OrthoCamera();

        test = "Nothing is touched";
        testBM = new BitmapFont();

        title = new Texture("Title.png");
        playButton = new Texture("PlayButton.png");
//      tutorialButton = new Texture("TutorialButton.png");
        scoreboardButton = new Texture("ScoreButton.png");
        exitButton = new Texture("QuitButton.png");

    }

    @Override
    public void update() {

        camera.update();

    }

    @Override
    public void render(SpriteBatch sb) {

        Gdx.gl.glClearColor(0, 0, 1,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        sb.setProjectionMatrix(camera.combined);
        sb.begin();

        testBM.setColor(1, 1, 1, 1);
        testBM.draw(sb, test, 10, 470);

        //Still need to figure out how to set to middle
        //This game uses a different camera
        sb.draw(title, 240, 380);
        sb.draw(playButton,340, 200);
        sb.draw(scoreboardButton, 340, 150);
        sb.draw(exitButton, 340, 100);

        if(Gdx.input.isTouched()){
            Vector3 tmp = new Vector3(Gdx.input.getX(), Gdx.input.getY(),0);
            camera.unproject(tmp);
            Rectangle playButtonBounds = new Rectangle(340, 200, playButton.getWidth(), playButton.getHeight());
            Rectangle scoreButtonBounds = new Rectangle(340, 150, scoreboardButton.getWidth(), scoreboardButton.getHeight());
            Rectangle exitButtonBounds = new Rectangle(340, 100, exitButton.getWidth(), exitButton.getHeight());

            if(playButtonBounds.contains(tmp.x, tmp.y)){
                ScreenManager.getCurrentScreen().dispose();
                ScreenManager.setScreen(new GameScreen());

            }

            if(scoreButtonBounds.contains(tmp.x, tmp.y)){
                test = "No scoreboard atm";
            }

            if(exitButtonBounds.contains(tmp.x, tmp.y)){
                ScreenManager.getCurrentScreen().dispose();
                Gdx.app.exit();
            }

        }

        sb.end();

    }

    @Override
    public void resize(int width, int height) {

        camera.resize();

    }

    @Override
    public void dispose() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }
}
