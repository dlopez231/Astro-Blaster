package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class TextureManager {

    public static Texture SHIP = new Texture(Gdx.files.internal("playership.png"));
    public static Texture BULLET = new Texture(Gdx.files.internal("bullet.png"));
    public static Texture ENEMY1 = new Texture(Gdx.files.internal("enemy1.png"));
    public static Texture GAME_LOST = new Texture(Gdx.files.internal("gameover.png"));
    public static Texture GAME_WON = new Texture(Gdx.files.internal("gamewon.png"));


}
