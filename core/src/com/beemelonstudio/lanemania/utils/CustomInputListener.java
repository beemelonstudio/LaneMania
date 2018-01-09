package com.beemelonstudio.lanemania.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.beemelonstudio.lanemania.entities.Line;
import com.beemelonstudio.lanemania.entities.PolyLine;
import com.beemelonstudio.lanemania.screens.PlayScreen;

/**
 * Created by Stampler on 09.01.2018.
 */
public class CustomInputListener implements GestureDetector.GestureListener, InputProcessor {

    private PlayScreen screen;

    private Vector3 coordinates;
    private PolyLine polyLine;
    private Line straightLine;

    private float dx = 0f;
    private float dy = 0f;
    private float distance = 0f;

    public CustomInputListener(PlayScreen screen) {

        this.screen = screen;
    }

    public InputProcessor createInputProcesser() {
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

        switch (screen.currentType) {

            case STRAIGHTLINE:
                straightLine = new Line(coordinates.x, coordinates.y);
                screen.straightLines.add(straightLine);
                break;

            case POLYGONLINE:
                polyLine = new PolyLine(coordinates.x, coordinates.y);
                screen.polyLines.add(polyLine);
                break;

            default: break;
        }

        Gdx.app.log("Touch down at", coordinates.x + " - " + coordinates.y);

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {

        coordinates.set(screenX, screenY, 0);
        screen.camera.unproject(coordinates);

        switch (screen.currentType) {

            case STRAIGHTLINE:
                straightLine.setEnd(coordinates.x, coordinates.y);
                straightLine.build();
                break;

            case POLYGONLINE:
                polyLine.insert(coordinates.x, coordinates.y);
                polyLine.build();
                break;

            default: break;
        }

        Gdx.app.log("Touch up at", coordinates.x + " - " + coordinates.y);

        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {

        coordinates.set(screenX, screenY, 0);
        screen.camera.unproject(coordinates);

        switch (screen.currentType) {

            case STRAIGHTLINE:
                straightLine.setEnd(coordinates.x, coordinates.y);
                break;

            case POLYGONLINE:
                dx = coordinates.x - polyLine.vertices.get(polyLine.vertices.size-2);
                dy = coordinates.y - polyLine.vertices.get(polyLine.vertices.size-1);
                distance = (float) Math.sqrt(dx*dx + dy*dy);

                // Only process points more than 1 mm distance to the last point
                if(distance > 0.05f)
                    polyLine.insert(coordinates.x, coordinates.y);
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
}
