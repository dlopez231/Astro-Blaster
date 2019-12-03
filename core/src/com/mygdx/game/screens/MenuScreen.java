package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.AstroBlaster;

public class MenuScreen extends Screens{

    // Textures for the menu screen
    private Texture title;
    private Texture background;
    private Texture playButton;
//    private Texture tutorialButton;
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
        title = new Texture("Title.png");
        playButton = new Texture("PlayButton.png");
//        tutorialButton = new Texture("tutorialButton.png");
        scoreButton = new Texture("ScoreButton.png");
        quitButton = new Texture("QuitButton.png");

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
            Rectangle playButtonBounds = new Rectangle(AstroBlaster.WIDTH / 2 - playButton.getWidth() / 2, 200, playButton.getWidth(), playButton.getHeight());
//            Rectangle tutorialButtonBounds = new Rectangle(AstroBlaster.WIDTH / 2 - tutorialButton.getWidth() / 2, 200, tutorialButton.getWidth(), tutorialButton.getHeight());
            Rectangle scoreButtonBounds = new Rectangle(AstroBlaster.WIDTH / 2 - scoreButton.getWidth() / 2, 150, scoreButton.getWidth(), scoreButton.getHeight());
            Rectangle quitButtonBounds = new Rectangle(AstroBlaster.WIDTH / 2 - quitButton.getWidth() / 2, 100, quitButton.getWidth(), quitButton.getHeight());

            // Screens will change is user's touch is in specific bounds
            if (playButtonBounds.contains(touchPos.x, touchPos.y)) {
                sm.setScreen(new GameScreen(sm));
            }

//            if(tutorialButtonBounds.contains(touchPos.x, touchPos.y)) {
//                System.out.println("Tutorial is touched");
//                sm.setScreen(new TutorialScreen(sm));
//            }

            if(scoreButtonBounds.contains(touchPos.x, touchPos.y)) {
                sm.setScreen(new ScoreScreen(sm));
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

        sb.setProjectionMatrix(camera.combined);

        // Render menu textures
        sb.begin();

        sb.draw(background, 0, 0, AstroBlaster.WIDTH, AstroBlaster.HEIGHT);
        sb.draw(title, (AstroBlaster.WIDTH / 2) - (title.getWidth() / 2), 380);
        sb.draw(playButton, (AstroBlaster.WIDTH / 2) - (playButton.getWidth() / 2), 200);
        sb.draw(scoreButton, (AstroBlaster.WIDTH / 2) - (scoreButton.getWidth() / 2), 150);
        sb.draw(quitButton, (AstroBlaster.WIDTH / 2) - (quitButton.getWidth() / 2), 100);

        sb.end();

    }



    @Override
    public void dispose() {

        // Dispose all textures
        background.dispose();
        title.dispose();
        playButton.dispose();
        scoreButton.dispose();
        quitButton.dispose();
        menuTheme.dispose();

    }
}
