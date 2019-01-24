package com.beemelonstudio.lanemania.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.beemelonstudio.lanemania.LaneMania;
import com.beemelonstudio.lanemania.screens.custombuttons.BmsImageButton;
import com.beemelonstudio.lanemania.screens.custombuttons.WorldTextButton;
import com.beemelonstudio.lanemania.utils.assets.Assets;

/**
 * Created by Jann on 27.01.18.
 */

public class MapSelectionScreen extends GameScreen {

    public Array<Array<String>> worlds;

    public Table table;
    public HorizontalGroup worldsGroup;
    public HorizontalGroup mapsGroup;
    public BmsImageButton leftArrowButton;
    public BmsImageButton rightArrowButton;
    public BmsImageButton leftArrowButton2;
    public BmsImageButton rightArrowButton2;

    public Array<WorldTextButton> worldButtons;

    private Actor currentWorld;
    private Actor currentMap;

    private TextureRegion leftArrowIconUp;
    private TextureRegion leftArrowIconDown;
    private TextureRegion rightArrowIconUp;
    private TextureRegion rightArrowIconDown;
    private int worldIndex = 0;
    private int mapIndex = 0;

    private float height, width;
    private float numberOfButtons = 3f;

    public MapSelectionScreen(LaneMania game) {
        super(game);
    }

    public MapSelectionScreen(LaneMania game, Array<Array<String>> worlds) {
        super(game);
        this.worlds = worlds;

        height = (Gdx.graphics.getHeight() / 2f) / 2f;
        width = Gdx.graphics.getWidth() / numberOfButtons;

        isBackgroundDrawing = true;
        textureAtlas = (TextureAtlas) Assets.get("general-theme");

        rightArrowIconUp = new TextureRegion(textureAtlas.findRegion("play_button_on"));
        rightArrowIconDown = new TextureRegion(textureAtlas.findRegion("play_button_off"));

        leftArrowIconUp = new TextureRegion(textureAtlas.findRegion("play_button_on"));
        leftArrowIconUp.flip(true, false);
        leftArrowIconDown = new TextureRegion(textureAtlas.findRegion("play_button_off"));
        leftArrowIconDown.flip(true, false);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
        super.show();

        table = new Table();

        stage.addActor(table);

        // Used for debugging
        table.setDebug(true);

        createWorldSelection();

        table.setFillParent(true);

        table.setTransform(true);
        table.setScale(1.5f);
        table.setOrigin(Align.center);
        table.setPosition(-Gdx.graphics.getWidth()/4f, -Gdx.graphics.getHeight()/4f);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        postDraw(delta);
    }

    public void createWorldSelection() {

        worldsGroup = new HorizontalGroup();
        mapsGroup = new HorizontalGroup();

        table.add(worldsGroup).pad(20f);
        table.row();
        table.add(mapsGroup).pad(20f);
        table.row();

        leftArrowButton = new BmsImageButton(skin, leftArrowIconUp, "transparent");
        leftArrowButton.getStyle().imageDown = new TextureRegionDrawable(leftArrowIconDown);

        rightArrowButton = new BmsImageButton(skin, rightArrowIconUp, "transparent");
        rightArrowButton.getStyle().imageDown = new TextureRegionDrawable(rightArrowIconDown);

        leftArrowButton2 = new BmsImageButton(skin, leftArrowIconUp, "transparent");
        leftArrowButton2.getStyle().imageDown = new TextureRegionDrawable(leftArrowIconDown);

        rightArrowButton2 = new BmsImageButton(skin, rightArrowIconUp, "transparent");
        rightArrowButton2.getStyle().imageDown = new TextureRegionDrawable(rightArrowIconDown);

        worldButtons = new Array<WorldTextButton>();

        for(int i = 0; i < worlds.size; i++) {

            final WorldTextButton worldButton = new WorldTextButton("World " + (i+1), skin);
            worldButton.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);

                    setAllMapsVisibility(false);
                    worldButton.setMapButtonVisibility(true);
                }
            });

            worldButtons.add(worldButton);

            for(int j = 0; j < worlds.get(i).size; j++) {

                final String map = worlds.get(i).get(j);

                TextButton mapButton = new TextButton(map, skin);
                mapButton.addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        super.clicked(event, x, y);

                        screens.push(new PlayScreen(game, map));
                        game.setScreen(screens.peek());
                    }
                });

                worldButton.addMapButton(mapButton);
            }
        }

        //updateTable();
        /*
        worldsGroup.add(leftArrowButton).width(width).height(height);
        worldsGroup.add(worldButtons.get(worldIndex)).pad(1f);
        worldsGroup.add(rightArrowButton).width(width).height(height);

        mapsGroup.add(leftArrowButton2).width(width).height(height);
        mapsGroup.add(worldButtons.get(worldIndex).mapButtons.get(mapIndex)).pad(1f).expand().fill().grow();//.fillX().expandX();
        mapsGroup.add(rightArrowButton2).width(width).height(height);*/

        leftArrowButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

                if (worldIndex > 0) {
                    worldIndex -= 1;
                    mapIndex = 0;
                    updateTable();
                }
            }
        });

        rightArrowButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

                if (worldIndex < worldButtons.size-1) {
                    worldIndex += 1;
                    mapIndex = 0;
                    updateTable();
                }
            }
        });

        leftArrowButton2.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

                if (mapIndex > 0) {
                    mapIndex -= 1;
                    updateTable();
                }
            }
        });

        rightArrowButton2.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

                if (mapIndex < worldButtons.get(worldIndex).mapButtons.size-1) {
                    mapIndex += 1;
                    updateTable();
                }
            }
        });

        worldButtons.get(0).setMapButtonVisibility(true);
        setAllMapsVisibility(true);

        TextButton returnButton = new TextButton("Return", skin);
        returnButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

                screens.pop();
                game.setScreen(screens.peek());
            }
        });
        table.add(returnButton).width(width/1.5f).pad(20f);

        currentWorld = worldButtons.get(worldIndex);
        currentMap = worldButtons.get(worldIndex).mapButtons.get(mapIndex);

        worldsGroup.addActor(leftArrowButton);
        worldsGroup.grow();//.width(width).height(height).center();
        worldsGroup.addActor(worldButtons.get(worldIndex));
        worldsGroup.setWidth(width);
        worldsGroup.addActor(rightArrowButton);
        worldsGroup.grow();//.width(width).height(height).center();

        mapsGroup.addActor(leftArrowButton2);
        mapsGroup.grow();
        mapsGroup.addActor(worldButtons.get(worldIndex).mapButtons.get(mapIndex));
        mapsGroup.setWidth(width);//.pad(1f);//.expandX().center();
        mapsGroup.addActor(rightArrowButton2);
        mapsGroup.grow();
    }

    public void setAllMapsVisibility(boolean visibility) {

        for(WorldTextButton button : worldButtons)
            button.setMapButtonVisibility(visibility);
    }

    public void updateTable() {
        table.swapActor(currentWorld, worldButtons.get(worldIndex));
        currentWorld = worldButtons.get(worldIndex);

        table.swapActor(currentMap, worldButtons.get(worldIndex));
        currentMap = worldButtons.get(worldIndex).mapButtons.get(mapIndex);
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
