package com.beemelonstudio.lanemania.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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

    public MenuScreen(LaneMania game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();

        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        isBackgroundDrawing = true;
        textureAtlas = (TextureAtlas) Assets.get("general-theme");

        // Used for debugging
        //table.setDebug(true);

        createMenu();

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
    }

    public void createMenu(){

        table.padBottom(100);
        Image logo = new Image(textureAtlas.findRegion("logo"));
        TextButton continueButton = new TextButton("Continue", skin);
        TextButton selectLevelButton = new TextButton("Select\nLevel", skin);
        TextButton settingsButton = new TextButton("Settings", skin);

        //table.add(logo).width(logo.getWidth()/2).height((logo.getHeight()/logo.getWidth())*logo.getWidth()/2);
        table.add(logo).width(Gdx.graphics.getWidth()/2).height((logo.getHeight()/logo.getWidth())*Gdx.graphics.getWidth()/2);///2)
        table.row().pad(80, 0, 10, 0);
        //table.add(continueButton).width(continueButton.getWidth()).height(continueButton.getHeight()/2);
        table.add(continueButton).width(Gdx.graphics.getWidth()/4).height((continueButton.getHeight()/continueButton.getWidth())*(Gdx.graphics.getWidth()/4));
        table.row().pad(10, 0, 10, 0);
        table.add(selectLevelButton).width(Gdx.graphics.getWidth()/4).height((selectLevelButton.getHeight()/selectLevelButton.getWidth())*(Gdx.graphics.getWidth()/4));
        table.row().pad(10, 0, 10, 0);
        table.add(settingsButton).width(Gdx.graphics.getWidth()/4).height((settingsButton.getHeight()/settingsButton.getWidth())*(Gdx.graphics.getWidth()/4));

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
