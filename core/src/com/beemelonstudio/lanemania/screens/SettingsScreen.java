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

    private float height, width;
    private float numberOfButtons = 4f;


    public SettingsScreen(LaneMania game) {
        super(game);

        height = (Gdx.graphics.getHeight() / 2f) / 5f;
        width = Gdx.graphics.getWidth() / numberOfButtons;
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

        table.add(muteButton).colspan(3).height(height).width(width).center();
        table.row().pad(10, 0, 10, 0);
        table.add(leftArrowButton).height(height).width(width);
        table.add(english).height(height).width(width).center();
        table.add(rightArrowButton).height(height).width(width);
        table.row().pad(10, 0, 10, 0);
        table.add(returnButton).colspan(3).center();

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
