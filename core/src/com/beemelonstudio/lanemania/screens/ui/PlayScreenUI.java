package com.beemelonstudio.lanemania.screens.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.beemelonstudio.lanemania.screens.GameScreen;
import com.beemelonstudio.lanemania.screens.PlayScreen;
import com.beemelonstudio.lanemania.utils.Assets;

/**
 * Created by Stampler on 09.01.2018.
 */

public class PlayScreenUI extends GameScreenUI {

    private PlayScreen screen;

    private float height, width;
    private float numberOfButtons = 6f;

    private TextButton playButton;
    private TextButton resetButton;
    private bmsImageButton[] lineButtons;

    public PlayScreenUI(GameScreen screen) {
        super(screen);

        this.screen = (PlayScreen) screen;

        textureAtlas = (TextureAtlas) Assets.get("orange-theme");

        height = (Gdx.graphics.getHeight() / 2f) / 5f;
        width = Gdx.graphics.getWidth() / numberOfButtons;

        table.row();

        createPlayButton();
        createResetButton();
        createbmsImageButtons();
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
        table.add(playButton).height(height).width(width);
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

    private void createResetButton() {

        // Create and position
        resetButton = new TextButton("Reset", skin);
        table.add(resetButton).height(height).width(width);
        table.top().right();

        // Listener
        resetButton.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

                screen.gravity = false;
                screen.ball.reset();
            }
        });
    }

    private void createbmsImageButtons() {

        // Retrieve images for buttons
        TextureRegion arrow = textureAtlas.findRegion("small_arrow");
        TextureRegion straightLine = textureAtlas.findRegion("rectangle_long");
        TextureRegion curvyLine = textureAtlas.findRegion("swiperadius");

        // Setup bmsImageButton Array and set left and right button for switching
        lineButtons = new bmsImageButton[4];
        lineButtons[3] = new bmsImageButton(skin, arrow);
        arrow = new TextureRegion(arrow);
        arrow.flip(true, false);
        lineButtons[0] = new bmsImageButton(skin, arrow);

        // Insert actual line buttons
        lineButtons[1] = new bmsImageButton(skin, straightLine);
        lineButtons[2] = new bmsImageButton(skin, curvyLine);

        // Add to table
        for(int i = 0; i < lineButtons.length; i++)
            table.add(lineButtons[i]).height(height).width(width);
        table.top().right();
    }
}
