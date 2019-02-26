package com.beemelonstudio.lanemania.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;
import com.beemelonstudio.lanemania.LaneMania;
import com.beemelonstudio.lanemania.screens.custombuttons.BmsImageButton;
import com.beemelonstudio.lanemania.screens.custombuttons.BmsTextButton;
import com.beemelonstudio.lanemania.utils.UIHelper;
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

    Sprite sprite, sprite2;

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
//        table.setDebug(true);

        createMenu();

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        batch.setProjectionMatrix(backgroundViewport.getCamera().combined);
        batch.begin();

        if(logoCell != null) {
            float logoPositionY = logoCell.getActorY() ;

            // Ropes above the logo
            batch.draw(leftRope, logoCell.getActorX() + logoCell.getActorWidth() * 0.15f, logoPositionY, 0, 0, upperRopeWidth, upperRopeHeight, 1f, 1f, 90f);
            batch.draw(rightRope, logoCell.getActorX() + logoCell.getActorWidth() * 0.9f, logoPositionY, 0, 0, upperRopeWidth, upperRopeHeight, 1f, 1f, 90f);

            // Ropes below the logo
            batch.draw(leftRope, (soundCell.getActorX() + soundCell.getActorWidth()/2f - lowerRopeHeight/2f), logoPositionY + logoCell.getActorHeight()/2f, 0, 0, lowerRopeWidth, lowerRopeHeight, 1f, 1f, -90f);
            batch.draw(rightRope, (languageCell.getActorX() + languageCell.getActorWidth()/2f - lowerRopeHeight/2f), logoPositionY + logoCell.getActorHeight()/2f, 0, 0, lowerRopeWidth, lowerRopeHeight, 1f, 1f, -90f);
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

        TextureRegionDrawable d = new TextureRegionDrawable(((TextureAtlas)Assets.get("general-theme")).findRegion("app_name_plate"));
        Image logo = new Image(d, Scaling.stretch);
        float aspectRatio = d.getRegion().getRegionWidth()/d.getRegion().getRegionHeight();
        float width = (float)Gdx.graphics.getWidth()*0.8f;
        float height = width / aspectRatio;

        BmsTextButton continueButton = new BmsTextButton("Continue", skin, "two_arrows");
        BmsTextButton selectLevelButton = new BmsTextButton("Level", skin, "one_arrow");
        final BmsImageButton languageButton = new BmsImageButton(skin, "languageEN");
        final BmsImageButton soundButton = new BmsImageButton(skin, "sound_on");

        logoCell = table.add(logo).width(width).height(height).colspan(2);
        table.row();
        table.add(continueButton).width(width).height(height).colspan(2);
        table.row();
        table.add(selectLevelButton).width(width).height(height).colspan(2);
        table.row();

        // Recalculate width and height
        soundCell = UIHelper.addToTable(table, soundButton, width/4f);
        languageCell = UIHelper.addToTable(table, languageButton, width/3.5f);

        selectLevelButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

                screens.push(new MapSelectionScreen(game, game.levels));
                game.setScreen(screens.peek());
            }
        });

        soundButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

                if (!muted){
                    volume = 0.0f;
                    backgroundMusic.setVolume(volume);
                    muted = true;
                    soundButton.setStyle(skin.get("sound_off", ImageButton.ImageButtonStyle.class));
                }
                else {
                    volume = 1.0f;
                    backgroundMusic.setVolume(volume);
                    muted = false;
                    soundButton.setStyle(skin.get("sound_on", ImageButton.ImageButtonStyle.class));
                }
            }
        });

        languageButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

                if (!english){
                    languageButton.setStyle(skin.get("languageEN", ImageButton.ImageButtonStyle.class));
                }
                else {
                    languageButton.setStyle(skin.get("languageDE", ImageButton.ImageButtonStyle.class));
                }
                english = !english;
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
