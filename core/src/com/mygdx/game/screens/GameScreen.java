package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntArray;
import com.mygdx.game.AstroBlaster;
import com.mygdx.game.sprites.Bullet;
import com.mygdx.game.sprites.Enemy;
import com.mygdx.game.sprites.Ship;

import java.util.ArrayList;

public class GameScreen extends Screens{

    private long lastFire;

    private Texture background;

    private Ship ship;

    private Texture pauseButton;

    private Texture pauseBG;
    private Texture resumeButton;
    private Texture homeButton;
    private Texture quitButton;

    private String shipHealth;
    private int currentHealth;
    private BitmapFont healthBM;

    private String score;
    private int currentScore;
    private BitmapFont scoreBM;

    private Array<Enemy> enemies;

    private Array<Bullet> bullets;

    private Sound laser;
    private Sound enemyDies;
    private Sound shipDies;

    private int bgX = 0;

    private float elapsed;
    private float spawnTime;

    public enum State{
        PAUSE,
        RUN
    }

    State state = State.RUN;

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

        pauseButton = new Texture("pause.png");

        enemies = new Array<Enemy>();
        bullets = new Array<Bullet>();

        pauseBG = new Texture("transparentBG.png");
        resumeButton = new Texture("resume.png");
        homeButton = new Texture("home.png");
        quitButton = new Texture("QuitButton.png");

        laser = Gdx.audio.newSound(Gdx.files.internal("laser.ogg"));
        enemyDies = Gdx.audio.newSound(Gdx.files.internal("enemydies.ogg"));
        shipDies = Gdx.audio.newSound(Gdx.files.internal("playerdies.ogg"));


    }

    @Override
    protected void handleInput() {

        if(Gdx.input.isTouched()){

            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);

            Rectangle pauseButtonBounds = new Rectangle(AstroBlaster.WIDTH - pauseButton.getWidth() + 10,10, pauseButton.getWidth(), pauseButton.getHeight());
            Rectangle shipTouchBounds = new Rectangle(0, 0, AstroBlaster.WIDTH/2, AstroBlaster.HEIGHT);

            if(pauseButtonBounds.contains(touchPos.x, touchPos.y)){
                pause();
            }

            if(shipTouchBounds.contains(touchPos.x, touchPos.y)) {
                if (touchPos.y > (ship.getPosition().y + ship.getTexture().getHeight() / 2)) {
                    ship.setDirection(0, touchPos.y - (ship.getPosition().y + (ship.getTexture().getHeight() / 2)));
                    fire();
                } else {
                    ship.setDirection(0, -((ship.getPosition().y + (ship.getTexture().getHeight() / 2)) - touchPos.y));
                    fire();
                }
            }


            if(this.state == State.PAUSE){
                Rectangle resumeButtonBounds = new Rectangle((AstroBlaster.WIDTH/2) - (resumeButton.getWidth()/2), 200, resumeButton.getWidth(), resumeButton.getHeight());
                Rectangle homeButtonBounds = new Rectangle((AstroBlaster.WIDTH/2) - (homeButton.getWidth()/2), 150, homeButton.getWidth(), homeButton.getHeight());
                Rectangle quitButtonBounds = new Rectangle((AstroBlaster.WIDTH/2) - (quitButton.getWidth()/2), 100, quitButton.getWidth(), quitButton.getHeight());

                if(resumeButtonBounds.contains(touchPos.x, touchPos.y)){
                    resume();
                }

                if(homeButtonBounds.contains(touchPos.x, touchPos.y)){
                    sm.setScreen(new MenuScreen(sm));
                }

                if(quitButtonBounds.contains(touchPos.x, touchPos.y)){
                    dispose();
                    Gdx.app.exit();
                }

            }

            if(!Gdx.input.isTouched()){
                ship.setDirection(0,0);
            }
        }
    }


    @Override
    public void update(float delta) {

        handleInput();

        switch (state){

            case RUN:

                background.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);

                bgX += 1;

                spawnTime += 1*Gdx.graphics.getDeltaTime();

                while(spawnTime >= 1f && getEnemies().size <= 10) {

                    enemies.add(new Enemy());
                    spawnTime -= 1f;
                }

                for(Enemy e1 : enemies) {
                    e1.update();

                    if(currentHealth > 0 && e1.collides(ship.getBounds())){
                        currentHealth--;
                        shipHealth = "Ship Health: " + currentHealth;

                        e1.subtractHP(3);

                        if(e1.getHealth() <= 0) {
                            enemies.removeValue(e1, false);
                            enemyDies.play(0.04f);
                        }
                    }
                }

                if(currentHealth <= 0){
                    //sound is causing weird bug
//                    shipDies.play(0.3f);

//                    int highScore = myPrefs.getInteger("HighScore", 0);
//                    if(highScore < currentScore){
//                        myPrefs.putInteger("LastScore", currentScore);
//                        myPrefs.flush();
//                    }
                    elapsed += delta;
                    if(elapsed > 2.0) {

                        sm.setScreen(new GameOverScreen(sm, currentScore));
                    }
                }


                for(Bullet b : bullets){
                    b.update();
                    if(b.checkEnd()){
                        bullets.removeValue(b, false);

                    }
                }

                for(Enemy e1 : enemies){
                    for(Bullet b : bullets){

                        if(b.collides(e1.getBounds())){
                            e1.subtractHP(1);
                            bullets.removeValue(b, false);

                            if(e1.getHealth() <= 0) {
                                enemies.removeValue(e1, false);
                                enemyDies.play(0.04f);
                                currentScore += 300;
                                score = "Score: " + currentScore;
                            }
                        }
                    }
                }
                ship.update(delta);
                break;

            case PAUSE:
                break;

        }
    }

    @Override
    public void render(SpriteBatch sb) {

        sb.setProjectionMatrix(camera.combined);

        sb.begin();

        sb.draw(background, 0, 0, bgX, 0, AstroBlaster.WIDTH, AstroBlaster.HEIGHT);

        for(Enemy e : enemies) {
            sb.draw(e.getTexture(), e.getPosition().x, e.getPosition().y);
        }

        sb.draw(ship.getTexture(), ship.getPosition().x, ship.getPosition().y);

        for(Bullet b : bullets) {
            sb.draw(b.getTexture(), b.getPosition().x, b.getPosition().y);
        }

        sb.draw(pauseButton, AstroBlaster.WIDTH - pauseButton.getWidth() - 10,10);

        healthBM.setColor(1, 1, 1, 1);
        healthBM.draw(sb, shipHealth, 10, 470);

        scoreBM.setColor(1, 1, 1, 1);
        scoreBM.draw(sb, score, 700, 470);

        if(this.state == state.PAUSE){

            sb.draw(pauseBG, 0, 0, AstroBlaster.WIDTH, AstroBlaster.HEIGHT);
            sb.draw(resumeButton, (AstroBlaster.WIDTH/2) - (resumeButton.getWidth()/2), 200);
            sb.draw(homeButton, (AstroBlaster.WIDTH/2) - (homeButton.getWidth()/2), 150);
            sb.draw(quitButton, (AstroBlaster.WIDTH/2) - (quitButton.getWidth()/2), 100);

        }
        sb.end();

    }

    private Array<Enemy> getEnemies(){
        Array<Enemy> ret = new Array<Enemy>();

        for(Enemy e : enemies){

            if(e instanceof Enemy){
                ret.add((Enemy)e);
            }
        }

        return ret;
    }

    public void fire(){

        if(System.currentTimeMillis() - lastFire >= 300 && currentHealth > 0){
            bullets.add(new Bullet(ship.getPosition().x + ship.getTexture().getWidth(), ship.getPosition().y + ship.getTexture().getHeight()/2));
            laser.play(0.1f);
            lastFire = System.currentTimeMillis();

        }
    }



    public void pause() {
        this.state = State.PAUSE;

    }

    public void resume(){

        if(this.state == State.PAUSE){
            this.state = State.RUN;

        }
    }

    @Override
    public void dispose() {

        background.dispose();
        ship.dispose();

        for(Enemy e : enemies){
            e.dispose();

        }

        for(Bullet b : bullets){
            b.dispose();

        }
        pauseBG.dispose();
        resumeButton.dispose();
        homeButton.dispose();
        quitButton.dispose();

        laser.dispose();
        enemyDies.dispose();
        shipDies.dispose();


    }

}
