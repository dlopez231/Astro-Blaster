package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.AstroBlaster;
import com.mygdx.game.sprites.GifDecoder;

// This is the main menu
// It can lead you to three other screens:
// the actual game, the leader board, a quick how to play screen
// Pressing the Quit button will exit the app
public class MenuScreen extends Screens{

    // Title is animated
    private Animation<TextureRegion> title;
    // The float will help with rendering the animation
    private float elapsed;

    // Textures for the menu screen
    private Texture background;
    private Texture startButton;
    private Texture helpButton;
    private Texture scoreButton;
    private Texture quitButton;

    // Music for menu theme
    private Music menuTheme;

    public MenuScreen(ScreenManager sm){

        super(sm);

        // Will set up what the player will see in screen, we just want the camera to set the game
        // width and height
        camera.setToOrtho(false, AstroBlaster.WIDTH, AstroBlaster.HEIGHT);

        // GifDecoder is a class that helps with reading GIF files and set up the animation for us
        title = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("Title.gif").read());

        // Textures are initialized
        background = new Texture("background.png");
        startButton = new Texture("Start Button.png");
        helpButton = new Texture("Help Button.png");
        scoreButton = new Texture("Scores Button.png");
        quitButton = new Texture("Quit Button.png");

        // Menu song is initialized
        menuTheme = Gdx.audio.newMusic(Gdx.files.internal("menuTheme.mp3"));

        // Menu song is looped, volume is set, and will play when screen is created
        menuTheme.setLooping(true);
        menuTheme.setVolume(0.4f);
        menuTheme.play();

    }

    @Override
    public void handleInput() {

        // Will look for just touched events
        if (Gdx.input.justTouched()) {

            // Touch positions
            input.set(Gdx.input.getX(), Gdx.input.getY(), 0);

            // For touch coordinates to match screen size
            camera.unproject(input);

            /* Rectangle has four parameters:
               The first parameter is where the x coordinate is in the screen, the second is the
               y coordinate, the third is how long the width is, and the fourth is how tall the
               height is.
            */

            // Bounds for where each button is
            Rectangle startButtonBounds = new Rectangle(300 - (startButton.getWidth() / 2), 130, startButton.getWidth(), startButton.getHeight());
            Rectangle helpButtonBounds = new Rectangle(300 - (helpButton.getWidth() / 2), 30, helpButton.getWidth(), helpButton.getHeight());
            Rectangle scoreButtonBounds = new Rectangle(500 - (scoreButton.getWidth() / 2), 130, scoreButton.getWidth(), scoreButton.getHeight());
            Rectangle quitButtonBounds = new Rectangle(500 - (quitButton.getWidth() / 2), 30, quitButton.getWidth(), quitButton.getHeight());

            /* Screens will change if user's touch is in specific bounds and the button sound will play.
               The Help and Score buttons will push the screens, that is because we want the music from
               the Menu screen to keep playing, not disposed.
               The Start button will dispose the menu screen and push the game screen. That is because
               the Game screen has it's own assets
            */

            // Start button will lead to the game
            if (startButtonBounds.contains(input.x, input.y)) {

                buttonSound.play(0.5f);
                sm.setScreen(new GameScreen(sm));

            }

            // Help will lead to a how to play screen
            if(helpButtonBounds.contains(input.x, input.y)) {

                buttonSound.play(0.5f);
                sm.pushScreen(new HelpScreen(sm));

            }

            // Score will lead to the leader board
            if(scoreButtonBounds.contains(input.x, input.y)) {

                buttonSound.play(0.5f);
                sm.pushScreen(new ScoreScreen(sm));

            }

            // Quit make the app exit
            if(quitButtonBounds.contains(input.x, input.y)) {

                buttonSound.play(0.5f);
                dispose();
                disposeSound();
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

        // This helps with the title animation
        elapsed += Gdx.graphics.getDeltaTime();

        // Helps with camera set up
        sb.setProjectionMatrix(camera.combined);

        /*  For rendering, always start with sb.begin(), draw everything you want to appear on screen,
            and then end with sb.end(). It's like opening a box, put what you want there, and
            then closing it

            draw() can have 3 or 5 parameters:
                3 for just the texture, x coordinate, and y coordinate (the coordinates are where you
                want them to appear in the screen)
                5 for the above three, setting the width, and setting the height
         */

        // Render menu textures
        sb.begin();

        sb.draw(background, 0, 0, AstroBlaster.WIDTH, AstroBlaster.HEIGHT);

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

        // Animation is a bit complex, must get all the frames into an object array then dispose
        // each frame texture
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
