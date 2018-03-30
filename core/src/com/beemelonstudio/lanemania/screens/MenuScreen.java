package com.beemelonstudio.lanemania.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.beemelonstudio.lanemania.LaneMania;
import com.beemelonstudio.lanemania.utils.assets.Assets;

/**
 * Created by Cedric on 30.01.2018.
 */

public class MenuScreen extends GameScreen {

    public Table table;
    public TextureRegion backgroundRegion;

    public MenuScreen(LaneMania game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();

        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        textureAtlas = (TextureAtlas) Assets.get("wildwest-theme");
        TextureRegion backgroundRegion = textureAtlas.findRegion("ball");

        // Used for debugging
        table.setDebug(true);

        createMenu();

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        batch.begin();
        batch.draw(backgroundRegion,
                0,
                0,
                Gdx.graphics.getWidth(),
                Gdx.graphics.getHeight());
        batch.end();
    }

    public void createMenu(){

        TextButton continueButton = new TextButton("Continue", skin);
        TextButton selectLevelButton = new TextButton("Select Level", skin);
        TextButton settingsButton = new TextButton("Settings", skin);

        table.add(continueButton).fillX().uniformX();
        table.row().pad(10, 0, 10, 0);
        table.add(selectLevelButton).fillX().uniformX();
        table.row().pad(10, 0, 10, 0);
        table.add(settingsButton).fillX().uniformX();

        selectLevelButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

                screens.push(new MapSelectionScreen(game, game.mapLoader.worlds));
                game.setScreen(screens.peek());
            }
        });

        settingsButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

                screens.push(new SettingsScreen(game));
                game.setScreen(screens.peek());
            }
        });
    }

    //public void drawBackground(){

    //}

}
