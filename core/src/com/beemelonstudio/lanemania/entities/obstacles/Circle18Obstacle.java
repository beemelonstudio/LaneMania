package com.beemelonstudio.lanemania.entities.obstacles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.beemelonstudio.lanemania.entities.Entity;
import com.beemelonstudio.lanemania.entities.types.EntityType;

/**
 * Created by Jann on 06.03.18.
 */

public class Circle18Obstacle extends Entity {

    public float radius;
    public float rotation;
    public boolean right;
    public float speed;

    public Circle18Obstacle(Body body, float width, boolean right, float speed) {
        super(body);

        this.width = width;
        this.right = right;
        this.speed = speed;

        if(!right)
            speed *= -1f;

        type = EntityType.OBSTACLE;
        textureRegion = textureAtlas.findRegion("circle18");

        calculateSizes();
        body.setUserData(type);
    }

    @Override
    public void act(float delta) {

        x = body.getPosition().x;
        y = body.getPosition().y;

        rotation += speed * delta;
        body.setTransform(body.getPosition(), -(rotation * DEGTORAD));
    }

    @Override
    public void draw(PolygonSpriteBatch batch) {

        batch.draw(textureRegion, x - radius, y - radius, width / 2, height / 2, width, height, 1f, 1f, -rotation);
    }

    /**
     * Retrieve PolygonShape vertices and calculate width and height
     */
    private void calculateSizes(){

        radius = width / 2;
        width = radius * 2;
        height = radius * 2;

        rotation = body.getAngle() * RADTODEG;
    }
}
