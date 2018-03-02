package com.beemelonstudio.lanemania.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.beemelonstudio.lanemania.LaneMania;
import com.beemelonstudio.lanemania.utils.Assets;

import java.util.Stack;

/**
 * Created by Jann on 9.01.2018.
 */

public class GameScreen implements Screen, InputProcessor {

    public LaneMania game;
    protected Stack<GameScreen> screens;

    protected SpriteBatch batch;
    public OrthographicCamera camera;
    protected Viewport viewport, backgroundViewport;

    protected Stage stage;
    protected Skin skin;

    protected TextureAtlas textureAtlas;
    protected TextureRegion backgroundTexture;
    protected TiledDrawable backgroundTile;

    private boolean shown = false;
    private boolean backButtonLocked = false;
    protected boolean isBackgroundDrawing = false;

    protected float volume = 1.0f;
    protected Boolean muted;
    protected Music backgroundMusic;



    public GameScreen(LaneMania game) {
        this.game = game;
        this.screens = game.screens;
        this.batch = game.batch;
        this.camera = game.camera;
        this.viewport = game.viewport;
        this.backgroundViewport = game.backgroundViewport;
        this.stage = game.stage;
        this.skin = game.skin;
        this.volume = game.volume;
        this.muted = game.muted;
        this.backgroundMusic = game.backgroundMusic;

        textureAtlas = (TextureAtlas) Assets.get("orange-theme");
        backgroundTexture = textureAtlas.findRegion("background");
        backgroundTile = new TiledDrawable(backgroundTexture);

        ImageButton muteButton = new ImageButton(skin);
        muteButton.setPosition(Gdx.graphics.getWidth()-muteButton.getWidth(), Gdx.graphics.getHeight()-muteButton.getHeight());
        stage.addActor(muteButton);
        muteButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

                if (muted == false){
                    volume = 0.0f;
                    backgroundMusic.setVolume(volume);
                    muted = true;
                } else {
                    volume = 1.0f;
                    backgroundMusic.setVolume(volume);
                    muted = false;
                }

            }
        });

        Gdx.input.setInputProcessor(this);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public void show() {
        stage.clear();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(255, 255, 255, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(isBackgroundDrawing) {
            backgroundViewport.apply();
            batch.setProjectionMatrix(backgroundViewport.getCamera().combined);
            batch.begin();
            backgroundTile.draw(batch, 0, 0, backgroundViewport.getScreenWidth(), backgroundViewport.getScreenHeight());
            batch.end();
        }

        stage.act(delta);
        stage.draw();

        // Set GL Viewport
        Gdx.gl.glViewport(viewport.getScreenX(), viewport.getScreenY(), viewport.getScreenWidth(), viewport.getScreenHeight());

        // Catch Androids native back button
        handleBackButton();

        camera.position.set(camera.viewportWidth/2, camera.viewportHeight/2, 0);

        camera.update();
        batch.setProjectionMatrix(camera.combined);
    }

    @Override
    public void resize(int width, int height) {

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

    @Override
    public void dispose() {
        // TODO: Do I dispose this?
        batch.dispose();
        stage.dispose();
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    private void handleBackButton(){

        if(!Gdx.input.isKeyJustPressed(Input.Keys.BACK)) {
            backButtonLocked = false;
            return;
        }

        if(backButtonLocked)
            return;

        Gdx.app.log("size", ""+screens.size());

        if(screens.size() > 1){

            screens.pop();
            game.setScreen(screens.peek());
        }
        /* TODO: Pixthulu Skin doesn't have Dialog Skin
        else {

            // Only show the dialog once;
            if(shown)
                return;

            shown = true;

            // Create "Are you sure" dialog
            final Dialog dialog = new Dialog("", skin, "dialog"){
                @Override
                protected void result(Object object) {
                    super.result(object);
                    if( (Boolean) object ){
                        shown = false;
                        Gdx.app.exit();
                    }
                    else {
                        shown = false;
                        hide();
                    }
                }
            };

            dialog.setBounds( camera.viewportWidth / 10, camera.viewportHeight / 2, camera.viewportWidth / 10 * 8, camera.viewportHeight / 10 * 4);
            dialog.text("Are you sure you want to quit?");
            dialog.button("Yes", true);
            dialog.button("No", false);

            dialog.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    if( x < dialog.getX() || x > dialog.getX() + dialog.getWidth()
                            || y < dialog.getY() || y > dialog.getY() + dialog.getHeight() ) {
                        shown = false;
                        dialog.hide();
                    }
                }
            });
            stage.addActor(dialog);
        }
        */
        backButtonLocked = true;
    }
}
