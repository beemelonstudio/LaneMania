package com.beemelonstudio.lanemania.entities.obstacles;

import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.beemelonstudio.lanemania.entities.Entity;
import com.beemelonstudio.lanemania.entities.types.EntityType;

/**
 * Created by Jann on 06.03.18.
 */

public class ArrowObstacle extends Entity {

    public ArrowObstacle(Body body, String name, float width, float speed, float rotationSpeed, boolean circle, float timer) {
        super(body);

        this.name = name;
        this.width = width;
        this.speed = speed / 100;
        this.rotationSpeed = rotationSpeed;
        this.circle = circle;
        this.timer = timer;

        type = EntityType.OBSTACLE;
        textureRegion = textureAtlas.findRegion("arrow");

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

        batch.draw(textureRegion, x - width / 2, y - height / 2, width / 2, height / 2, width, height, 1f, 1f, -rotation);
    }

    /**
     * Retrieve PolygonShape vertices and calculate width and height
     */
    private void calculateSizes(){

        float ratio = (float) textureRegion.getRegionHeight() / (float) textureRegion.getRegionWidth();
        height = width * ratio;

        rotation = body.getAngle() * RADTODEG;
        rotation = body.getAngle() * RADTODEG;
    }
}
