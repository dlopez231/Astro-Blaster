package com.mygdx.game.screens;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.Stack;

// Screen helper
// Screens will work like a stack
public class ScreenManager {

    private Stack<Screens> screens;

    public ScreenManager(){
        screens = new Stack<Screens>();

    }

    public void pushScreen(Screens screen){
        screens.push(screen);

    }

    public void popScreen(){
        screens.pop().dispose();

    }

    public void setScreen(Screens screen){
        screens.pop().dispose();
        screens.push(screen);

    }

    public void update(float delta){
        screens.peek().update(delta);

    }

    public void render(SpriteBatch sb){
        screens.peek().render(sb);

    }

}
