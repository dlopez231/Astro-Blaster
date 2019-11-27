package com.mygdx.game.screen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Assets;
import com.mygdx.game.MainGame;
import com.mygdx.game.camera.OrthoCamera;

public class GameOverScreen extends Screen{

    private OrthoCamera camera;
    private Texture texture;

    //Boolean to determine which screen to show
    public GameOverScreen(boolean win){
        if(win){
            texture = Assets.GAME_WON;
        }
        else{
            texture = Assets.GAME_LOST;
        }
    }

    @Override
    public void create() {
        camera = new OrthoCamera();
        camera.resize();

    }

    @Override
    public void update() {
        camera.update();

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(camera.combined);
        sb.begin();

        //Game over screens set to middle
        sb.draw(texture, MainGame.WIDTH / 2 - texture.getWidth() / 2, MainGame.HEIGHT / 2 - texture.getHeight() / 2);
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
