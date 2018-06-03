package com.beemelonstudio.lanemania.screens.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.beemelonstudio.lanemania.entities.objects.StraightLine;
import com.beemelonstudio.lanemania.entities.types.EntityType;
import com.beemelonstudio.lanemania.screens.GameScreen;
import com.beemelonstudio.lanemania.screens.MapSelectionScreen;
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

    private BmsImageButton playButton;
    private BmsImageButton menuButton;
    private BmsImageButton undoButton;
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

        height = (Gdx.graphics.getHeight() / 2f) / 6f;
        width = Gdx.graphics.getWidth() / numberOfButtons;

        table.row();

        createButtonRow();
        //createPlayButton();
        //createBmsImageButtons();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw(PolygonSpriteBatch batch) {
        super.draw(batch);
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
                screen.game.screens.push(new MapSelectionScreen(screen.game, screen.game.mapLoader.worlds));
                screen.game.setScreen(screen.game.screens.peek());
            }
        });
    }

    private void createButtonRow() {

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
                    // When in creative mode
                    /*
                    for (Array.ArrayIterator<Body> iter = new Array.ArrayIterator<Body>(bodies, true); iter.hasNext(); ) {
                        Body body = iter.next();
                        if (body != null && body.getUserData() != null) {
                            EntityType type = asEntityType(body.getUserData().toString());
                            if (type == EntityType.STRAIGHTLINE) {
                                straightLines.add(body);
                            }
                        }
                    }
                    */
                    if (screen.straightLines.size > 0) {
                        int index = screen.straightLines.size-1;

                        if(screen.straightLines.get(index).body != null) {
                            StraightLine line = screen.straightLines.get(index);
                            screen.straightLines.removeIndex(index);
                            screen.straightLinePool.free(line);
                        }
                    }
                }
                else {
                    // When the game is playing
                    screen.customInputListener.reset();
                    screen.gravity = false;
                    screen.ball.reset();
                }
            }
        });

        // Create and position
        menuButton = new BmsImageButton(skin, textureAtlas.findRegion("menu_button"), "transparent");
        menuButton.getStyle().imageDown = new TextureRegionDrawable(textureAtlas.findRegion("menu_button_light"));
        menuButton.setHeight(height);
        menuButton.setWidth(width);
        menuButton.setPosition(Gdx.graphics.getWidth() - width, 0);
        stage.addActor(menuButton);

        // Listener
        menuButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

                screen.game.screens.pop();
                screen.game.setScreen(screen.game.screens.peek());
            }
        });
    }

    private void createPlayButton() {

        // Translate ball bounds to screen coordinates for play button
        Vector3 position = new Vector3(screen.ball.x, screen.ball.y, 0);
        screen.camera.project(position);
        Vector3 sizes = new Vector3(screen.ball.width, screen.ball.height, 0);
        screen.camera.project(sizes);

        // Create and position
        playButton = new BmsImageButton(skin, textureAtlas.findRegion("play_button"), "default");
        playButton.getStyle().imageDown = new TextureRegionDrawable(textureAtlas.findRegion("play_button_light"));
        playButton.setPosition(
                position.x - playButton.getWidth(),
                position.y - playButton.getHeight());
        stage.addActor(playButton);

        Gdx.app.log("pos - size", position.x + "/"+position.y + " - " +sizes.x+"/"+sizes.y);
        Gdx.app.log("Playbutton", playButton.getX() + "/" + playButton.getY());
        //table.add(playButton).height(height).width(width);
        //table.top().left();

        // Listener
        playButton.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

                screen.startLevel();
            }
        });
    }

    private void createBmsImageButtons() {

        // Retrieve images for buttons
        TextureRegion arrow = textureAtlas.findRegion("pickaxe");
        TextureRegion straightLine = textureAtlas.findRegion("straightline");
        TextureRegion curvyLine = textureAtlas.findRegion("circle");

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
