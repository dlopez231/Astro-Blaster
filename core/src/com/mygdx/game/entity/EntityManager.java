package com.mygdx.game.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Assets;
import com.mygdx.game.MainGame;
import com.mygdx.game.camera.OrthoCamera;
import com.mygdx.game.screen.GameOverScreen;
import com.mygdx.game.screen.ScreenManager;

//This class handles what all the entities are doing, aka ship, bullets, enemies
public class EntityManager {

    //We will store them in an array (mainly the enemies and bullets)
    private final Array<SpriteEntity> entities = new Array<SpriteEntity>();

    //The ship will be its own thing
    private final Ship ship;

    //Amount is how many enemies will appear
    public EntityManager(int amount, OrthoCamera camera){

        //This is where the ship will spawn
        ship = new Ship(new Vector2(20, 240), new Vector2(0, 0), this, camera);

        //Enemies randomly appear from the right side
        for (int i = 0; i < amount; i++){

            //Second parameter is for how many enemies do you want to spawn at once
            float x = MathUtils.random(MainGame.WIDTH, MainGame.WIDTH * 2);
            float y = MathUtils.random(0, MainGame.HEIGHT - Assets.ENEMY1.getHeight());

            //This determines the speed of enemies
            float speed = MathUtils.random(2, 5);
            addEntity(new Enemy1(new Vector2(x, y), new Vector2(-speed, 0)));
        }
    }

    public void update() {

        //Update the enemies and bullets
        for (SpriteEntity se : entities) {
            se.update();
        }

        //Will get rid of bullets when it reaches end of screen
        for (Bullet b : getBullets()){
            if (b.checkEnd()) {
                entities.removeValue(b, false);
            }
        }

        //Ship will update
        ship.update();

        //Collisions will be constantly checked
        checkCollisions();
    }

    public void render(SpriteBatch sb){

        //Render enemies and bullets
        for (SpriteEntity se : entities){
            se.render(sb);
        }

        //Render ship
        ship.render(sb);
    }

    //Hitbox
    private void checkCollisions(){

        //This will check if the bounds of the enemies and bullet sprites overlap each other
        for (Enemy1 e : getEnemies1()){
            for (Bullet b : getBullets()){
                if(e.getBounds().overlaps(b.getBounds())){

                    //This will remove them from their perspective arrays
                    entities.removeValue(e, false);
                    entities.removeValue(b, false);

                    //checks to see if there's no more enemies
                    if(gameOver()){
                        //Screen will change to win
                        ScreenManager.setScreen(new GameOverScreen(true));

                    }
                }
            }

            //If ship gets hit by the enemy, game over
            if (e.getBounds().overlaps(ship.getBounds())){
                ScreenManager.setScreen(new GameOverScreen(false));
            }
        }
    }

    //Method to add entities to arrays
    public void addEntity(SpriteEntity entity){
        entities.add(entity);

    }

    private Array<Enemy1> getEnemies1(){
        Array<Enemy1> ret = new Array<Enemy1>();

        for(SpriteEntity se : entities){

            //Will check for an instance of an enemy, if it returns true, will be returned
            if (se instanceof Enemy1){
                ret.add((Enemy1)se);
            }
        }

        return ret;
    }

    //Works the same way as previous method
    private Array<Bullet> getBullets(){
        Array<Bullet> ret = new Array<Bullet>();

        for(SpriteEntity se : entities){

            if (se instanceof Bullet){
                ret.add((Bullet)se);
            }
        }

        return ret;
    }

    //Checks for to see if there's no more enemies
    public boolean gameOver(){
        return getEnemies1().size <= 0;
    }

}
