package com.beemelonstudio.lanemania.screens.ui;

import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.beemelonstudio.lanemania.screens.GameScreen;

/**
 * Created by Jann on 27.12.17.
 */

public class GameScreenUI {

    protected Stage stage;
    protected Skin skin;
    protected Table table;
    protected TextureAtlas textureAtlas;

    public GameScreenUI(GameScreen screen) {

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

    protected void draw(PolygonSpriteBatch batch) {

    }
}
