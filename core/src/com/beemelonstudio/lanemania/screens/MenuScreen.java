package com.beemelonstudio.lanemania.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.beemelonstudio.lanemania.LaneMania;
import com.beemelonstudio.lanemania.utils.Assets;

/**
 * Created by Cedric on 30.01.2018.
 */

public class MenuScreen extends GameScreen {

    public Table table;

    Sound backgroundMusic;

    Boolean muted = false;

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

        backgroundMusic = (Sound)Assets.get("backgroundMenuMusic");
        backgroundMusic.loop(volume);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
    }

    public void createMenu(){

        TextButton continueButton = new TextButton("Continue", skin);
        TextButton selectLevelButton = new TextButton("Select Level", skin);
        ImageButton muteButton = new ImageButton(skin);
        muteButton.setPosition(Gdx.graphics.getWidth()-muteButton.getWidth(), Gdx.graphics.getHeight()-muteButton.getHeight());
        stage.addActor(muteButton);

        table.add(continueButton).fillX().uniformX();
        table.row().pad(10, 0, 10, 0);
        table.add(selectLevelButton).fillX().uniformX();

        selectLevelButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

                screens.push(new MapSelectionScreen(game, game.mapLoader.worlds));
                game.setScreen(screens.peek());
            }
        });

        muteButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

                //TODO: Sound and music difference
                if (muted == false){
                    volume = 0.0f;
                    backgroundMusic.setVolume(0, volume);
                    muted = true;
                } else {
                    volume = 1.0f;
                    backgroundMusic.setVolume(0, volume);
                    muted = false;
                }

            }
        });

    }



}
