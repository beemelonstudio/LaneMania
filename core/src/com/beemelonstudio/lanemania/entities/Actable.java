package com.beemelonstudio.lanemania.entities;

import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;

/**
 * Created by Jann on 09.01.2018.
 */

public interface Actable {

    public void act(float delta);
    public void draw(PolygonSpriteBatch batch);
}
