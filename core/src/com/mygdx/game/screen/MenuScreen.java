package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Assets;
import com.mygdx.game.MainGame;
import com.mygdx.game.camera.OrthoCamera;

public class MenuScreen extends Screen {

    private OrthoCamera camera;

    //Button textures
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

        //Will show this in the corner in the beginning
        test = "Nothing is touched";
        testBM = new BitmapFont();

        //Set up what pictures the buttons will use
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

        //Blue background, background color is different just so i can see that screens really changed and
        //Its only the sprites causing the problem if they don't appear
        Gdx.gl.glClearColor(0, 0, 1,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        sb.setProjectionMatrix(camera.combined);
        sb.begin();

        //Set up where the text will be drawn
        testBM.setColor(1, 1, 1, 1);
        testBM.draw(sb, test, 10, 470);

        //Buttons are drawn in the middle of the screen with different heights
        sb.draw(title, MainGame.WIDTH / 2 - title.getWidth() / 2, 380);
        sb.draw(playButton,MainGame.WIDTH / 2 - playButton.getWidth() / 2, 200);
        sb.draw(scoreboardButton, MainGame.WIDTH / 2 - scoreboardButton.getWidth() / 2, 150);
        sb.draw(exitButton, MainGame.WIDTH / 2 - exitButton.getWidth() / 2, 100);

        //Will check which button is touched
        if(Gdx.input.isTouched()){
            Vector3 tmp = new Vector3(Gdx.input.getX(), Gdx.input.getY(),0);
            camera.unproject(tmp);
            Rectangle playButtonBounds = new Rectangle(MainGame.WIDTH / 2 - playButton.getWidth() / 2, 200, playButton.getWidth(), playButton.getHeight());
            Rectangle scoreButtonBounds = new Rectangle(MainGame.WIDTH / 2 - scoreboardButton.getWidth() / 2, 150, scoreboardButton.getWidth(), scoreboardButton.getHeight());
            Rectangle exitButtonBounds = new Rectangle(MainGame.WIDTH / 2 - exitButton.getWidth() / 2, 100, exitButton.getWidth(), exitButton.getHeight());

            //Will change to main game when play is touched
            //but with sprites not appearing upon screen change is still not fixed
            if(playButtonBounds.contains(tmp.x, tmp.y)){
                ScreenManager.setScreen(new GameScreen());

            }

            //Scoreboard screen is still not set
            if(scoreButtonBounds.contains(tmp.x, tmp.y)){
                test = "No scoreboard atm";
            }

            //App will just exit, not completely closed
            if(exitButtonBounds.contains(tmp.x, tmp.y)){
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
