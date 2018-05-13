package com.beemelonstudio.lanemania.entities.obstacles;

import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.beemelonstudio.lanemania.entities.Entity;
import com.beemelonstudio.lanemania.entities.types.EntityType;

/**
 * Created by Jann on 05.05.18.
 */

public class SquareObstacle extends Entity {

    private float rotation;

    public SquareObstacle(Body body) {
        super(body);

        type = EntityType.OBSTACLE;
        textureRegion = textureAtlas.findRegion("square");

        calculateSizes();
        body.setUserData(type);
    }

    @Override
    public void act(float delta) {

        //x = body.getPosition().x;
        //y = body.getPosition().y;
    }

    @Override
    public void draw(PolygonSpriteBatch batch) {

        batch.draw(textureRegion, x - width / 2, y - height / 2, width / 2, height / 2, width, height, 1f, 1f, rotation);
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

        x = body.getPosition().x;
        y = body.getPosition().y;
    }
}
