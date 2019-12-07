package com.mygdx.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.AstroBlaster;

public class Bullet {

    private Vector2 position;

    private Vector2 direction;

    private Texture bullet;

    private Rectangle bullet_hitbox;


    public Bullet(float x, float y){

        position = new Vector2(x, y);

        // Speed of bullet
        direction = new Vector2(+7, 0);

        bullet = new Texture("bullet.png");

        bullet_hitbox = new Rectangle(position.x, position.y, bullet.getWidth(), bullet.getHeight());

    }

    public void update(float delta){
        position.add(direction);

        bullet_hitbox.setPosition(position.x, position.y);

    }

    public Texture getTexture(){
        return bullet;

    }

    public Vector2 getPosition(){
        return position;
    }

    public boolean collides(Rectangle enemies){



        return enemies.overlaps(bullet_hitbox);
    }

    public boolean checkEnd(){
        return position.x >= AstroBlaster.WIDTH;

    }

    public void dispose(){
        bullet.dispose();
    }

    public Rectangle getBounds(){
        return bullet_hitbox;
    }



}
