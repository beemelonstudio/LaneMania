package com.beemelonstudio.lanemania.utils.listeners;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.QueryCallback;
import com.badlogic.gdx.utils.Array;
import com.beemelonstudio.lanemania.entities.Entity;
import com.beemelonstudio.lanemania.entities.objects.StraightLine;
import com.beemelonstudio.lanemania.entities.types.EntityType;
import com.beemelonstudio.lanemania.screens.PlayScreen;

import java.util.ArrayList;

import static com.badlogic.gdx.graphics.g2d.ParticleEmitter.SpawnShape.point;

/**
 * Created by Jann on 09.01.2018.
 */
public class CustomInputListener implements GestureDetector.GestureListener, InputProcessor {

    private PlayScreen screen;

    private Vector3 coordinates;
    private StraightLine straightStraightLine;
    private boolean ballWasHit;
    private boolean drawing;

    public CustomInputListener(PlayScreen screen) {

        this.screen = screen;
    }

    public InputProcessor createInputProcessor() {
        return this;
    }

    public GestureDetector createGestureListener() {
        return new GestureDetector(this);
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }

    @Override
    public void pinchStop() {    }

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
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        coordinates = new Vector3(screenX, screenY, 0);
        screen.camera.unproject(coordinates);

        // Testing if ball was touched
        if(coordinates.x > screen.ball.x - screen.ball.width &&
           coordinates.x < screen.ball.x + screen.ball.width &&
           coordinates.y > screen.ball.y - screen.ball.height &&
           coordinates.y < screen.ball.y + screen.ball.height)
            ballWasHit = true;

        if(ballWasHit) {
            screen.startLevel();
        }
        else {

            switch (screen.currentType) {

                case STRAIGHTLINE:
                    //if (screen.straightLines.size < screen.maxStraightLines) {
                        straightStraightLine = screen.straightLinePool.obtain();
                        straightStraightLine.init(coordinates.x, coordinates.y);
                        screen.straightLines.add(straightStraightLine);
                        drawing = true;
                    //}
                    break;

                default:
                    break;
            }
        }

        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {

        coordinates.set(screenX, screenY, 0);
        screen.camera.unproject(coordinates);

        switch (screen.currentType) {

            case STRAIGHTLINE:
                if(drawing) {
                    if (straightStraightLine.width > 0.05f) {
                        straightStraightLine.build();
                    } else {
                        screen.straightLines.removeValue(straightStraightLine, true);
                        screen.straightLinePool.free(straightStraightLine);
                    }
                    drawing = false;
                }
                break;

            default: break;
        }

        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {

        coordinates.set(screenX, screenY, 0);
        screen.camera.unproject(coordinates);

        switch (screen.currentType) {

            case STRAIGHTLINE:
                if(drawing) {
                    straightStraightLine.setEnd(coordinates.x, coordinates.y);
                    if(straightStraightLine.width > 0.49f) {
                        float x = (float) (straightStraightLine.start.x + 0.5f * Math.cos(straightStraightLine.angle));
                        float y = (float) (straightStraightLine.start.y + 0.5f * Math.sin(straightStraightLine.angle));

                        straightStraightLine.setEnd(x, y);
                    }
                    else
                        straightStraightLine.setEnd(coordinates.x, coordinates.y);
                }
                break;

            default: break;
        }

        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    public void reset() {
        ballWasHit = false;
    }

    /*
    Callback function for getting a touched body.
    Source: https://stackoverflow.com/questions/31764270/libgdx-check-if-body-is-touch
     */
    QueryCallback callback = new QueryCallback() {
        @Override
        public boolean reportFixture (Fixture fixture) {
            if (fixture.testPoint(coordinates.x, coordinates.y)) {

                if(fixture.getBody().getUserData() != null) {
                    EntityType type = (EntityType) fixture.getBody().getUserData();

                    if (type == EntityType.BALL)
                        ballWasHit = true;
                }

                return false;
            } else
                return true;
        }
    };
}
