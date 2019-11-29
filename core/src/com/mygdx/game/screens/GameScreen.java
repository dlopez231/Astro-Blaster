package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.AstroBlaster;
import com.mygdx.game.sprites.Bullet;
import com.mygdx.game.sprites.Enemy1;
import com.mygdx.game.sprites.Ship;

public class GameScreen extends Screens{

    private static final int enemies1_count = 10;
    private long lastFire;

    private Texture background;

    private Ship ship;

    private String shipHealth;
    private int currentHealth;
    private BitmapFont healthBM;

    private String score;
    private int currentScore;
    private BitmapFont scoreBM;

    private Array<Enemy1> enemies1;
    private Array<Bullet> bullets;

    private int bgX = 0;


    public GameScreen(ScreenManager sm) {
        super(sm);

        camera.setToOrtho(false, AstroBlaster.WIDTH, AstroBlaster.HEIGHT);

        currentHealth = 3;
        shipHealth = "Ship Health: " + currentHealth;
        healthBM = new BitmapFont();

        currentScore = 0;
        score = "Score: " + currentScore;
        scoreBM = new BitmapFont();

        ship = new Ship(20, 230);
        background = new Texture("background2.png");

        enemies1 = new Array<Enemy1>();
        bullets = new Array<Bullet>();

        for (int i = 0; i < enemies1_count; i++){

            enemies1.add(new Enemy1());
        }


    }

    @Override
    protected void handleInput() {

        if(Gdx.input.isTouched()){

            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            if(touchPos.y > AstroBlaster.HEIGHT/2){
                ship.setDirection(0,(touchPos.y - ship.getTexture().getHeight() / 2) - AstroBlaster.HEIGHT/2);
            }
            else{
                ship.setDirection(0, -(AstroBlaster.HEIGHT/2 - (touchPos.y - ship.getTexture().getHeight() / 2)));
            }
        }

    }

    @Override
    public void update(float delta) {

        handleInput();

        background.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);

        bgX += 1;

        for(Enemy1 e1 : enemies1) {
            e1.update();

            if(e1.collides(ship.getBounds())){
                currentHealth--;
                shipHealth = "Ship Health: " + currentHealth;
                enemies1.removeValue(e1, false);
            }
        }

        if(currentHealth == 0){
            sm.setScreen(new GameOverScreen(sm, false));
        }

        if(System.currentTimeMillis() - lastFire >= 400){
            bullets.add(new Bullet(ship.getPosition().x + ship.getTexture().getWidth(), ship.getPosition().y + ship.getTexture().getHeight()/2));
            lastFire = System.currentTimeMillis();
        }

        for(Bullet b : bullets){
            b.update();
            if(b.checkEnd()){
                bullets.removeValue(b, false);

            }
        }

        for(Enemy1 e1 : enemies1){
            for(Bullet b : bullets){
                if(b.collides(e1.getBounds())){
                    enemies1.removeValue(e1, false);
                    bullets.removeValue(b, false);
                    currentScore += 300;
                    score = "Score: " + currentScore;

                }
            }
        }

        if(gameOver()){

            sm.setScreen(new GameOverScreen(sm, true));
        }

        ship.update(delta);
    }

    @Override
    public void render(SpriteBatch sb) {

        sb.setProjectionMatrix(camera.combined);

        sb.begin();

        sb.draw(background, 0, 0, bgX, 0, AstroBlaster.WIDTH, AstroBlaster.HEIGHT);

        sb.draw(ship.getTexture(), ship.getPosition().x, ship.getPosition().y);

        for(Enemy1 e1 : enemies1) {
            sb.draw(e1.getTexture(), e1.getPosition().x, e1.getPosition().y);
        }

        for(Bullet b : bullets) {
            sb.draw(b.getTexture(), b.getPosition().x, b.getPosition().y);
        }

        healthBM.setColor(1, 1, 1, 1);
        healthBM.draw(sb, shipHealth, 10, 470);

        scoreBM.setColor(1, 1, 1, 1);
        scoreBM.draw(sb, score, 700, 470);

        sb.end();

    }

    private Array<Enemy1> getEnemies1(){
        Array<Enemy1> ret = new Array<Enemy1>();

        for(Enemy1 e1 : enemies1){

            if(e1 instanceof Enemy1){
                ret.add((Enemy1)e1);
            }
        }

        return ret;
    }

    public boolean gameOver(){
        return getEnemies1().size <= 0;

    }

    @Override
    public void dispose() {

        background.dispose();
        ship.dispose();

        for(Enemy1 e1 : enemies1){
            e1.dispose();

        }

        for(Bullet b : bullets){
            b.dispose();

        }

        System.out.println("Game Screen disposed");

    }
}
