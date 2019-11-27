package com.mygdx.game.entity;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Assets;
import com.mygdx.game.MainGame;

public class Enemy1 extends SpriteEntity {

    public Enemy1(Vector2 position, Vector2 direction) {
        super(Assets.ENEMY1, position, direction);
    }

    @Override
    public void update() {

        position.add(direction);

        //This checks when enemies goes past the ship, it will respawn in the right side randomly
        if(position.x <= - Assets.ENEMY1.getWidth()){
            float y = MathUtils.random(0, MainGame.HEIGHT - Assets.ENEMY1.getHeight());
            position.set(MainGame.WIDTH, y);
        }

    }
}
