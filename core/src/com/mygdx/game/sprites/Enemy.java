package com.mygdx.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.AstroBlaster;


public class Enemy {

    private Vector2 position;

    private Vector2 direction;

    private Texture enemy;

    private Rectangle enemyHitbox;

    private int health;

    public Enemy(){
        enemy = new Texture("enemy.png");

        // Random enemy spawn coordinates
        // Enemies spawn in edge of screen times 1/3rd of screen width size
        float x = MathUtils.random(AstroBlaster.WIDTH + enemy.getWidth(), AstroBlaster.WIDTH + AstroBlaster.WIDTH * 0.3f);

        // Enemies spawn in randomm height of screen
        float y = MathUtils.random(0, AstroBlaster.HEIGHT - enemy.getHeight());

        // Random speed of enemy
        float speed = MathUtils.random(3, 6);

        // Set up position
        position = new Vector2(x, y);

        // Negative speed to move to left
        direction = new Vector2(-speed, 0);

        // Update bounds along with enemy texture coordinates
        enemyHitbox = new Rectangle(position.x, position.y, enemy.getWidth(), enemy.getHeight());

        // Enemies have 3 health
        health = 3;

    }

    public void update(){

        position.add(direction);

        // Enemies that reach end of screen in left side respawn in right
        if(position.x <= -enemy.getWidth()){
            float y = MathUtils.random(0, AstroBlaster.HEIGHT - enemy.getHeight());
            position.set(AstroBlaster.WIDTH, y);
        }

        enemyHitbox.setPosition(position.x, position.y);

    }


    public Texture getTexture(){
        return enemy;
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

        enemy.dispose();

    }

    public void subtractHP(int hp){
        health -= hp;

    }

    public int getHealth(){
        return health;

    }



}
