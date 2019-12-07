package com.mygdx.game.sprites;

import com.badlogic.gdx.utils.Pool;

public class EnemyPool extends Pool<Enemy> {

    public EnemyPool(int init, int max){
        super(init, max);

    }

    @Override
    protected Enemy newObject() {
        return new Enemy();
    }
}


