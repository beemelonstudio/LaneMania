package com.beemelonstudio.lanemania;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.beemelonstudio.lanemania.screens.GameScreen;
import com.beemelonstudio.lanemania.screens.PlayScreen;
import com.beemelonstudio.lanemania.utils.Assets;

import java.util.Stack;

public class LaneMania extends Game {

    public static final float WIDTH = 1f;
    public static final float HEIGHT = 2f;
    private static final float VIRTUAL_HEIGHT = 2f;

    public SpriteBatch batch;
    public OrthographicCamera camera, hudCamera;
    public Viewport viewport;

    public Stage stage;
    public Skin skin;

    public Stack<GameScreen> screens;

	@Override
	public void create () {
        Assets.load();

        skin = (Skin) Assets.get("pixthulhuSkin");

        camera = new OrthographicCamera();
        hudCamera = new OrthographicCamera();
        batch = new SpriteBatch();
        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), hudCamera));

        screens = new Stack<GameScreen>();
        screens.push(new PlayScreen(this));

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

        camera.setToOrtho(false, VIRTUAL_HEIGHT * width / (float) height, VIRTUAL_HEIGHT);
        hudCamera.setToOrtho(false, width, height);
        batch.setProjectionMatrix(camera.combined);
    }
}
