package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.AstroBlaster;

public class GameOverScreen extends Screens{

    private Texture background;
    private Texture gameStatus;
    private Texture replayButton;
    private Texture homeButton;
    private Texture quitButton;

    public GameOverScreen(ScreenManager sm, boolean won)    {
        super(sm);

        camera.setToOrtho(false, AstroBlaster.WIDTH, AstroBlaster.HEIGHT);

        background = new Texture("background2.png");

        if(won){
            gameStatus = new Texture("win.png");
        }

        else{
            gameStatus = new Texture("lost.png");
        }

        replayButton = new Texture("replay.png");
        homeButton = new Texture("home.png");
        quitButton = new Texture("QuitButton.png");

    }

    @Override
    protected void handleInput() {

        if(Gdx.input.justTouched()){

            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);

            Rectangle replayButtonBounds = new Rectangle(AstroBlaster.WIDTH / 2 - replayButton.getWidth() / 2, 200, replayButton.getWidth(), replayButton.getHeight());
            Rectangle homeButtonBounds = new Rectangle(AstroBlaster.WIDTH / 2 - homeButton.getWidth() / 2, 150, homeButton.getWidth(), homeButton.getHeight());
            Rectangle quitButtonBounds = new Rectangle(AstroBlaster.WIDTH / 2 - quitButton.getWidth() / 2, 100, quitButton.getWidth(), quitButton.getHeight());

            if (replayButtonBounds.contains(touchPos.x, touchPos.y)){
                sm.setScreen(new GameScreen(sm));

            }

            if(homeButtonBounds.contains(touchPos.x, touchPos.y)){
                sm.setScreen(new MenuScreen(sm));

            }

            if(quitButtonBounds.contains(touchPos.x, touchPos.y)){
                dispose();
                Gdx.app.exit();
            }

        }

    }

    @Override
    public void update(float delta) {

        camera.update();
        handleInput();

    }

    @Override
    public void render(SpriteBatch sb) {

        sb.setProjectionMatrix(camera.combined);

        sb.begin();
        sb.draw(background, 0, 0, AstroBlaster.WIDTH, AstroBlaster.HEIGHT);
        sb.draw(gameStatus, (AstroBlaster.WIDTH/2) - (gameStatus.getWidth() / 2), 360);
        sb.draw(replayButton, (AstroBlaster.WIDTH/2) - (replayButton.getWidth()/2), 200);
        sb.draw(homeButton, (AstroBlaster.WIDTH/2) - (homeButton.getWidth()/2), 150);
        sb.draw(quitButton, (AstroBlaster.WIDTH/2) - (quitButton.getWidth()/2), 100);
        sb.end();


    }

    @Override
    public void dispose() {

        background.dispose();
        gameStatus.dispose();
        replayButton.dispose();
        homeButton.dispose();
        quitButton.dispose();

    }
}
