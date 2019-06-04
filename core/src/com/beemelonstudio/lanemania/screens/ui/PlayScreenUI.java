package com.beemelonstudio.lanemania.screens.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.beemelonstudio.lanemania.entities.objects.JumpLine;
import com.beemelonstudio.lanemania.entities.objects.Line;
import com.beemelonstudio.lanemania.entities.objects.StraightLine;
import com.beemelonstudio.lanemania.entities.types.EntityType;
import com.beemelonstudio.lanemania.entities.types.LineType;
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
    private float numberOfButtons = 7f;

    private TextureRegion[] starsTextures;
    public int amountStars;

    private BmsImageButton playButton;
    private BmsImageButton menuButton;
    private BmsImageButton undoButton;
    private BmsImageButton lineChooserButton;
    private BmsImageButton[] lineButtons;

    public Table endTable;
    private TextButton selectLevelButton;
    private TextButton restartButton;
    private TextureRegionDrawable star;
    private TextureRegionDrawable emptyStar;

    public PlayScreenUI(GameScreen screen) {
        super(screen);

        this.screen = (PlayScreen) screen;

        textureAtlas = (TextureAtlas) Assets.get("general-theme");

        starsTextures = new TextureRegion[4];
        starsTextures[0] = Assets.currentWorldTextureAtlas.findRegion("0stars");
        starsTextures[1] = Assets.currentWorldTextureAtlas.findRegion("1stars");
        starsTextures[2] = Assets.currentWorldTextureAtlas.findRegion("2stars");
        starsTextures[3] = Assets.currentWorldTextureAtlas.findRegion("3stars");
        amountStars = 3;

        height = (Gdx.graphics.getHeight() / 2f) / 6f;
        width = Gdx.graphics.getWidth() / numberOfButtons;

        table.row();

        createButtonRow();
        createPlayButton();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw(PolygonSpriteBatch batch) {
        super.draw(batch);

        //batch.draw(starsTextures[amountStars], screen.viewport.getScreenWidth() / 3, screen.viewport.getScreenHeight() - starsTextures[amountStars].getRegionHeight(), starsTextures[amountStars].getRegionWidth(), starsTextures[amountStars].getRegionHeight());
        batch.draw(starsTextures[amountStars], 0.7f, 1.849f, 0.30f, 0.161f);
    }

    public void createEndTable() {

        endTable = new Table();
        selectLevelButton = new TextButton("Select Level", skin);
        restartButton = new TextButton("Restart", skin);
        emptyStar = new TextureRegionDrawable(textureAtlas.findRegion("star_empty"));
        Image star1 = new Image(emptyStar);
        Image star2 = new Image(emptyStar);
        Image star3 = new Image(emptyStar);
        Label win = new Label("Congratulations!", skin);

        endTable.add(star1);
        endTable.add(star2).center();
        endTable.add(star3);
        endTable.row().pad(10, 0, 10, 0);
        endTable.add(win);
        endTable.row().pad(10, 0, 10, 0);
        endTable.add(selectLevelButton);
        endTable.add(restartButton);

        endTable.setX(Gdx.graphics.getWidth()/2);
        endTable.setY(Gdx.graphics.getHeight()/2);

        selectLevelButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

                screen.game.screens.pop();
                //screen.game.screens.push(new MapSelectionScreen(screen.game, screen.game.levels));
                screen.game.setScreen(screen.game.screens.peek());
            }
        });
    }

    private void createButtonRow() {
        createUndoButton();
        createPlayButton();
        createLineChooserButton();
        createMenuButton();
    }

    private void createUndoButton() {
        // Create and position
        undoButton = new BmsImageButton(skin, textureAtlas.findRegion("line_back"), "transparent");
        undoButton.getStyle().imageDown = new TextureRegionDrawable(textureAtlas.findRegion("line_back_light"));
        undoButton.setHeight(height);
        undoButton.setWidth(width);
        undoButton.setPosition(0, 0);
        stage.addActor(undoButton);

        // Listener
        undoButton.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

                if(!screen.gravity) {
//                    if (screen.straightLines.size > 0) {
//                        int index = screen.straightLines.size-1;
//
//                        if(screen.straightLines.get(index).body != null) {
//                            StraightLine line = screen.straightLines.get(index);
//                            screen.straightLines.removeIndex(index);
//                            screen.straightLinePool.free(line);
//                        }
//                    }
                    if (screen.lines.size > 0) {
                        int index = screen.lines.size-1;

                        if(screen.lines.get(index).body != null) {
                            Line line = screen.lines.get(index);
                            screen.lines.removeIndex(index);
//                            screen.straightLinePool.free(line);
                            if(line instanceof StraightLine)
                                screen.straightLinePool.free((StraightLine) line);
                            if(line instanceof JumpLine)
                                screen.jumpLinePool.free((JumpLine) line);
                        }
                    }
                }
                else {
                    // When the game is playing
                    screen.reset();
                }
            }
        });
    }

    private void createMenuButton() {
        // Create and position
        menuButton = new BmsImageButton(skin, textureAtlas.findRegion("menu_button"), "transparent");
        menuButton.getStyle().imageDown = new TextureRegionDrawable(textureAtlas.findRegion("menu_button_light"));
        menuButton.setHeight(height);
        menuButton.setWidth(width);
        menuButton.setPosition(Gdx.graphics.getWidth() - width, 0);
//        menuButton.setVisible(false);
        stage.addActor(menuButton);

        // Listener
        menuButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

                screen.game.screens.pop();
                screen.game.setScreen(screen.game.screens.peek());
//                screen.currentType = LineType.STRAIGHTLINE;
            }
        });
    }

    private void createPlayButton() {
        // Create and position
        playButton = new BmsImageButton(skin, textureAtlas.findRegion("play_button"), "default");
        playButton.getStyle().imageDown = new TextureRegionDrawable(textureAtlas.findRegion("play_button_light"));
        playButton.setHeight(height);
        playButton.setWidth(width);
        playButton.setPosition(Gdx.graphics.getWidth() / 2f - width, 0);
        stage.addActor(playButton);

        // Listener
        playButton.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

                screen.startLevel();
            }
        });
    }

    private void createLineChooserButton() {
        // Create and position
        lineChooserButton = new BmsImageButton(skin, textureAtlas.findRegion("straightline_icon"), "default");
        lineChooserButton.getStyle().imageDown = new TextureRegionDrawable(textureAtlas.findRegion("straightline_icon"));
        lineChooserButton.setHeight(height);
        lineChooserButton.setWidth(width);
        lineChooserButton.setPosition(Gdx.graphics.getWidth() / 2f, 0);
        stage.addActor(lineChooserButton);

        // Listener
        lineChooserButton.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

                screen.toggleLineType();

                switch (screen.currentType) {
                    case STRAIGHTLINE:
                        lineChooserButton.getStyle().imageUp = new TextureRegionDrawable(textureAtlas.findRegion("straightline_icon"));
                        break;
                    case JUMPLINE:
                        lineChooserButton.getStyle().imageUp = new TextureRegionDrawable(textureAtlas.findRegion("jumpline_icon"));
                        break;
                }
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
