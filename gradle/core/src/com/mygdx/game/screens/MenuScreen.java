package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureArray;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.AstroBlaster;
import com.mygdx.game.sprites.GifDecoder;


public class MenuScreen extends Screens{

    // Textures for the menu screen
//    private Texture title;

    private Animation<TextureRegion> title;
    private float elapsed;


    private Texture background;
    private Texture startButton;
    private Texture helpButton;
    private Texture scoreButton;
    private Texture quitButton;

    // Music for meny theme
    private Music menuTheme;

    public MenuScreen(ScreenManager sm){
        super(sm);

        // Will set up game window/view to size of the screen
        camera.setToOrtho(false, AstroBlaster.WIDTH, AstroBlaster.HEIGHT);

        // Textures are initialized
        background = new Texture("background2.png");
//        title = new Texture("Title.png");

        title = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("Title.gif").read());

        startButton = new Texture("Start Button.png");
        helpButton = new Texture("Help Button.png");
        scoreButton = new Texture("Scores Button.png");
        quitButton = new Texture("Quit Button.png");

        // Menu song is initialized
        menuTheme = Gdx.audio.newMusic(Gdx.files.internal("menuTheme.mp3"));

        // Menu song is looped
        menuTheme.setLooping(true);
        menuTheme.setVolume(0.4f);
        menuTheme.play();

    }

    @Override
    public void handleInput() {

        // Will look for just touched events
        if (Gdx.input.justTouched()) {

            // Touch positions
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);

            // For accurate touch
            camera.unproject(touchPos);

            // Bounds for where each button is
            Rectangle playButtonBounds = new Rectangle(300 - (startButton.getWidth() / 2), 130, startButton.getWidth(), startButton.getHeight());
            Rectangle helpButtonBounds = new Rectangle(300 - (helpButton.getWidth() / 2), 30, helpButton.getWidth(), helpButton.getHeight());
            Rectangle scoreButtonBounds = new Rectangle(500 - (scoreButton.getWidth() / 2), 130, scoreButton.getWidth(), scoreButton.getHeight());
            Rectangle quitButtonBounds = new Rectangle(500 - (quitButton.getWidth() / 2), 30, quitButton.getWidth(), quitButton.getHeight());

            // Screens will change is user's touch is in specific bounds
            if (playButtonBounds.contains(touchPos.x, touchPos.y)) {
                sm.setScreen(new GameScreen(sm));
            }

            if(helpButtonBounds.contains(touchPos.x, touchPos.y)) {
                sm.pushScreen(new HelpScreen(sm));
            }

            if(scoreButtonBounds.contains(touchPos.x, touchPos.y)) {
                sm.pushScreen(new ScoreScreen(sm));
            }

            if(quitButtonBounds.contains(touchPos.x, touchPos.y)) {
                dispose();
                Gdx.app.exit();
            }
        }
    }

    @Override
    public void update(float delta) {
        camera.update();

        handleInput();

    }

    @Override
    public void render(SpriteBatch sb) {

        elapsed += Gdx.graphics.getDeltaTime();

        sb.setProjectionMatrix(camera.combined);

        // Render menu textures
        sb.begin();

        sb.draw(background, 0, 0, AstroBlaster.WIDTH, AstroBlaster.HEIGHT);
//        sb.draw(title, (AstroBlaster.WIDTH / 2) - (title.getWidth() / 2), 380);

        sb.draw(title.getKeyFrame(elapsed), (AstroBlaster.WIDTH/2) - 195, 230);

        sb.draw(startButton, 280 - (startButton.getWidth() / 2), 130);
        sb.draw(scoreButton, 520 - (scoreButton.getWidth() / 2), 130);
        sb.draw(helpButton, 280 - (helpButton.getWidth() / 2), 30);
        sb.draw(quitButton, 520 - (quitButton.getWidth() / 2), 30);

        sb.end();

    }



    @Override
    public void dispose() {

        // Dispose all textures
        background.dispose();
//        title.dispose();

        Object[] titleFrames = title.getKeyFrames();

        for(int i = 0; i < titleFrames.length; i++){
            Texture tmp = ((TextureRegion) titleFrames[i]).getTexture();
            tmp.dispose();
        }

        startButton.dispose();
        helpButton.dispose();
        scoreButton.dispose();
        quitButton.dispose();
        menuTheme.dispose();


    }
}
