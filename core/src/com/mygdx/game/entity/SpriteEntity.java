package com.mygdx.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class SpriteEntity {

    protected Texture texture;
    protected Vector2 position;
    protected Vector2 direction;

    public SpriteEntity(Texture texture, Vector2 position, Vector2 direction){
        this.texture = texture;
        this.position = position;
        this.direction = direction;
    }

    public abstract void update();

    public void render(SpriteBatch sb){
        sb.draw(texture, position.x, position.y);

    }

    public Rectangle getBounds(){
        return new Rectangle(position.x, position.y, texture.getWidth(), texture.getHeight());
    }

    public Vector2 getPosition(){
        return position;
    }

    public void setDirection(float x, float y){

        direction.set(x, y);

        //Helps with making ship move around the same fps for all devices
        direction.scl(Gdx.graphics.getDeltaTime());

    }

}
