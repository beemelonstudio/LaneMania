package com.beemelonstudio.lanemania.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;
import com.beemelonstudio.lanemania.LaneMania;
import com.beemelonstudio.lanemania.entities.Entity;
import com.beemelonstudio.lanemania.entities.objects.Ball;
import com.beemelonstudio.lanemania.entities.objects.Goal;
import com.beemelonstudio.lanemania.entities.objects.StraightLine;
import com.beemelonstudio.lanemania.entities.types.EntityType;
import com.beemelonstudio.lanemania.screens.ui.PlayScreenUI;
import com.beemelonstudio.lanemania.utils.WorldManager;
import com.beemelonstudio.lanemania.utils.assets.Assets;
import com.beemelonstudio.lanemania.utils.factories.BodyFactory;
import com.beemelonstudio.lanemania.utils.listeners.CustomContactListener;
import com.beemelonstudio.lanemania.utils.listeners.CustomInputListener;
import com.beemelonstudio.lanemania.utils.mapeditor.MapAnalyser;

/**
 * Created by Jann on 09.01.2018.
 */

public class PlayScreen extends GameScreen {

    private Box2DDebugRenderer debugRenderer;

    private PlayScreenUI playScreenUI;
    public WorldManager worldManager;
    private MapAnalyser mapAnalyser;

    public boolean gravity = false;
    public CustomInputListener customInputListener;

    public String mapName;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    public float unitScale;
    public float mapHeightInPixel;

    public EntityType currentType;

    public Ball ball;
    public Goal goal;
    public Array<StraightLine> straightLines;
    public Pool<StraightLine> straightLinePool = Pools.get(StraightLine.class);
    public Array<Body> toBeDeleted;

    private Array<ParticleEffect> particleEffects;

    public int maxStraightLines;

    public PlayScreen(LaneMania game) {
        super(game);
    }

    public PlayScreen(LaneMania game, String mapName) {
        super(game);
        this.mapName = mapName;
    }

    @Override
    public void show() {
        super.show();

        toBeDeleted = new Array<Body>();
        debugRenderer = new Box2DDebugRenderer(true,true,false,true,true,true);

        worldManager = new WorldManager();
        worldManager.world.setContactListener(new CustomContactListener(this));

        BodyFactory.initialize(worldManager.world);

        customInputListener = new CustomInputListener(this);
        Gdx.input.setInputProcessor(new InputMultiplexer(
                stage,
                customInputListener.createInputProcessor(),
                customInputListener.createGestureListener()
        ));

        setupTextures();
        loadLevel();
        playScreenUI = new PlayScreenUI(this);

        particleEffects = new Array<ParticleEffect>();
        TextureAtlas atlas = (TextureAtlas) Assets.get("general-theme");

        ParticleEffect effect1 = new ParticleEffect();
        effect1.load(Gdx.files.internal("particles/dust1.p"), atlas);
        effect1.setPosition(0.5f, 1f);
        effect1.scaleEffect(0.005f);
        effect1.start();
        particleEffects.add(effect1);

        ParticleEffect effect2 = new ParticleEffect();
        effect2.load(Gdx.files.internal("particles/finish1.p"), atlas);
        effect2.setPosition(0.5f, 1.5f);
        effect2.scaleEffect(0.005f);
        effect2.start();
        particleEffects.add(effect2);

        ParticleEffect effect3 = new ParticleEffect();
        effect3.load(Gdx.files.internal("particles/finish2.p"), atlas);
        effect3.setPosition(0.5f, 1.2f);
        effect3.scaleEffect(0.005f);
        effect3.start();
        particleEffects.add(effect3);
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
        goal.drawBackground(batch);

        ball.draw(batch);
        goal.draw(batch);

        for(StraightLine straightLine : straightLines)
            straightLine.draw(batch);

        for(Entity entity : mapAnalyser.obstacles)
            entity.draw(batch);

        for (ParticleEffect effect : particleEffects)
            effect.draw(batch, delta);

        batch.end();

        debugRenderer.render(worldManager.world, camera.combined);
    }

    private void loadLevel() {

        currentType = EntityType.STRAIGHTLINE;
        maxStraightLines = 3; //TODO: Extract this info from level file
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

    private void setupTextures() {

        if(mapName.contains("world1")) Assets.currentWorldTextureAtlas = (TextureAtlas) Assets.get("wildwest-theme");
        else Assets.currentWorldTextureAtlas = (TextureAtlas) Assets.get("orange-theme");

        backgroundTexture = Assets.currentWorldTextureAtlas.findRegion("background");
        backgroundTile = new TiledDrawable(backgroundTexture);

        isBackgroundDrawing = true;
    }

    public void startLevel() {

        gravity = true;
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
