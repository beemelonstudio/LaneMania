package com.beemelonstudio.lanemania.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.beemelonstudio.lanemania.LaneMania;
import com.beemelonstudio.lanemania.screens.custombuttons.BmsImageButton;
import com.beemelonstudio.lanemania.utils.assets.Assets;

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

        isBackgroundDrawing = true;
        textureAtlas = (TextureAtlas) Assets.get("general-theme");

        muteIcon = textureAtlas.findRegion("mute_button_on");
        leftArrowIcon = textureAtlas.findRegion("arrow_left");
        rightArrowIcon = textureAtlas.findRegion("arrow_right");

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

        BmsImageButton muteButton = new BmsImageButton(skin, textureAtlas.findRegion("mute_button_on"));
        muteButton.getStyle().imageChecked = new TextureRegionDrawable(textureAtlas.findRegion("mute_button_off"));
        BmsImageButton leftArrowButton = new BmsImageButton(skin, leftArrowIcon);
        BmsImageButton rightArrowButton = new BmsImageButton(skin, rightArrowIcon);
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
