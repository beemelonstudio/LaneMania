package com.beemelonstudio.lanemania;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.beemelonstudio.lanemania.screens.GameScreen;
import com.beemelonstudio.lanemania.utils.assets.Assets;
import com.kotcrab.vis.ui.VisUI;

/**
 * Created by Jann on 30.07.18.
 */

public class ViewportTest extends GameScreen {

    Array<Viewport> viewports;
    Array<String> names;
    Label label;

    public ViewportTest(LaneMania game) {
        super(game);
    }

    public void show () {
        //stage = new Stage();
        //Skin skin = (Skin) Assets.get("beemelonSkin");

        label = new Label("", skin);

        Table root = new Table(skin);
        root.setFillParent(true);
        root.debug().defaults().space(6);
        root.add(new TextButton("Button 1", skin));
        root.add(new TextButton("Button 2", skin)).row();
        root.add("Press spacebar to change the viewport:").colspan(2).row();
        root.add(label).colspan(2);
        stage.addActor(root);

        viewports = getViewports(camera);
        names = getViewportNames();

        stage.setViewport(viewports.first());
        label.setText(names.first());

        Gdx.input.setInputProcessor(new InputMultiplexer(new InputAdapter() {
            public boolean keyDown (int keycode) {
                if (keycode == Input.Keys.SPACE) {
                    int index = (viewports.indexOf(stage.getViewport(), true) + 1) % viewports.size;
                    label.setText(names.get(index));
                    Viewport viewport = viewports.get(index);
                    stage.setViewport(viewport);
                    resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
                }
                return false;
            }
        }, stage));
    }

    public void render (float delta) {
        stage.act();

        Gdx.gl.glClearColor(255, 255, 255, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
    }

    public void resize (int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    public void dispose () {
        stage.dispose();
    }

    static public Array<String> getViewportNames () {
        Array<String> names = new Array();
        names.add("StretchViewport");
        names.add("FillViewport");
        names.add("FitViewport");
        names.add("ExtendViewport: no max");
        names.add("ExtendViewport: max");
        names.add("ScreenViewport: 1:1");
        names.add("ScreenViewport: 0.75:1");
        names.add("ScalingViewport: none");
        names.add("LaneManiaExtendViewport");
        return names;
    }

    static public Array<Viewport> getViewports (Camera camera) {
        int minWorldWidth = 640;
        int minWorldHeight = 480;
        int maxWorldWidth = 800;
        int maxWorldHeight = 480;

        Array<Viewport> viewports = new Array();
        viewports.add(new StretchViewport(minWorldWidth, minWorldHeight, camera));
        viewports.add(new FillViewport(minWorldWidth, minWorldHeight, camera));
        viewports.add(new FitViewport(minWorldWidth, minWorldHeight, camera));
        viewports.add(new ExtendViewport(minWorldWidth, minWorldHeight, camera));
        viewports.add(new ExtendViewport(minWorldWidth, minWorldHeight, maxWorldWidth, maxWorldHeight, camera));
        viewports.add(new ScreenViewport(camera));

        ScreenViewport screenViewport = new ScreenViewport(camera);
        screenViewport.setUnitsPerPixel(0.75f);
        viewports.add(screenViewport);

        viewports.add(new ScalingViewport(Scaling.none, minWorldWidth, minWorldHeight, camera));

        viewports.add(new ExtendViewport(640, 480, new OrthographicCamera()));
        return viewports;
    }
}
