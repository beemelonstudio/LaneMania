package com.beemelonstudio.lanemania.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.Array;
import com.beemelonstudio.lanemania.LaneMania;
import com.beemelonstudio.lanemania.entities.Ball;
import com.beemelonstudio.lanemania.entities.Entity;
import com.beemelonstudio.lanemania.entities.EntityType;
import com.beemelonstudio.lanemania.entities.Goal;
import com.beemelonstudio.lanemania.entities.StraightLine;
import com.beemelonstudio.lanemania.screens.ui.PlayScreenUI;
import com.beemelonstudio.lanemania.utils.Assets;
import com.beemelonstudio.lanemania.utils.BodyFactory;
import com.beemelonstudio.lanemania.utils.CustomContactListener;
import com.beemelonstudio.lanemania.utils.CustomInputListener;
import com.beemelonstudio.lanemania.utils.MapAnalyser;
import com.beemelonstudio.lanemania.utils.MapBodyBuilder;
import com.beemelonstudio.lanemania.utils.WorldManager;

/**
 * Created by Jann on 09.01.2018.
 */

public class PlayScreen extends GameScreen {

    private Box2DDebugRenderer debugRenderer;

    private PlayScreenUI playScreenUI;

    public WorldManager worldManager;

    private MapAnalyser mapAnalyser;

    public boolean gravity = false;

    public String mapName;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    public float unitScale;
    public float mapHeightInPixel;

    public EntityType currentType;

    public Ball ball;
    public Goal goal;
    public Array<StraightLine> straightLines;

    public PlayScreen(LaneMania game) {
        super(game);
    }

    public PlayScreen(LaneMania game, String mapName) {
        super(game);
        this.mapName = mapName;

        Gdx.app.log("Map name", mapName);
    }

    @Override
    public void show() {
        super.show();

        debugRenderer = new Box2DDebugRenderer(true,true,false,true,true,true);

        worldManager = new WorldManager();
        worldManager.world.setContactListener(new CustomContactListener(this));

        BodyFactory.initialize(worldManager.world);

        CustomInputListener customInputListener = new CustomInputListener(this);
        Gdx.input.setInputProcessor(new InputMultiplexer(
                stage,
                customInputListener.createInputProcesser(),
                customInputListener.createGestureListener()
        ));

        loadLevel();

        isBackgroundDrawing = true;
        playScreenUI = new PlayScreenUI(this);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        // Acting
        playScreenUI.act(delta);

        if(gravity)
            worldManager.world.step(delta, 10, 8);

        ball.act(delta);
        goal.act(delta);

        for(StraightLine straightLine : straightLines)
            straightLine.act(delta);

        for(Entity entity : mapAnalyser.obstacles)
            entity.act(delta);

        // Drawing
        renderer.setView(camera);
        renderer.render();

        playScreenUI.draw(batch);

        batch.begin();

        // Goal background texture
        batch.draw(goal.backTexture, goal.x - goal.width / 2, goal.y + goal.height / 3.3f, goal.width, goal.height * 0.4f);

        ball.draw(batch);
        goal.draw(batch);

        for(StraightLine straightLine : straightLines)
            straightLine.draw(batch);

        for(Entity entity : mapAnalyser.obstacles)
            entity.draw(batch);

        batch.end();

        debugRenderer.render(worldManager.world, camera.combined);
    }

    private void loadLevel() {

        currentType = EntityType.STRAIGHTLINE;
        straightLines = new Array<StraightLine>();

        map = new TmxMapLoader().load(mapName);
        float mapWidth = (float) map.getProperties().get("width", Integer.class);
        float tileWidth = (float) map.getProperties().get("tilewidth", Integer.class);
        unitScale = 1/(mapWidth * tileWidth);
        renderer = new OrthogonalTiledMapRenderer(map, unitScale);

        float mapHeight = (float) map.getProperties().get("height", Integer.class);
        float tileHeight = (float) map.getProperties().get("tileheight", Integer.class);
        mapHeightInPixel = mapHeight * tileHeight;

        mapAnalyser = new MapAnalyser(map, unitScale);

        ball = new Ball(mapAnalyser.ball);
        goal = new Goal(mapAnalyser.goal);
    }

    public void endLevel() {

        Gdx.app.log("The End", "You did it!");
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
