package com.beemelonstudio.lanemania.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;
import com.beemelonstudio.lanemania.LaneMania;
import com.beemelonstudio.lanemania.entities.Entity;
import com.beemelonstudio.lanemania.entities.objects.Ball;
import com.beemelonstudio.lanemania.entities.objects.Goal;
import com.beemelonstudio.lanemania.entities.objects.JumpLine;
import com.beemelonstudio.lanemania.entities.objects.Line;
import com.beemelonstudio.lanemania.entities.objects.StraightLine;
import com.beemelonstudio.lanemania.entities.types.EntityType;
import com.beemelonstudio.lanemania.entities.types.LineType;
import com.beemelonstudio.lanemania.screens.ui.PlayScreenUI;
import com.beemelonstudio.lanemania.utils.WorldManager;
import com.beemelonstudio.lanemania.utils.assets.Assets;
import com.beemelonstudio.lanemania.utils.factories.BodyFactory;
import com.beemelonstudio.lanemania.utils.listeners.CustomContactListener;
import com.beemelonstudio.lanemania.utils.listeners.CustomInputListener;
import com.beemelonstudio.lanemania.utils.mapeditor.Level;

/**
 * Created by Jann on 09.01.2018.
 */

public class PlayScreen extends GameScreen {

    private Box2DDebugRenderer debugRenderer;

    private boolean isFinished = false;
    private PlayScreenUI playScreenUI;
    public WorldManager worldManager;

    public boolean gravity = false;
    public CustomInputListener customInputListener;

    public Level level;
    private OrthogonalTiledMapRenderer renderer;

    public LineType currentType;

    public Ball ball;
    public Goal goal;
    public Array<Line> lines;
    public Array<StraightLine> straightLines;
    public Array<JumpLine> jumpLines;
    public Pool<StraightLine> straightLinePool = Pools.get(StraightLine.class);
    public Pool<JumpLine> jumpLinePool = Pools.get(JumpLine.class);
    public Array<Body> toBeDeleted;

    public PlayScreen(LaneMania game, Level level) {
        super(game);
        this.level = level;
    }

    @Override
    public void show() {
        super.show();

        toBeDeleted = new Array<>();
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
//        update(Gdx.graphics.getDeltaTime());
    }

    private void update(float delta) {
        // Acting
        playScreenUI.act(delta);

        if(gravity)
            ball.body.setType(BodyDef.BodyType.DynamicBody);

        worldManager.world.step(delta, 10, 8);

        //ball behaviour on screen coordinates
        ball.act(delta);
        ball.screenPosition.set(camera.project(ball.worldPosition));
        if(!ball.checkOnScreen())
            reset();

        goal.act(delta);

        for(StraightLine straightLine : straightLines)
            straightLine.act(delta);

        for(JumpLine jumpLine : jumpLines)
            jumpLine.act(delta);

        for(Line line : lines)
            line.act(delta);

        for(Entity entity : level.mapAnalyser.obstacles)
            entity.act(delta);

        calculateStars();
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        update(delta);

        batch.begin();

        // Goal background texture
        goal.drawBackground(batch);

        ball.draw(batch);
        goal.draw(batch);

        for(Entity entity : level.mapAnalyser.obstacles)
            entity.draw(batch);

        for(StraightLine straightLine : straightLines)
            straightLine.draw(batch);

        for(JumpLine jumpLine : jumpLines)
            jumpLine.draw(batch);

        for (Line line : lines)
            line.draw(batch);

        playScreenUI.draw(batch);

        batch.end();

//        debugRenderer.render(worldManager.world, camera.combined);

        postDraw(delta);
    }

    private void loadLevel() {

        currentType = LineType.STRAIGHTLINE;
        lines = new Array<>();
        straightLines = new Array<>();
        jumpLines = new Array<>();

        level.setup();

        ball = new Ball(level.mapAnalyser.ball);
        goal = new Goal(level.mapAnalyser.goal);
    }

    private void setupTextures() {

        if(level.worldName.contains("Wild-West") || level.worldName.contains("world2")) Assets.currentWorldTextureAtlas = (TextureAtlas) Assets.get("wildwest-theme");
        else Assets.currentWorldTextureAtlas = (TextureAtlas) Assets.get("wildwest-theme");

        backgroundTexture = Assets.currentWorldTextureAtlas.findRegion("background");
        backgroundTile = new TiledDrawable(backgroundTexture);

        isBackgroundDrawing = true;
    }

    public void startLevel() {

        gravity = true;
    }

    public void endLevel() {

        if(!isFinished) {
            Gdx.app.log("The End", "You did it!");
            level.saveStars(playScreenUI.amountStars);
            if (playScreenUI.endTable == null) {
                //playScreenUI.createEndTable();
                //stage.addActor(playScreenUI.endTable);
                //playScreenUI.endTable.toFront();
            }
            isFinished = true;
        }
    }

    public void reset() {

        ball.reset();
        customInputListener.reset();
        gravity = false;
    }

    private void calculateStars() {
        int numberOfStars = straightLines.size + jumpLines.size;

        if(numberOfStars <= level.star3)
            playScreenUI.amountStars = 3;
        else if(numberOfStars <= level.star2)
            playScreenUI.amountStars = 2;
        else if(numberOfStars <= level.star1)
            playScreenUI.amountStars = 1;
        else
            playScreenUI.amountStars = 0;
    }

    public void toggleLineType() {
        currentType = (currentType == LineType.STRAIGHTLINE) ? LineType.JUMPLINE : LineType.STRAIGHTLINE; //TODO: Change needed for additional LineTypes
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
