package com.mygdx.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.AstroBlaster;


public class Enemy {

    private Vector2 position;

    private Vector2 direction;

//    private Texture enemy;

    private Rectangle enemyHitbox;

    private int health;

    private Animation<TextureRegion> enemy;

    private Animation<TextureRegion>enemyDies;

    private float elapsed;

    public Enemy(){
//        enemy = new Texture("enemy.png");

        enemy = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("enemyTest.gif").read());
        enemyDies = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("enemyTest2.gif").read());

        // Random enemy spawn coordinates
        // Enemies spawn in edge of screen times 1/3rd of screen width size
        float x = MathUtils.random(AstroBlaster.WIDTH + 75, AstroBlaster.WIDTH + AstroBlaster.WIDTH * 0.3f);

        // Enemies spawn in randomm height of screen
        float y = MathUtils.random(0, AstroBlaster.HEIGHT - 75);

        // Random speed of enemy
        float speed = MathUtils.random(3, 6);

        // Set up position
        position = new Vector2(x, y);

        // Negative speed to move to left
        direction = new Vector2(-speed, 0);

        // Update bounds along with enemy texture coordinates
        enemyHitbox = new Rectangle(position.x, position.y, 75, 75);

        // Enemies have 3 health
        health = 3;

    }

    public void update(float delta){

        position.add(direction);

        // Enemies that reach end of screen in left side respawn in right
        if(position.x <= -75){
            float y = MathUtils.random(0, AstroBlaster.HEIGHT - 75);
            position.set(AstroBlaster.WIDTH, y);
        }

        if(health > 0) {
            enemyHitbox.setPosition(position.x, position.y);
        }

    }


//    public Texture getTexture(){
//        return enemy;
//    }

    public void render(SpriteBatch sb){

        elapsed += Gdx.graphics.getDeltaTime();

        if(health > 0){
            sb.draw(enemy.getKeyFrame(elapsed), position.x, position.y);
        }

        else{
            sb.draw(enemyDies.getKeyFrame(elapsed), position.x, position.y);
            direction.x = 0;

        }

    }

    public Vector2 getPosition(){
        return position;

    }

    public boolean collides(Rectangle ship){


        return ship.overlaps(enemyHitbox);

    }

    public Rectangle getBounds(){
        return enemyHitbox;
    }


    public void dispose(){

        Object[] enemyFrames = enemy.getKeyFrames();

        for(int i = 0; i < enemyFrames.length; i++){
            Texture tmp = ((TextureRegion) enemyFrames[i]).getTexture();
            tmp.dispose();
        }

        Object[] enemyFrames2 = enemyDies.getKeyFrames();

        for(int i = 0; i < enemyFrames2.length; i++){
            Texture tmp = ((TextureRegion) enemyFrames2[i]).getTexture();
            tmp.dispose();

        }

    }

    public void subtractHP(int hp){
        health -= hp;

    }

    public int getHealth(){
        return health;

    }



}
