package com.mygdx.game.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MainGame;
import com.mygdx.game.TextureManager;
import com.mygdx.game.camera.OrthoCamera;
import com.mygdx.game.screen.GameOverScreen;
import com.mygdx.game.screen.ScreenManager;


public class EntityManager {

    private final Array<SpriteEntity> entities = new Array<SpriteEntity>();
    private final Ship ship;

    public EntityManager(int amount, OrthoCamera camera){
        ship = new Ship(new Vector2(230, 15), new Vector2(0, 0), this, camera);

        //Enemies appear from top, will figure out how to do this landscape later
        for (int i = 0; i < amount; i++){
            float x = MathUtils.random(0, MainGame.WIDTH - TextureManager.ENEMY1.getWidth());
            float y = MathUtils.random(MainGame.HEIGHT, MainGame.HEIGHT * 3);
            float speed = MathUtils.random(2, 5);
            addEntity(new Enemy1(new Vector2(x, y), new Vector2(0, -speed)));
        }
    }

    public void update() {
        for (SpriteEntity se : entities) {
            se.update();
        }

        for (Bullet b : getBullets()){
            if (b.checkEnd()) {
                entities.removeValue(b, false);
            }
        }

        ship.update();
        checkCollisions();
    }

    public void render(SpriteBatch sb){
        for (SpriteEntity se : entities){
            se.render(sb);
        }
        ship.render(sb);
    }

    private void checkCollisions(){
        for (Enemy1 e : getEnemies1()){
            for (Bullet b : getBullets()){
                if(e.getBounds().overlaps(b.getBounds())){
                    entities.removeValue(e, false);
                    entities.removeValue(b, false);
                    if(gameOver()){
                        //end game, won
                        ScreenManager.setScreen(new GameOverScreen(true));

                    }
                }
            }
            if (e.getBounds().overlaps(ship.getBounds())){
                //end game
                ScreenManager.setScreen(new GameOverScreen(false));
            }
        }
    }

    public void addEntity(SpriteEntity entity){
        entities.add(entity);

    }

    private Array<Enemy1> getEnemies1(){
        Array<Enemy1> ret = new Array<Enemy1>();

        for(SpriteEntity se : entities){

            if (se instanceof Enemy1){
                ret.add((Enemy1)se);
            }
        }

        return ret;
    }

    private Array<Bullet> getBullets(){
        Array<Bullet> ret = new Array<Bullet>();

        for(SpriteEntity se : entities){

            if (se instanceof Bullet){
                ret.add((Bullet)se);
            }
        }

        return ret;
    }

    public boolean gameOver(){
        return getEnemies1().size <= 0;
    }

}
