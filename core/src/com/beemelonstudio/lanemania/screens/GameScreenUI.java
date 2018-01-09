package com.beemelonstudio.lanemania.screens;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

/**
 * Created by Jann on 27.12.17.
 */

public class GameScreenUI {

    protected GameScreen screen;

    protected Stage stage;
    protected Skin skin;
    protected Table table;
    protected TextureAtlas textureAtlas;

    public GameScreenUI(){}

    public GameScreenUI( GameScreen screen) {

        this.stage = screen.game.stage;
        this.skin = screen.game.skin;

        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        // Used for debugging
        table.setDebug(true);
    }

    protected void act(float delta) {

    }

    protected void draw(SpriteBatch batch) {

    }
}
