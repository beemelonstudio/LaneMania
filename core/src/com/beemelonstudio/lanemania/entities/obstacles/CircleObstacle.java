package com.beemelonstudio.lanemania.entities.obstacles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.beemelonstudio.lanemania.entities.Entity;
import com.beemelonstudio.lanemania.entities.types.EntityType;

/**
 * Created by Jann on 07.02.18.
 */

public class CircleObstacle extends Entity {

    float radius;

    public CircleObstacle(Body body, String name, float speed, float rotationSpeed, boolean circle, float timer) {
        super(body);

        this.name = name;
        this.speed = speed / 100;
        this.rotationSpeed = rotationSpeed;
        this.circle = circle;
        this.timer = timer;

        type = EntityType.OBSTACLE;
        textureRegion = textureAtlas.findRegion("circle");

        calculateSizes();
        body.setUserData(type);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if(rotationSpeed != 0) {
            rotation += rotationSpeed * delta;
            body.setTransform(body.getPosition(), -(rotation * DEGTORAD));
        }
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

        rotation = body.getAngle() * RADTODEG;
        body.setTransform(body.getPosition(), -(rotation * DEGTORAD));
    }
}
