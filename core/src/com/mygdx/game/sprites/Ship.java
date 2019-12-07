package com.mygdx.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.AstroBlaster;

public class Ship{

    private Vector2 position;

    private Vector2 direction;

    private int health;

    private Animation<TextureRegion> animation;

    private Animation<TextureRegion> deathAnimation;

    private float elapsed;

    private Rectangle hitbox;

    public Ship(int x, int y){

        position = new Vector2(x, y);
        direction = new Vector2(0, 0);

        animation = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("ship.gif").read());
        deathAnimation = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("shipTest2.gif").read());

        hitbox = new Rectangle(x, y, 122, 70);

        health = 3;

    }

    public void update(){

        // Move ship based on direction
        position.add(0, direction.y);

        // Stop ship movement if it's touching screen bounds
        if (position.y < 0){
            position.y = 0;
        }

        else if(position.y > AstroBlaster.HEIGHT - 61){
            position.y = AstroBlaster.HEIGHT - 61;
        }

        // Update ship bounds along with ship texture position
        hitbox.setPosition(position.x, position.y);

    }

    public void render(SpriteBatch sb){

        elapsed += Gdx.graphics.getDeltaTime();

        if(health > 0){
            sb.draw(animation.getKeyFrame(elapsed), position.x, position.y);
        }
        else{
            sb.draw(deathAnimation.getKeyFrame(elapsed), position.x, position.y);
        }
    }


    public Vector2 getPosition() {
        return position;
    }

    public void setDirection(float x, float y){

        // Ship direction is slowed down
        direction.set(0, y/10);

    }


    public Rectangle getBounds(){
        return hitbox;

    }

    public void subtractHP(){

        health -= 1;

    }

    public int getHealth(){
        return health;

    }

    public void dispose(){

        Object[] shipFrames = animation.getKeyFrames();

        for(int i = 0; i < shipFrames.length; i++){
            Texture tmp = ((TextureRegion) shipFrames[i]).getTexture();
            tmp.dispose();

        }

        Object[] shipDiesFrames = deathAnimation.getKeyFrames();

        for(int i = 0; i < shipDiesFrames.length; i++){
            Texture tmp = ((TextureRegion) shipDiesFrames[i]).getTexture();
            tmp.dispose();

        }
    }
}
