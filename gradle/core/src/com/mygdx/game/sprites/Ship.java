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

    private Animation<TextureRegion> ship;

    private Animation<TextureRegion> shipDies;

    private float elapsed;

    private Rectangle ship_hitbox;

    public Ship(int x, int y){

        position = new Vector2(x, y);
        direction = new Vector2(0, 0);
//        ship = new Texture("playership.png");

        ship = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("shipTest.gif").read());
        shipDies = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("shipTest2.gif").read());

//        ship_hitbox = new Rectangle(x, y, ship.getWidth(), ship.getHeight());

        ship_hitbox = new Rectangle(x, y, 75, 61);

        health = 3;

    }

    public void update(float delta){

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
        ship_hitbox.setPosition(position.x, position.y);

    }

    public void render(SpriteBatch sb){

        elapsed += Gdx.graphics.getDeltaTime();

        if(health > 0){
            sb.draw(ship.getKeyFrame(elapsed), position.x, position.y);
        }
        else{
            sb.draw(shipDies.getKeyFrame(elapsed), position.x, position.y);
        }

    }


    public Vector2 getPosition() {
        return position;
    }

    public void setDirection(float x, float y){

        // Ship direction is slowed down
        direction.set(0, y/20);

    }


    public Rectangle getBounds(){
        return ship_hitbox;

    }

    public void subtractHP(){

        health -= 1;

    }

    public int getHealth(){
        return health;

    }

    public void dispose(){

        Object[] shipFrames = ship.getKeyFrames();

        for(int i = 0; i < shipFrames.length; i++){
            Texture tmp = ((TextureRegion) shipFrames[i]).getTexture();
            tmp.dispose();

        }

        Object[] shipDiesFrames = shipDies.getKeyFrames();

        for(int i = 0; i < shipDiesFrames.length; i++){
            Texture tmp = ((TextureRegion) shipDiesFrames[i]).getTexture();
            tmp.dispose();

        }

    }



}
