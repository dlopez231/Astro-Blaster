package com.mygdx.game.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.MainGame;
import com.mygdx.game.TextureManager;

public class Enemy1 extends SpriteEntity {

    public Enemy1(Vector2 position, Vector2 direction) {
        super(TextureManager.ENEMY1, position, direction);
    }

    @Override
    public void update() {

        position.add(direction);

        if(position.y <= -TextureManager.ENEMY1.getHeight()){
            float x = MathUtils.random(0, MainGame.WIDTH - TextureManager.ENEMY1.getWidth());
            position.set(x, MainGame.HEIGHT);
        }

    }
}
