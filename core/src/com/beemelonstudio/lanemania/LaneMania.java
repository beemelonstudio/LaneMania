package com.beemelonstudio.lanemania;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Sort;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.beemelonstudio.lanemania.screens.GameScreen;
import com.beemelonstudio.lanemania.screens.MapSelectionScreen;
import com.beemelonstudio.lanemania.screens.MenuScreen;
import com.beemelonstudio.lanemania.screens.PlayScreen;
import com.beemelonstudio.lanemania.utils.assets.Assets;
import com.beemelonstudio.lanemania.utils.mapeditor.Level;
import com.beemelonstudio.lanemania.utils.mapeditor.MapLoader;
import com.github.czyzby.lml.parser.LmlParser;
import com.github.czyzby.lml.util.LmlApplicationListener;
import com.github.czyzby.lml.vis.util.VisLml;
import com.kotcrab.vis.ui.VisUI;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Stack;

import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;

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
    public Array<Level> levels;

    public Preferences preferences;

    public float volume = 0f;
    public Boolean muted = false;
    public Music backgroundMusic;

    public boolean english = true;

    @Override
	public void create () {

        Gdx.app.setLogLevel(Application.LOG_DEBUG);

        Assets.load();

        skin = (Skin) Assets.get("beemelonSkin");
        VisUI.load(skin);

        backgroundMusic = (Music) Assets.get("backgroundMenuMusic");
        backgroundMusic.play();
        backgroundMusic.setVolume(volume);
        backgroundMusic.setLooping(true);

        camera = new OrthographicCamera();
        hudCamera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        viewport = new ExtendViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, camera);
        backgroundViewport = new ScreenViewport();
        backgroundViewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
        batch = new PolygonSpriteBatch();
        stage = new Stage(new ScreenViewport());
        stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);

        levels = loadLevels();
        Sort.instance().sort(levels, (l1, l2) -> l1.gameplayOrder - l2.gameplayOrder);

        screens = new Stack<>();
//        screens.push(new MenuScreen(this));
//        screens.push(new MapSelectionScreen(this, levels));
        screens.push(new PlayScreen(this, levels.get(10)));

        setScreen(screens.peek());
	}

	private Array<Level> loadLevels() {
        mapLoader = new MapLoader();
        mapLoader.loadMaps();

        Array<Level> levels = new Array<>();
        for (Array<String> world : mapLoader.worlds) {
            for (String map : world) {
                Level level = new Level(map);
                levels.add(level);
            }
        }

        return levels;
    }

    @Override
    public void dispose() {
        super.dispose();
        VisUI.dispose();
        Assets.dispose();
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

        stage.getViewport().update(width, height, true);
        viewport.update(width, height, false);
        camera.position.set(VIRTUAL_WIDTH/2, VIRTUAL_HEIGHT/2, 0);
        backgroundViewport.update(width, height, true);
        hudCamera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.setProjectionMatrix(camera.combined);
    }
}
