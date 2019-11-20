package com.mygdx.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.MainGame;
import com.mygdx.game.TextureManager;
import com.mygdx.game.camera.OrthoCamera;

public class Ship extends SpriteEntity{

    private final EntityManager entityManager;
    private final OrthoCamera camera;
    private long lastFire;

    public Ship(Vector2 position, Vector2 direction, EntityManager entityManager, OrthoCamera camera) {
        super(TextureManager.SHIP, position, direction);
        this.entityManager = entityManager;
        this.camera = camera;
    }

    @Override
    public void update() {

        position.add(direction);

        int direction = 0;
        if(Gdx.input.isTouched()){
                Vector2 touch = camera.unprojectCoordinates(Gdx.input.getX(), Gdx.input.getY());
                if(touch.y > MainGame.HEIGHT / 2){
                    direction = 2;
                }
                else{
                    direction = 1;
                }

        }

        //To test with desktop
        if(Gdx.input.isKeyPressed(Input.Keys.UP) || direction == 1){
            setDirection(0, -300);
        }

        else if(Gdx.input.isKeyPressed(Input.Keys.DOWN) || direction == 2){
            setDirection(0, 300);
        }

        else{
            setDirection(0,0);

        }

        //if(Gdx.input.isKeyPressed(Input.Keys.SPACE)){
            if(System.currentTimeMillis() - lastFire >= 350) {
                entityManager.addEntity(new Bullet(position.cpy().add(TextureManager.SHIP.getHeight(),25)));
                lastFire = System.currentTimeMillis();
            }

        //}


    }
}
