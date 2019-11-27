package com.mygdx.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Assets;
import com.mygdx.game.MainGame;
import com.mygdx.game.camera.OrthoCamera;

public class Ship extends SpriteEntity{

    private final EntityManager entityManager;
    private final OrthoCamera camera;
    private long lastFire;

    //Constructor
    public Ship(Vector2 position, Vector2 direction, EntityManager entityManager, OrthoCamera camera) {

        super(Assets.SHIP, position, direction);
        this.entityManager = entityManager;
        this.camera = camera;
    }

    @Override
    public void update() {

        position.add(direction);

        //Checks touch input
        int direction = 0;
        if(Gdx.input.isTouched()){
                Vector2 touch = camera.unprojectCoordinates(Gdx.input.getX(), Gdx.input.getY());

                //If touched at top part of screen, ship will go up
                if(touch.y > MainGame.HEIGHT / 2){
                    direction = 1;
                }

                //Else go down
                else{
                    direction = 2;
                }

        }

        if(direction == 1){
            setDirection(0, 300);
        }

        else if(direction == 2){
            setDirection(0, -300);
        }

        //Will stop the ship from moving when there's no touch input
        else{
            setDirection(0,0);

        }


        //To slow down bullets
        if(System.currentTimeMillis() - lastFire >= 350) {
            entityManager.addEntity(new Bullet(position.cpy().add(Assets.SHIP.getHeight(),25)));
            lastFire = System.currentTimeMillis();
        }

    }
}
