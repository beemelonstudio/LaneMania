package com.beemelonstudio.lanemania.entities.obstacles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.beemelonstudio.lanemania.entities.Entity;

/**
 * Created by Jann on 06.03.18.
 */

public class PickaxeObstacle extends Entity {

    public float rotation;

    public PickaxeObstacle(Body body, float width) {
        super(body);
        this.width = width;

        textureRegion = textureAtlas.findRegion("pickaxe");

        calculateSizes();
    }

    @Override
    public void act(float delta) {

        x = body.getPosition().x;
        y = body.getPosition().y;
    }

    @Override
    public void draw(PolygonSpriteBatch batch) {

        batch.draw(textureRegion, x - width / 2, y - height / 2, width / 2, height / 2, width, height, 1f, 1f, rotation);
    }

    /**
     * Retrieve PolygonShape vertices and calculate width and height
     */
    private void calculateSizes(){

        float ratio = (float) textureRegion.getRegionHeight() / (float) textureRegion.getRegionWidth();
        height = width * ratio;

        rotation = body.getAngle() * RADTODEG;
    }
}
