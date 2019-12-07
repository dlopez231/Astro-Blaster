package com.mygdx.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.AstroBlaster;

public class Bullet {

    private Vector2 position;

    private Vector2 direction;

    private Texture texture;

    private Rectangle hitbox;

    public Bullet(float x, float y){

        position = new Vector2(x, y);

        // Speed of bullet
        direction = new Vector2(+10, 0);

        texture = new Texture("bullet.png");

        hitbox = new Rectangle(position.x, position.y, texture.getWidth(), texture.getHeight());

    }

    public void update(){
        position.add(direction);

        hitbox.setPosition(position.x, position.y);

    }

    public void render(SpriteBatch sb){

        sb.draw(texture, position.x, position.y);
    }

    public Texture getTexture(){
        return texture;

    }

    public Vector2 getPosition(){
        return position;
    }

    public boolean collides(Rectangle enemies){


        return enemies.overlaps(hitbox);
    }

    public boolean checkEnd(){
        return position.x >= AstroBlaster.WIDTH;

    }

    public void dispose(){
        texture.dispose();
    }




}
