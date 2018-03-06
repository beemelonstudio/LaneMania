package com.beemelonstudio.lanemania.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.beemelonstudio.lanemania.LaneMania;

/**
 * Created by Cedric on 30.01.2018.
 */

public class MenuScreen extends GameScreen {

    public Table table;

    public MenuScreen(LaneMania game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();

        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        // Used for debugging
        table.setDebug(true);

        createMenu();

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
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
}
