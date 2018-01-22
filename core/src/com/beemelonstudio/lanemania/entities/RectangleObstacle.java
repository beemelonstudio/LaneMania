package com.beemelonstudio.lanemania.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.PolygonShape;

/**
 * Created by Jann on 11.01.18.
 */

public class RectangleObstacle extends Entity {

    private float rotation;

    public RectangleObstacle(Body body) {
        super(body);

        textureRegion = textureAtlas.findRegion("rectangle");

        calculateSizes();
    }

    @Override
    public void act(float delta) {

        x = body.getPosition().x;
        y = body.getPosition().y;
    }

    @Override
    public void draw(SpriteBatch batch) {

        batch.draw(textureRegion, x - width / 2, y - height / 2, width, height);
    }

    /**
     * Retrieve PolygonShape vertices and calculate width and height
     */
    private void calculateSizes(){

        Vector2[] vertices = new Vector2[4];

        PolygonShape shape = (PolygonShape) body.getFixtureList().get(0).getShape();

        for(int i = 0; i < shape.getVertexCount(); i++) {
            vertices[i] = new Vector2();
            shape.getVertex(i, vertices[i]);
        }

        width = Math.abs(vertices[0].x) + Math.abs(vertices[1].x);
        height = Math.abs(vertices[1].y) + Math.abs(vertices[2].y);

        rotation = body.getAngle() * RADTODEG;
    }
}
