package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
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

    private Texture pauseButton;

    private Texture pauseBG;
    private Texture resumeButton;

    private String shipHealth;
    private int currentHealth;
    private BitmapFont healthBM;

    private String score;
    private int currentScore;
    private BitmapFont scoreBM;

    private Array<Enemy1> enemies1;
    private Array<Bullet> bullets;


    private int bgX = 0;


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

        enemies1 = new Array<Enemy1>();
        bullets = new Array<Bullet>();

        pauseBG = new Texture("transparentBG.png");
        resumeButton = new Texture("resume.png");

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

            Rectangle pauseButtonBounds = new Rectangle(AstroBlaster.WIDTH - pauseButton.getWidth() - 10, 5, pauseButton.getWidth(), pauseButton.getHeight());


            if(pauseButtonBounds.contains(touchPos.x, touchPos.y)){
                System.out.println("Pause is touched");
                pause();

            }

            if(touchPos.y > AstroBlaster.HEIGHT/2){
                ship.setDirection(0,(touchPos.y - ship.getTexture().getHeight() / 2) - AstroBlaster.HEIGHT/2);
            }
            else{
                ship.setDirection(0, -(AstroBlaster.HEIGHT/2 - (touchPos.y - ship.getTexture().getHeight() / 2)));
            }

            if(this.state == State.PAUSE){
                Rectangle resumeButtonBounds = new Rectangle((AstroBlaster.WIDTH/2) - (resumeButton.getWidth()/2), (AstroBlaster.HEIGHT/2) - (resumeButton.getHeight()/2), resumeButton.getWidth(), resumeButton.getHeight());

                if(resumeButtonBounds.contains(touchPos.x, touchPos.y)){
                    System.out.println("Resume is touched");
                    resume();
                }

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

        sb.draw(ship.getTexture(), ship.getPosition().x, ship.getPosition().y);

        for(Enemy1 e1 : enemies1) {
            sb.draw(e1.getTexture(), e1.getPosition().x, e1.getPosition().y);
        }

        for(Bullet b : bullets) {
            sb.draw(b.getTexture(), b.getPosition().x, b.getPosition().y);
        }

        sb.draw(pauseButton, AstroBlaster.WIDTH - pauseButton.getWidth() - 10, 5);

        healthBM.setColor(1, 1, 1, 1);
        healthBM.draw(sb, shipHealth, 10, 470);

        scoreBM.setColor(1, 1, 1, 1);
        scoreBM.draw(sb, score, 700, 470);

        if(this.state == state.PAUSE){

            sb.draw(pauseBG, 0, 0, AstroBlaster.WIDTH, AstroBlaster.HEIGHT);
            sb.draw(resumeButton, (AstroBlaster.WIDTH/2) - (resumeButton.getWidth()/2), (AstroBlaster.HEIGHT/2) - (resumeButton.getHeight()/2));

        }

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

        for(Enemy1 e1 : enemies1){
            e1.dispose();

        }

        for(Bullet b : bullets){
            b.dispose();

        }

        pauseBG.dispose();
        resumeButton.dispose();

        System.out.println("Game Screen disposed");

    }

}
