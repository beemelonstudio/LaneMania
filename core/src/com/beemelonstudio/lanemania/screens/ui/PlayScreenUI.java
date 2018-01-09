package com.beemelonstudio.lanemania.screens.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.beemelonstudio.lanemania.screens.GameScreen;
import com.beemelonstudio.lanemania.screens.PlayScreen;
import com.beemelonstudio.lanemania.utils.Assets;

/**
 * Created by Jann on 27.12.17.
 */

public class PlayScreenUI extends GameScreenUI {

    private PlayScreen screen;

    private TextButton playButton;

    public PlayScreenUI(GameScreen screen) {
        super(screen);

        this.screen = (PlayScreen) screen;

        textureAtlas = (TextureAtlas) Assets.get("orange-theme");

        createPlayButton();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);
    }

    private void createPlayButton() {

        // Create and position
        playButton = new TextButton("Play", skin);
        table.add(playButton);
        table.top().right();

        // Listener
        playButton.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

                screen.gravity = true;
            }
        });
    }
}
