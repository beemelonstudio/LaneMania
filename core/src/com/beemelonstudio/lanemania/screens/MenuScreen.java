package com.beemelonstudio.lanemania.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;
import com.beemelonstudio.lanemania.LaneMania;
import com.beemelonstudio.lanemania.utils.assets.Assets;

/**
 * Created by Cedric on 30.01.2018.
 */

public class MenuScreen extends GameScreen {

    public Table table;
    private boolean menuCreated = false;
    private boolean calculatedOnce = false;

    private Cell logoCell;

    private TextureRegion leftRope, rightRope;

    private float upperRopeWidth, upperRopeHeight;
    private float lowerRopeWidth, lowerRopeHeight;

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
        leftRope = textureAtlas.findRegion("seil1");
        rightRope = textureAtlas.findRegion("seil2");

        // Used for debugging
        table.setDebug(true);

        createMenu();

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        batch.setProjectionMatrix(backgroundViewport.getCamera().combined);
        batch.begin();

        if(logoCell != null) {
            // Ropes above the logo
            batch.draw(leftRope, logoCell.getActorX() + upperRopeWidth / 2, logoCell.getActorY(), upperRopeWidth, upperRopeHeight);
            batch.draw(rightRope, (logoCell.getActorX() + logoCell.getActorWidth()) - upperRopeWidth * 1.5f, logoCell.getActorY(), upperRopeWidth, upperRopeHeight);

            // Ropes below the logo
            batch.draw(rightRope, logoCell.getActorX() + logoCell.getActorWidth() / 3 - lowerRopeWidth, logoCell.getActorY() - lowerRopeHeight * 0.8f, upperRopeWidth, upperRopeHeight);
            batch.draw(leftRope, logoCell.getActorX() + logoCell.getActorWidth() / 3 * 2, logoCell.getActorY() - lowerRopeHeight * 0.8f, lowerRopeWidth, upperRopeHeight);
        }
        batch.end();
        batch.setProjectionMatrix(camera.combined);

        postDraw(delta);

        // Dimensions are calculated after the first render.
        // This first render sets up the UI elements
        if(!calculatedOnce) {
            calculateRopeDimensions();
            calculatedOnce = true;
        }
    }

    public void createMenu() {

        //table.padBottom(100);
        Image logo = new Image(new TextureRegionDrawable(textureAtlas.findRegion("app_name_plate")), Scaling.fillX);
        TextButton continueButton = new TextButton("Continue", skin, "two_arrows");
        TextButton selectLevelButton = new TextButton("Level", skin, "one_arrow");

        logoCell = table.add(logo).padRight(50f).padLeft(50f).growX();
        table.row().pad(80, 0, 10, 0);
        table.add(continueButton).padRight(80f).padLeft(80f).growX();
        table.row().pad(10, 0, 10, 0);
        table.add(selectLevelButton).padRight(80f).padLeft(80f).padBottom(50f).growX();

        selectLevelButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

                screens.push(new MapSelectionScreen(game, game.mapLoader.worlds));
                game.setScreen(screens.peek());
            }
        });
    }

    /*
     * The height for the upper two ropes the distance between the logo and the display height
     * THe width is calculated accordingly, while keeping the aspect ratio
     * 
     * The lower two ropes are calculated similarly, but the distance is from the logo to a point (90%) near the display
     */
    private void calculateRopeDimensions() {

        //Using the left rope for the aspec ratio since both images are the same
        float aspectRatio = (float) leftRope.getRegionHeight() / (float) leftRope.getRegionWidth();
        float upperDistance = Gdx.graphics.getHeight() * 1.1f - logoCell.getActorY();
        float lowerDistance = logoCell.getActorY() * 0.9f;

        upperRopeHeight = upperDistance;
        upperRopeWidth = upperRopeHeight / aspectRatio;

        lowerRopeHeight = lowerDistance;
        lowerRopeWidth = lowerRopeHeight / aspectRatio;

        Gdx.app.log("//////////////////////////////////", "########################################################################");
        Gdx.app.log("Aspect Ratio", ""+aspectRatio);
        Gdx.app.log("Logo Cell Y", ""+logoCell.getActorY());
        Gdx.app.log("Distances", upperDistance+" - "+lowerDistance);
        Gdx.app.log("Upper", upperRopeWidth+" - "+upperRopeHeight);
        Gdx.app.log("Lower", lowerRopeWidth+" - "+lowerRopeHeight);
    }
}
