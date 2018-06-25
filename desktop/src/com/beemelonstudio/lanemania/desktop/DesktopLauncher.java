package com.beemelonstudio.lanemania.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.beemelonstudio.lanemania.LaneMania;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 1080;//(int) (720f / 1.5f);
        config.height = 1920;//(int) (1280f / 1.5f);
        config.title = "LaneMania";

		new LwjglApplication(new LaneMania(), config);
	}
}
