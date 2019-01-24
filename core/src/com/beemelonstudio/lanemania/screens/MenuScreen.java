package com.beemelonstudio.lanemania.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
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
    private Cell soundCell;
    private Cell languageCell;

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
        textureAtlas = (TextureAtlas) Assets.get("wildwest-theme");
        leftRope = textureAtlas.findRegion("straightline");
        rightRope = textureAtlas.findRegion("straightline");

        // Used for debugging
        table.setDebug(true);

        createMenu();

        table.setTransform(true);
        table.setScale(1.5f);
        table.setOrigin(Align.center);
        table.setPosition(-Gdx.graphics.getWidth()/4f, -Gdx.graphics.getHeight()/4f);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        batch.setProjectionMatrix(backgroundViewport.getCamera().combined);
        batch.begin();

        if(logoCell != null) {
            float logoPositionY = logoCell.getActorY() ;//+ (logoCell.getActorHeight()/2f);

            // Ropes above the logo
            batch.draw(leftRope, scaleUI(logoCell.getActorX()) * 3f, logoPositionY, 0, 0, upperRopeWidth, upperRopeHeight, 1f, 1f, 90f);
            batch.draw(rightRope, scaleUI((logoCell.getActorX()) + scaleUI(logoCell.getActorWidth()) * 0.95f), logoPositionY, 0, 0, upperRopeWidth, upperRopeHeight, 1f, 1f, 90f);

            // Ropes below the logo
            batch.draw(leftRope, scaleUI(soundCell.getActorX()) + scaleUI(soundCell.getActorWidth()) / 2f, logoPositionY, 0, 0, lowerRopeWidth, lowerRopeHeight, 1f, 1f, -90f);
            batch.draw(rightRope, scaleUI(languageCell.getActorX()) + (scaleUI(languageCell.getActorWidth()) * 0.5f), logoPositionY, 0, 0, lowerRopeWidth, lowerRopeHeight, 1f, 1f, -90f);
            //batch.draw(leftRope, logoCell.getActorX(), logoCell.getActorY(), 0, 0, width, height, 1f, 1f, 90f);
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
        Image logo = new Image(new TextureRegionDrawable(((TextureAtlas)Assets.get("general-theme")).findRegion("app_name_plate")), Scaling.fillX);
        TextButton continueButton = new TextButton("Continue", skin, "two_arrows");
        TextButton selectLevelButton = new TextButton("Level", skin, "one_arrow");
        ImageButton languageButton = new ImageButton(skin, "languageDE");
        ImageButton soundButton = new ImageButton(skin, "sound_on");

        logoCell = table.add(logo).colspan(2);//.pad(50f).padBottom(60f).colspan(2);//.grow();
        table.row();
        table.add(continueButton).colspan(2);//.grow();
        table.row();
        table.add(selectLevelButton).colspan(2);//.grow();
        table.row();
        soundCell = table.add(soundButton);//.grow();
        languageCell = table.add(languageButton);//.grow();

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
     * The lower two ropes are calculated similarly, but the distance is from the logo to a point (80%) near the display
     */
    private void calculateRopeDimensions() {

        //Using the left rope for the aspec ratio since both images are the same
        float aspectRatio = (float) leftRope.getRegionWidth() / (float) leftRope.getRegionHeight();
        float upperDistance = Gdx.graphics.getHeight() * 1.1f - logoCell.getActorY();
        float lowerDistance = logoCell.getActorY() * 0.8f;

        upperRopeWidth = upperDistance;
        upperRopeHeight = upperRopeWidth / aspectRatio;

        lowerRopeWidth = lowerDistance;
        lowerRopeHeight = lowerRopeWidth / aspectRatio;
    }
}
