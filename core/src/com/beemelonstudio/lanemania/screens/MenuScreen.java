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

/**
 * Created by Cedric on 30.01.2018.
 */

public class MenuScreen extends GameScreen {

    public Table table;
    Sound backgroundMenuMusic = Gdx.audio.newSound(Gdx.files.internal("sounds/backgroundExample.mp3"));

    Boolean isMuted = false;

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

        backgroundMenuMusic.loop(volume);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
    }

    public void createMenu(){

        TextButton Continue = new TextButton("Continue", skin);
        TextButton SelectLevel = new TextButton("Select Level", skin);
        ImageButton Mute = new ImageButton(skin);
        Mute.setPosition(100,100);
        stage.addActor(Mute);

        table.add(Continue).fillX().uniformX();
        table.row().pad(10, 0, 10, 0);
        table.add(SelectLevel).fillX().uniformX();

        SelectLevel.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

                screens.push(new MapSelectionScreen(game, game.mapLoader.worlds));
                game.setScreen(screens.peek());
            }
        });

        Mute.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

                if (isMuted == false){
                    volume = 0.0f;
                    isMuted = true;
                } else {
                    volume = 1.0f;
                    isMuted = false;
                }

            }
        });

    }



}
