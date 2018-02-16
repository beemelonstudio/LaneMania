package com.beemelonstudio.lanemania.screens.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.beemelonstudio.lanemania.entities.objects.StraightLine;
import com.beemelonstudio.lanemania.entities.types.EntityType;
import com.beemelonstudio.lanemania.screens.GameScreen;
import com.beemelonstudio.lanemania.screens.PlayScreen;
import com.beemelonstudio.lanemania.screens.custombuttons.BmsImageButton;
import com.beemelonstudio.lanemania.utils.assets.Assets;


/**
 * Created by Stampler on 09.01.2018.
 */

public class PlayScreenUI extends GameScreenUI {

    private PlayScreen screen;

    private float height, width;
    private float numberOfButtons = 6f;

    private TextButton playButton;
    private TextButton resetButton;
    private BmsImageButton[] lineButtons;

    public PlayScreenUI(GameScreen screen) {
        super(screen);

        this.screen = (PlayScreen) screen;

        textureAtlas = (TextureAtlas) Assets.get("orange-theme");

        height = (Gdx.graphics.getHeight() / 2f) / 5f;
        width = Gdx.graphics.getWidth() / numberOfButtons;

        table.row();

        createPlayButton();
        createResetButton();
        createBmsImageButtons();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw(PolygonSpriteBatch batch) {
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

                Array<Body> bodies = new Array<Body>();
                screen.worldManager.world.getBodies(bodies);

                for ( Array.ArrayIterator<Body> iter = new Array.ArrayIterator<Body>(bodies, true); iter.hasNext();) {
                    Body body = iter.next();
                    if (body != null) {
                        EntityType type = asEntityType(body.getUserData().toString());
                        if(type == EntityType.STRAIGHTLINE) {
                            screen.worldManager.world.destroyBody(body);
                            screen.straightLines = new Array<StraightLine>();
                        }
                    }
                }
            }
        });
    }

    private void createBmsImageButtons() {

        // Retrieve images for buttons
        TextureRegion arrow = textureAtlas.findRegion("small_arrow");
        TextureRegion straightLine = textureAtlas.findRegion("rectangle_long");
        TextureRegion curvyLine = textureAtlas.findRegion("swiperadius");

        // Setup BmsImageButton Array and set left and right button for switching
        lineButtons = new BmsImageButton[4];
        lineButtons[3] = new BmsImageButton(skin, arrow);
        arrow = new TextureRegion(arrow);
        arrow.flip(true, false);
        lineButtons[0] = new BmsImageButton(skin, arrow);

        // Insert actual line buttons
        lineButtons[1] = new BmsImageButton(skin, straightLine);
        lineButtons[2] = new BmsImageButton(skin, curvyLine);

        // Add to table
        for(int i = 0; i < lineButtons.length; i++)
            table.add(lineButtons[i]).height(height).width(width);
        table.top().right();

        // TODO: This is for going back to the MapSelectionScreen
        lineButtons[3].addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

                screen.game.screens.pop();
                screen.game.setScreen(screen.game.screens.peek());
            }
        });
    }

    private EntityType asEntityType(String str) {
        for (EntityType me : EntityType.values()) {
            if (me.name().equalsIgnoreCase(str))
                return me;
        }
        return null;
    }
}
