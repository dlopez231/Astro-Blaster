package com.mygdx.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import com.mygdx.game.AstroBlaster;


public class Enemy implements Pool.Poolable {

    private Vector2 position;

    private Vector2 direction;

    private Rectangle hitbox;

    private int health;

    private Animation<TextureRegion> animation;

    private Animation<TextureRegion> deathAnimation;

    private float elapsed;

    private float x;
    private float y;
    private float speed;


    public Enemy(){
//    public void init(){

        animation = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("enemy.gif").read());
        deathAnimation = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("enemyDeath.gif").read());

        // Random enemy spawn coordinates
        // Enemies spawn in edge of screen times 1/3rd of screen width size
        x = MathUtils.random(AstroBlaster.WIDTH + 75, AstroBlaster.WIDTH + AstroBlaster.WIDTH * 0.3f);

        // Enemies spawn in random height of screen
        y = MathUtils.random(0, AstroBlaster.HEIGHT - 75);

        // Random speed of enemy
        speed = MathUtils.random(3, 6);

        // Set up position
        position = new Vector2(x, y);

        // Negative speed to move to left
        direction = new Vector2(-speed, 0);

        // Update bounds along with enemy texture coordinates
        hitbox = new Rectangle(position.x + 18, position.y + 20, 53, 75);

        // Enemies have 3 health
        health = 3;

    }

    public void re_init(){

        x = MathUtils.random(AstroBlaster.WIDTH + 75, AstroBlaster.WIDTH + AstroBlaster.WIDTH * 0.3f);

        y = MathUtils.random(0, AstroBlaster.HEIGHT - 75);

        speed = MathUtils.random(3, 6);

        position.set(x, y);

        direction.set(-speed, 0);

        health = 3;

    }

    public void update(){

        position.add(direction);

        // Enemies that reach end of screen in left side respawn in right
        if(position.x <= -75){
            float y = MathUtils.random(0, AstroBlaster.HEIGHT - 75);
            position.set(AstroBlaster.WIDTH, y);
        }

        if(health > 0) {
            hitbox.setPosition(position.x + 18, position.y + 21);
        }
    }

    public void render(SpriteBatch sb){

        elapsed += Gdx.graphics.getDeltaTime();

        if(health > 0){
            sb.draw(animation.getKeyFrame(elapsed), position.x, position.y);
        }

        else{
            sb.draw(deathAnimation.getKeyFrame(elapsed), position.x, position.y);
            direction.x = 0;

        }
    }

    public boolean collides(Rectangle ship){


        return ship.overlaps(hitbox);

    }

    public Rectangle getBounds(){
        return hitbox;
    }


    public void dispose(){

        Object[] enemyFrames = animation.getKeyFrames();

        for(int i = 0; i < enemyFrames.length; i++){
            Texture tmp = ((TextureRegion) enemyFrames[i]).getTexture();
            tmp.dispose();
        }

        Object[] enemyFrames2 = deathAnimation.getKeyFrames();

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


    @Override
    public void reset() {

        //Put unused enemy sprites out of screen
        position.set(AstroBlaster.WIDTH + 75, 0);
        direction.set(0,0);

    }
}
