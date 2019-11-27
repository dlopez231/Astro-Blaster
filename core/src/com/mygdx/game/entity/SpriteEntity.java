package com.mygdx.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

// This class is what all sprites share
public abstract class SpriteEntity {

    //The image of each sprite
    protected Texture texture;

    //Position of where they spawn
    protected Vector2 position;

    //Where the sprite is going
    protected Vector2 direction;

    //Constructor
    public SpriteEntity(Texture texture, Vector2 position, Vector2 direction){
        this.texture = texture;
        this.position = position;
        this.direction = direction;
    }

    public abstract void update();

    //Drawing the sprites when they're rendered
    public void render(SpriteBatch sb){
        sb.draw(texture, position.x, position.y);

    }

    //This helps with hitbox
    public Rectangle getBounds(){
        return new Rectangle(position.x, position.y, texture.getWidth(), texture.getHeight());
    }

    //Position getter
    public Vector2 getPosition(){
        return position;
    }

    //Setter for direction
    public void setDirection(float x, float y){

        direction.set(x, y);

        //Helps with making ship move around the same fps for all devices
        direction.scl(Gdx.graphics.getDeltaTime());

    }

}
