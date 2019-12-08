package com.mygdx.game.screens;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.Stack;

// Screen helper
// Screens will work like a stack
public class ScreenManager {

    // Create a stack to hold screens
    private Stack<Screens> screens;

    // Initialize
    public ScreenManager(){

        screens = new Stack<Screens>();

    }

    // Push screen into stack
    public void pushScreen(Screens screen){

        screens.push(screen);

    }

    // Pop and dispose screen on top
    public void popScreen(){

        screens.pop().dispose();

    }

    // Dispose current screen and replace it with a new one
    public void setScreen(Screens screen){

        screens.pop().dispose();
        screens.push(screen);

    }

    // Update the top most screen
    public void update(float delta){

        screens.peek().update(delta);

    }

    // Render the top most screen
    public void render(SpriteBatch sb){

        screens.peek().render(sb);

    }

}
