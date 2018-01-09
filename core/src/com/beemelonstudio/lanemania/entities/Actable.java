package com.beemelonstudio.lanemania.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Stampler on 09.01.2018.
 */

public interface Actable {

    public void act(float delta);
    public void draw(SpriteBatch batch);
}
