package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.AstroBlaster;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = AstroBlaster.WIDTH;
		config.height = AstroBlaster.HEIGHT;
		config.title = AstroBlaster.TITLE;
		new LwjglApplication(new AstroBlaster(), config);
	}
}
