package com.beemelonstudio.lanemania.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.beemelonstudio.lanemania.LaneMania;
import com.beemelonstudio.lanemania.screens.custombuttons.bmsImageButton;

/**
 * Created by Cedric on 06.03.2018.
 */

public class SettingsScreen extends GameScreen {

    public Table table;
    private TextureRegion muteIcon;
    private TextureRegion leftArrowIcon;
    private TextureRegion rightArrowIcon;


    public SettingsScreen(LaneMania game) {
        super(game);


    }

    @Override
    public void show() {
        super.show();

        muteIcon = textureAtlas.findRegion("triangle");
        leftArrowIcon = textureAtlas.findRegion("triangle");
        rightArrowIcon = textureAtlas.findRegion("triangle");

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

        bmsImageButton muteButton = new bmsImageButton(skin, muteIcon);
        bmsImageButton leftArrowButton = new bmsImageButton(skin, leftArrowIcon);
        bmsImageButton rightArrowButton = new bmsImageButton(skin, rightArrowIcon);
        TextButton returnButton = new TextButton("Return", skin);
        Label english = new Label("English", skin);

        table.add(muteButton).fillX().uniformX();
        table.row().pad(10, 0, 10, 0);
        table.add(leftArrowButton).fillX().uniformX();
        table.add(english).fillX().uniformX();
        table.add(rightArrowButton).fillX().uniformX();
        table.row().pad(10, 0, 10, 0);
        table.add(returnButton).fillX().uniformX();

        muteButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

                if (muted == false){
                    volume = 0.0f;
                    backgroundMusic.setVolume(volume);
                    muted = true;
                } else {
                    volume = 1.0f;
                    backgroundMusic.setVolume(volume);
                    muted = false;
                }
            }
        });

        returnButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

                screens.pop();
                game.setScreen(screens.peek());
            }
        });

    }

}
