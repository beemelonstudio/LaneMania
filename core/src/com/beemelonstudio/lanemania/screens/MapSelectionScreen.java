package com.beemelonstudio.lanemania.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.beemelonstudio.lanemania.LaneMania;
import com.beemelonstudio.lanemania.screens.custombuttons.BmsImageButton;
import com.beemelonstudio.lanemania.utils.UIHelper;
import com.beemelonstudio.lanemania.utils.assets.Assets;
import com.beemelonstudio.lanemania.utils.mapeditor.Level;

/**
 * Created by Jann on 27.01.18.
 */

public class MapSelectionScreen extends GameScreen {

    public Array<Level> levels;

    public Table table;
    public BmsImageButton leftArrowButton;
    public BmsImageButton rightArrowButton;

    private TextureRegion leftArrowIconUp;
    private TextureRegion leftArrowIconDown;
    private TextureRegion rightArrowIconUp;
    private TextureRegion rightArrowIconDown;
    private TextureRegion emptyStar;
    private TextureRegion fullStar;
    private TextureRegion wantedPoster;

    private TextureRegion rope;

    private Image levelImage;
    private Label levelLabel;

    private Cell starCell;

    private float ropeWidth, ropeHeight;

    private int worldIndex = 0;
    private int mapIndex = 0;

    public MapSelectionScreen(LaneMania game, Array<Level> levels) {
        super(game);
        this.levels = levels;

        isBackgroundDrawing = true;
        textureAtlas = (TextureAtlas) Assets.get("general-theme");

        rightArrowIconUp = new TextureRegion(textureAtlas.findRegion("play_button_on"));
        rightArrowIconDown = new TextureRegion(textureAtlas.findRegion("play_button_off"));

        leftArrowIconUp = new TextureRegion(textureAtlas.findRegion("play_button_on"));
        leftArrowIconUp.flip(true, false);
        leftArrowIconDown = new TextureRegion(textureAtlas.findRegion("play_button_off"));
        leftArrowIconDown.flip(true, false);

        emptyStar = new TextureRegion(textureAtlas.findRegion("kein_star_wood"));
        fullStar = new TextureRegion(textureAtlas.findRegion("star"));

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
        super.show();

        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        // Used for debugging
//        table.setDebug(true);

        createWorldSelection();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        batch.setProjectionMatrix(backgroundViewport.getCamera().combined);
        batch.begin();

        if(starCell != null) {
            batch.draw(rope, starCell.getActorX() + starCell.getActorWidth() * 0.15f, starCell.getActorY() + starCell.getActorHeight()/2f, 0, 0, ropeWidth, ropeHeight/2f, 1f, 1f, 90f);
            batch.draw(rope, starCell.getActorX() + starCell.getActorWidth() * 0.9f, starCell.getActorY() + starCell.getActorHeight()/2f, 0, 0, ropeWidth, ropeHeight/2f, 1f, 1f, 90f);
        }

        batch.end();
        batch.setProjectionMatrix(camera.combined);

        postDraw(delta);

        batch.setProjectionMatrix(backgroundViewport.getCamera().combined);
        batch.begin();

        // TODO: Do this right
        float width = starCell.getActorWidth() * 0.8f;
        float widthPerStar = width/3f;
        for (int i = 0; i < 3; i++)
            batch.draw(emptyStar, starCell.getActorX() + widthPerStar * i + widthPerStar/2f, starCell.getActorY() + starCell.getActorHeight()/2f - widthPerStar/4f, widthPerStar/2f, widthPerStar/2f);

        batch.end();
        batch.setProjectionMatrix(camera.combined);
    }

    public void createWorldSelection() {

        rope = ((TextureAtlas) Assets.get("wildwest-theme")).findRegion("straightline");
        Image level = new Image(new TextureRegionDrawable(textureAtlas.findRegion("level")));
        Image starDisplay = new Image(new TextureRegionDrawable(textureAtlas.findRegion("grosses_schild")));
        levelImage = new Image(new TextureRegionDrawable(textureAtlas.findRegion("w1l1")));
        levelLabel = new Label("Level Placeholder", skin);

        leftArrowButton = new BmsImageButton(skin, leftArrowIconUp, "transparent");
        leftArrowButton.getStyle().imageDown = new TextureRegionDrawable(leftArrowIconDown);

        rightArrowButton = new BmsImageButton(skin, rightArrowIconUp, "transparent");
        rightArrowButton.getStyle().imageDown = new TextureRegionDrawable(rightArrowIconDown);

        float width = (float)Gdx.graphics.getWidth()*0.8f;

        UIHelper.addToTable(table, level, (float)Gdx.graphics.getWidth()*0.5f).colspan(3).pad(20);
        table.row();
        starCell = UIHelper.addToTable(table, starDisplay, width).colspan(3).pad(20);
        table.row();
        UIHelper.addToTable(table, leftArrowButton, width / 5f);
        UIHelper.addToTable(table, levelImage, width / 5f * 3f);
        UIHelper.addToTable(table, rightArrowButton, width / 5f);
        table.row();
        UIHelper.addToTable(table, levelLabel, width).colspan(3).pad(20);

        float aspectRatio = (float) rope.getRegionWidth() / (float) rope.getRegionHeight();
        float upperDistance = Gdx.graphics.getHeight() * 1.1f - starCell.getActorY();

        ropeWidth = upperDistance;
        ropeHeight = ropeWidth / aspectRatio;

        // Button ClickListeners
        levelImage.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

                screens.push(new PlayScreen(game, levels.get(mapIndex)));
                game.setScreen(screens.peek());
            }
        });

        leftArrowButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

                if (mapIndex > 0) {
                    mapIndex -= 1;
                    updateTable();
                }
            }
        });

        rightArrowButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

                if (mapIndex < levels.size-1) {
                    mapIndex += 1;
                    updateTable();
                }
            }
        });

        updateTable();
    }

    public void updateTable() {
        levelLabel.setText("Level " + (worldIndex + 1) + " - " + (mapIndex + 1));
        levelImage.setDrawable(new TextureRegionDrawable(textureAtlas.findRegion("w" + (worldIndex + 1) + "l" + (mapIndex + 1))));
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void resume() {
        super.resume();
    }

    @Override
    public void hide() {
        super.hide();
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
