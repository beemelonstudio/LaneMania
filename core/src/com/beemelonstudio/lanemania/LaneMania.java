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
import com.badlogic.gdx.utils.viewport.Viewport;
import com.beemelonstudio.lanemania.screens.GameScreen;

import java.util.Stack;

public class LaneMania extends Game {

    public static final float WIDTH = 1f;
    public static final float HEIGHT = 2f;
    private static final float VIRTUAL_HEIGHT = 2f;

    public SpriteBatch batch;
    public PolygonSpriteBatch polygonbatch;
    public OrthographicCamera camera, hudCamera;
    public Viewport viewport;

    public Stage stage;
    public Skin skin;

    public Stack<GameScreen> screens;

	@Override
	public void create () {

	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}
	
	@Override
	public void dispose () {
	}
}
