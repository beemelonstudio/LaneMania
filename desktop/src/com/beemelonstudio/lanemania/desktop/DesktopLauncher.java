package com.beemelonstudio.lanemania.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.beemelonstudio.lanemania.LaneMania;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = (int) (400f / 1f);
        config.height = (int) (711f / 1f);
        config.title = "LaneMania";

		new LwjglApplication(new LaneMania(), config);
	}
}
