package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.AstroBlaster;

public class HelpScreen extends Screens{

    private Texture helpScreen;
    private Texture homeButton;

    public HelpScreen(ScreenManager sm){
        super(sm);

        camera.setToOrtho(false, AstroBlaster.WIDTH, AstroBlaster.HEIGHT);

        helpScreen = new Texture("helpBG.png");
        homeButton = new Texture("home.png");


    }

    @Override
    protected void handleInput() {

        if(Gdx.input.justTouched()){

            input = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);

            camera.unproject(input);

            Rectangle homeButtonBounds = new Rectangle(740, 20, homeButton.getWidth(), homeButton.getHeight());

            if(homeButtonBounds.contains(input.x, input.y)){
                sm.popScreen();

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

        sb.draw(helpScreen, 0, 0, AstroBlaster.WIDTH, AstroBlaster.HEIGHT);
        sb.draw(homeButton, 740, 20);

        sb.end();

    }

    @Override
    public void dispose() {

        helpScreen.dispose();
        homeButton.dispose();

    }
}
