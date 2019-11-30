package com.mygdx.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.RandomXS128;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.AstroBlaster;


public class Enemy1 {

    private Vector2 position;

    private Vector2 direction;

    private Texture enemy1;

    private Rectangle enemy1_hitbox;

    public Enemy1(){
        enemy1 = new Texture("enemy1.png");

        float x = MathUtils.random(AstroBlaster.WIDTH, AstroBlaster.WIDTH * 5);
        float y = MathUtils.random(0, AstroBlaster.HEIGHT - enemy1.getHeight());

        float speed = MathUtils.random(2, 5);

        position = new Vector2(x, y);
        direction = new Vector2(-speed, 0);

        enemy1_hitbox = new Rectangle(position.x, position.y, enemy1.getWidth(), enemy1.getHeight());

    }

    public void update(){

        position.add(direction);


        if(position.x <= -enemy1.getWidth()){
            float y = MathUtils.random(0, AstroBlaster.HEIGHT - enemy1.getHeight());
            position.set(AstroBlaster.WIDTH, y);
        }

        enemy1_hitbox.setPosition(position.x, position.y);

    }

    public Texture getTexture(){
        return enemy1;
    }

    public Vector2 getPosition(){
        return position;

    }

    public boolean collides(Rectangle ship){
        return ship.overlaps(enemy1_hitbox);

    }

    public Rectangle getBounds(){
        return enemy1_hitbox;
    }

    public void dispose(){

        enemy1.dispose();

    }

}
