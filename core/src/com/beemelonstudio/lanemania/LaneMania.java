package com.beemelonstudio.lanemania;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.beemelonstudio.lanemania.screens.GameScreen;
import com.beemelonstudio.lanemania.screens.MapSelectionScreen;
import com.beemelonstudio.lanemania.screens.MenuScreen;
import com.beemelonstudio.lanemania.screens.PlayScreen;
import com.beemelonstudio.lanemania.utils.assets.Assets;
import com.beemelonstudio.lanemania.utils.mapeditor.MapLoader;

import java.util.Stack;

public class LaneMania extends Game {

    public static final float VIRTUAL_WIDTH = 1f;
    public static final float VIRTUAL_HEIGHT = 2f;

    public PolygonSpriteBatch batch;
    public OrthographicCamera camera, hudCamera;
    public Viewport viewport, backgroundViewport;

    public Stage stage;
    public Skin skin;

    public Stack<GameScreen> screens;
    public MapLoader mapLoader;

    @Override
	public void create () {
        Assets.load();
        mapLoader = new MapLoader();
        mapLoader.loadMaps();

        skin = (Skin) Assets.get("pixthulhuSkin");

        backgroundMusic = (Music)Assets.get("backgroundMenuMusic");
        backgroundMusic.play();
        backgroundMusic.setVolume(volume);
        backgroundMusic.setLooping(true);



        camera = new OrthographicCamera(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        hudCamera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        viewport = new FitViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, camera);
        backgroundViewport = new ScreenViewport();
        batch = new PolygonSpriteBatch();
        stage = new Stage(new ScreenViewport(hudCamera));

        screens = new Stack<GameScreen>();
        screens.push(new MenuScreen(this));
        //screens.push(new MapSelectionScreen(this, mapLoader.worlds));
        screens.push(new PlayScreen(this, mapLoader.getMap(0,4)));

        setScreen(screens.peek());
	}

    @Override
    public void dispose() {
        super.dispose();
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void resume() {
        super.resume();
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);

        viewport.update(width, height);
        backgroundViewport.update(width, height, true);
        hudCamera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.setProjectionMatrix(camera.combined);
    }
}
