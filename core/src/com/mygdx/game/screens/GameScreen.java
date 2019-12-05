package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
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

    // Variable to help with slowing down ship bullets
    private long lastFire;

    private Music battleTheme;

    // Game screen textures
    private Texture background;
    private Texture pauseButton;
    private Texture pauseBG;
    private Texture resumeButton;
    private Texture homeButton;
    private Texture quitButton;

    // For displaying ship health
    private String shipHealth;
//    private int currentHealth;
    private BitmapFont healthBM;

    // For displaying score
    private String score;
    private int currentScore;
    private BitmapFont scoreBM;

    // Player ship
    private Ship ship;

    // Enemies are stored in an array
    private Array<Enemy> enemies;

    // Bullets are stored in an array
    private Array<Bullet> bullets;

    // Sounds for gameplay
    private Sound laser;
    private Sound enemyHit;
    private Sound enemyDies;
    private Sound shipDies;

    // Variable for moving background
    private int bgX = 0;

    // Float for adding delay before screen changes to game over
    private float elapsed;

    // Float for slowin down enemies spawning
    private float spawnTime;

    // Enum for state of the game
    public enum State{
        PAUSE,
        RUN
    }

    // Initially set state to RUN
    State state = State.RUN;

    public GameScreen(ScreenManager sm) {
        super(sm);

        camera.setToOrtho(false, AstroBlaster.WIDTH, AstroBlaster.HEIGHT);

//        battleTheme = Gdx.audio.newMusic(Gdx.files.internal("battleTheme.mp3"));
//        battleTheme.setLooping(true);
//        battleTheme.setVolume(0.4f);
//        battleTheme.play();

        // Initialize textures to use for game screen
        background = new Texture("background2.png");
        pauseButton = new Texture("pause.png");
        pauseBG = new Texture("transparentBG.png");
        resumeButton = new Texture("resume.png");
        homeButton = new Texture("home.png");
        quitButton = new Texture("Quit Button.png");



        // Player score
        currentScore = 0;
        score = "Score: " + currentScore;
        scoreBM = new BitmapFont();

        // Spawn ship in specific coordinates
        ship = new Ship(20, 230);

        shipHealth = "Ship Health: " + ship.getHealth();
        healthBM = new BitmapFont();

        // Initialize arrays
        enemies = new Array<Enemy>();
        bullets = new Array<Bullet>();

        // Initialize sounds
        laser = Gdx.audio.newSound(Gdx.files.internal("laser.ogg"));
        enemyHit = Gdx.audio.newSound(Gdx.files.internal("enemyhit.ogg"));
        enemyDies = Gdx.audio.newSound(Gdx.files.internal("enemydies.ogg"));
        shipDies = Gdx.audio.newSound(Gdx.files.internal("playerdies.ogg"));


    }

    @Override
    protected void handleInput() {

        if(Gdx.input.isTouched()){

            // Touch positions
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);

            camera.unproject(touchPos);

            // Set up bounds for pause button
            Rectangle pauseButtonBounds = new Rectangle(AstroBlaster.WIDTH - pauseButton.getWidth() - 10,10, pauseButton.getWidth(), pauseButton.getHeight());

            // Touch bounds for ship movement
            Rectangle shipTouchBounds = new Rectangle(0, 0, AstroBlaster.WIDTH/2, AstroBlaster.HEIGHT);

            // Pause if pause button is touched
            if(pauseButtonBounds.contains(touchPos.x, touchPos.y)){
                pause();
            }

            // Move ship if touch position is in left side of screen
//            if(shipTouchBounds.contains(touchPos.x, touchPos.y)) {
//
//                // Ship will go up if touch position is above ship texture position
//                if (touchPos.y > (ship.getPosition().y + ship.getTexture().getHeight() / 2)) {
//
//                    ship.setDirection(0, touchPos.y - (ship.getPosition().y + (ship.getTexture().getHeight() / 2)));
//                    // Ship will fire at the same time
//                    fire();
//
//                }
//
//                // Ship will go down is touch position is below ship texture position
//                else {
//                    ship.setDirection(0, -((ship.getPosition().y + (ship.getTexture().getHeight() / 2)) - touchPos.y));
//                    fire();
//                }
//            }

            if(shipTouchBounds.contains(touchPos.x, touchPos.y)) {

                // Ship will go up if touch position is above ship texture position
                if (touchPos.y > (ship.getPosition().y + 61 / 2)) {

                    ship.setDirection(0, touchPos.y - (ship.getPosition().y + (61 / 2)));
                    // Ship will fire at the same time
                    fire();

                }

                // Ship will go down is touch position is below ship texture position
                else {
                    ship.setDirection(0, -((ship.getPosition().y + (61 / 2)) - touchPos.y));
                    fire();
                }
            }


            // If the state is paused, set up pause menu button bounds
            if(this.state == State.PAUSE){

                Rectangle resumeButtonBounds = new Rectangle((AstroBlaster.WIDTH/2) - (resumeButton.getWidth()/2), 200, resumeButton.getWidth(), resumeButton.getHeight());
                Rectangle homeButtonBounds = new Rectangle((AstroBlaster.WIDTH/2) - (homeButton.getWidth()/2), 150, homeButton.getWidth(), homeButton.getHeight());
                Rectangle quitButtonBounds = new Rectangle((AstroBlaster.WIDTH/2) - (quitButton.getWidth()/2), 100, quitButton.getWidth(), quitButton.getHeight());

                // Game will either resume, go back to menu, or quit
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

            // Stop moving ship is touch position is in right side
            if(!Gdx.input.isTouched()){
                ship.setDirection(0,0);
            }
        }
    }


    @Override
    public void update(float delta) {

        // Keep handling input
        handleInput();

        switch (state){

            // Update game in RUN state
            case RUN:

                // Background image will loop
                background.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);

                // Speed of background loop
                bgX += 1;

                // Get spawn time
                spawnTime += 1*Gdx.graphics.getDeltaTime();

                // Spawn enemy when 1 sec has passed and while enemy array is
                // lower than or equal to 10
                while(spawnTime >= 1f && getEnemies().size <= 10) {

                    // Add enemies to array
                    enemies.add(new Enemy());

                    // Reset spawn time
                    spawnTime -= 1f;
                }

                // Update each enemy
                for(Enemy e : enemies) {
                    e.update();

                    // Subtract health for ship when it still has more than 0
                    // health and collides with enemy bounds
                    if(ship.getHealth() > 0 && e.collides(ship.getBounds())){
                        ship.subtractHP();
                        shipHealth = "Ship Health: " + ship.getHealth();
                        if(ship.getHealth() <= 0) {

                            shipDies.play(0.2f);
//                            battleTheme.dispose();
                        }

                        // Enemy just dies
                        e.subtractHP(3);

                        // Remove enemy if it has 0 health and play death sound
                        if(e.getHealth() <= 0) {
                            enemies.removeValue(e, false);
                            enemyDies.play(0.04f);
                        }
                    }
                }

                // If ship health is 0, game over
                if(ship.getHealth() <= 0){

                    // Wait for 2 seconds before screen changes to game over
                    elapsed += delta;
                    if(elapsed > 2.0) {

                        // Change to game over screen
                        sm.setScreen(new GameOverScreen(sm, currentScore));
                    }
                }

                // Update each bullet in array
                for(Bullet b : bullets){
                    b.update();

                    // Remove bullet when it reaches end of screen
                    if(b.checkEnd()){
                        bullets.removeValue(b, false);

                    }
                }

                for(Enemy e : enemies){
                    for(Bullet b : bullets){

                        // Subtract 1 health from each enemy if it collides with
                        // bullet bounds
                        if(b.collides(e.getBounds())){

                            e.subtractHP(1);

                            if(e.getHealth() > 0) {
                                enemyHit.play(0.03f);
                            }

                            // Remove bullet from array
                            bullets.removeValue(b, false);

                            // Remove ship from array and play death sound when
                            // it has 0 health
                            if(e.getHealth() <= 0) {
                                enemies.removeValue(e, false);
                                enemyDies.play(0.04f);

                                // Add 300 to score when enemy is killed with
                                // bullets
                                currentScore += 300;
                                score = "Score: " + currentScore;
                            }
                        }
                    }
                }

                // Update ship
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

        // Draw game screen textures
        sb.draw(background, 0, 0, bgX, 0, AstroBlaster.WIDTH, AstroBlaster.HEIGHT);

        for(Enemy e : enemies) {
            sb.draw(e.getTexture(), e.getPosition().x, e.getPosition().y);
        }

//        sb.draw(ship.getTexture(), ship.getPosition().x, ship.getPosition().y);

        ship.render(sb);

        for(Bullet b : bullets) {
            sb.draw(b.getTexture(), b.getPosition().x, b.getPosition().y);
        }

        sb.draw(pauseButton, AstroBlaster.WIDTH - pauseButton.getWidth() - 10,10);

        healthBM.setColor(1, 1, 1, 1);
        healthBM.draw(sb, shipHealth, 10, 470);

        scoreBM.setColor(1, 1, 1, 1);
        scoreBM.draw(sb, score, 700, 470);

        // Pause menu background and buttons will only render when state is PAUSE
        if(this.state == state.PAUSE){

            sb.draw(pauseBG, 0, 0, AstroBlaster.WIDTH, AstroBlaster.HEIGHT);
            sb.draw(resumeButton, (AstroBlaster.WIDTH/2) - (resumeButton.getWidth()/2), 200);
            sb.draw(homeButton, (AstroBlaster.WIDTH/2) - (homeButton.getWidth()/2), 150);
            sb.draw(quitButton, (AstroBlaster.WIDTH/2) - (quitButton.getWidth()/2), 100);

        }
        sb.end();

    }

    // Number of enemies getter
    private Array<Enemy> getEnemies(){
        Array<Enemy> ret = new Array<Enemy>();

        for(Enemy e : enemies){

            if(e instanceof Enemy){
                ret.add((Enemy)e);
            }
        }

        return ret;
    }

    // Function when ship fires
    public void fire(){

        // Slow down adding bullets and play sound
        if(System.currentTimeMillis() - lastFire >= 300 && ship.getHealth() > 0){
            bullets.add(new Bullet(ship.getPosition().x + 75, ship.getPosition().y + 61/2));
            laser.play(0.1f);
            lastFire = System.currentTimeMillis();

        }
    }

    // Pause function for PAUSE state
    public void pause() {
        this.state = State.PAUSE;

    }

    // Resume function to resume game
    public void resume(){

        if(this.state == State.PAUSE){
            this.state = State.RUN;

        }
    }

    // Dispose all textures
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
        enemyHit.dispose();
        enemyDies.dispose();
        shipDies.dispose();

        healthBM.dispose();
        scoreBM.dispose();

//        battleTheme.dispose();

    }

}
