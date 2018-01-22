package com.beemelonstudio.lanemania.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.CircleShape;

/**
 * Created by Jann on 09.01.2018.
 */

public class Ball extends Entity {

    private float radius;
    private Vector2 origin;

    public Ball() {
        super();
    }

    public Ball(Body body) {
        super(body);

        type = EntityType.BALL;

        textureRegion = textureAtlas.findRegion("ball");

        origin = new Vector2(body.getPosition());

        CircleShape shape = (CircleShape) body.getFixtureList().get(0).getShape();
        radius = shape.getRadius();
        width = radius * 2;
        height = radius * 2;

        body.getFixtureList().get(0).setUserData(type);
    }

    @Override
    public void act(float delta) {

        x = body.getPosition().x;
        y = body.getPosition().y;
    }

    @Override
    public void draw(SpriteBatch batch) {

        batch.draw(textureRegion, x - radius, y - radius, width, height);
    }

    public void reset() {

        body.setTransform(origin, 50);
        body.setActive(true);
    }
}
