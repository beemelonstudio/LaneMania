package com.beemelonstudio.lanemania.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
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
    public Table worldsTable;
    public Table mapsTable;

    public Array<WorldTextButton> worldButtons;

    private TextureRegion leftArrowIcon;
    private TextureRegion rightArrowIcon;

    public MapSelectionScreen(LaneMania game) {
        super(game);
    }

    public MapSelectionScreen(LaneMania game, Array<Array<String>> worlds) {
        super(game);
        this.worlds = worlds;
    }

    @Override
    public void show() {
        super.show();

        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        isBackgroundDrawing = true;
        textureAtlas = (TextureAtlas) Assets.get("general-theme");

        leftArrowIcon = textureAtlas.findRegion("arrow_left");
        rightArrowIcon = textureAtlas.findRegion("arrow_right");

        // Used for debugging
        table.setDebug(true);

        createWorldSelection();

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
    }

    public void createWorldSelection() {

        worldsTable = new Table();
        worldsTable.top().pad(50f);

        mapsTable = new Table();
        mapsTable.bottom().pad(50f);

        table.add(worldsTable);
        table.row();
        table.add(mapsTable);

        BmsImageButton leftArrowButton = new BmsImageButton(skin, leftArrowIcon, "transparent");
        BmsImageButton rightArrowButton = new BmsImageButton(skin, rightArrowIcon, "transparent");
        BmsImageButton leftArrowButton2 = new BmsImageButton(skin, leftArrowIcon, "transparent");
        BmsImageButton rightArrowButton2 = new BmsImageButton(skin, rightArrowIcon, "transparent");

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
            worldsTable.add(leftArrowButton);
            worldsTable.add(worldButton).pad(10f);
            worldsTable.add(rightArrowButton);

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
                mapsTable.add(leftArrowButton2);
                mapsTable.add(mapButton).pad(10f).fillX().expandX();
                mapsTable.add(rightArrowButton2);

                if(j % 3 == 0)
                    mapsTable.row();
            }
        }

        worldButtons.get(0).setMapButtonVisibility(true);
        setAllMapsVisibility(true);
    }

    public void setAllMapsVisibility(boolean visibility) {

        for(WorldTextButton button : worldButtons)
            button.setMapButtonVisibility(visibility);
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
