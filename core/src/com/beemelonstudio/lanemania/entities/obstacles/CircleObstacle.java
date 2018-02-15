package com.beemelonstudio.lanemania.entities.obstacles;

import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.beemelonstudio.lanemania.entities.Entity;

/**
 * Created by Jann on 07.02.18.
 */

public class CircleObstacle extends Entity {

    float radius;

    public CircleObstacle(Body body) {
        super(body);

        textureRegion = textureAtlas.findRegion("swiperadius");

        calculateSizes();
    }

    @Override
    public void act(float delta) {

        x = body.getPosition().x;
        y = body.getPosition().y;
    }

    @Override
    public void draw(PolygonSpriteBatch batch) {

        batch.draw(textureRegion, x - radius, y - radius, width, height);
    }

    /**
     * Retrieve PolygonShape vertices and calculate width and height
     */
    private void calculateSizes(){

        CircleShape shape = (CircleShape) body.getFixtureList().get(0).getShape();

        radius = shape.getRadius();
        width = radius * 2;
        height = radius * 2;
    }
}
