package com.mygdx.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.AstroBlaster;

public class Ship{

    private Vector2 position;

    private Vector2 direction;

    private Texture ship;

    private Rectangle ship_hitbox;


    public Ship(int x, int y){

        position = new Vector2(x, y);
        direction = new Vector2(0, 0);
        ship = new Texture("playership.png");

        ship_hitbox = new Rectangle(x, y, ship.getWidth(), ship.getHeight());

    }

    public void update(float delta){


        position.add(0, direction.y);


        if (position.y < 0){
            position.y = 0;
        }
        else if(position.y > AstroBlaster.HEIGHT - ship.getHeight()){
            position.y = AstroBlaster.HEIGHT - ship.getHeight();
        }

        ship_hitbox.setPosition(position.x, position.y);

    }


    public Vector2 getPosition() {
        return position;
    }

    public Texture getTexture() {
        return ship;
    }


    public void setDirection(float x, float y){
        direction.set(0, y/20);

    }


    public Rectangle getBounds(){
        return ship_hitbox;

    }

    public void dispose(){
        ship.dispose();
    }
}
