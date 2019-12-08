package com.mygdx.game.sprites;

import com.badlogic.gdx.utils.Pool;

/*  This is to help with avoiding the stuttering problem when playing the app in android device
    The game will stutter every time there is a new enemy, so to fix it a bit we will use the
    pooling method to re-use enemies instead of creating a new one every second
 */

public class EnemyPool extends Pool<Enemy> {

    // Init is how many enemy objects are created and max is the cap the pool can hold
    public EnemyPool(int init, int max){
        super(init, max);

    }

    @Override
    protected Enemy newObject() {
        return new Enemy();
    }
}


