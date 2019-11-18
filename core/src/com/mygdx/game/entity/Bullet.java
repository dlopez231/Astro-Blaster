package com.mygdx.game.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.MainGame;
import com.mygdx.game.TextureManager;

public class Bullet extends SpriteEntity{

    public Bullet(Vector2 position){
        super(TextureManager.BULLET, position, new Vector2(0, 5));
    }

    @Override
    public void update(){
        position.add(direction);
    }

    //Check to see if bullet hit end of screen
    public boolean checkEnd(){
        return position.y >= MainGame.HEIGHT;
    }

}


