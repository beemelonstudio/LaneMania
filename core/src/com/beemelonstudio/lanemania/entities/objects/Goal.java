package com.beemelonstudio.lanemania.entities.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.beemelonstudio.lanemania.entities.Entity;
import com.beemelonstudio.lanemania.entities.types.EntityType;

import java.util.ArrayList;

/**
 * Created by Jann on 09.01.2018.
 */

public class Goal extends Entity {

    //TODO: Add different layers for rendering
    TextureRegion frontTexture;
    public TextureRegion backTexture;

    public Goal() {
        super();
    }

    public Goal(Body body) {
        super(body);

        type = EntityType.GOAL;

        frontTexture = textureAtlas.findRegion("goal_front");
        backTexture = textureAtlas.findRegion("goal_back");

        calculateSizes();

        body.setUserData(type);
    }

    @Override
    public void act(float delta) {

        x = body.getPosition().x;
        y = body.getPosition().y;
    }

    @Override
    public void draw(PolygonSpriteBatch batch) {

        batch.draw(frontTexture, x - width / 2, y - height / 2, width, height);
    }

    /**
     * This is called as the first object to be drawn to create the depth of the goal
     */
    public void drawBackground(PolygonSpriteBatch batch) {

        // The height multiplier is neared by trial and error
        batch.draw(backTexture, x - width / 2, y + height / 2.5f, width, height * 0.25f);
    }

    /**
     * Retrieve PolygonShape vertices and calculate width and height
     */
    private void calculateSizes(){

        /* List of shapes
        -- Left Wall
        -- Left Corner
        -- Right Wall
        -- Right Corner */
        ArrayList<Vector2[]> shapes = new ArrayList<Vector2[]>();
        shapes.add(new Vector2[4]);
        shapes.add(new Vector2[3]);
        shapes.add(new Vector2[4]);
        shapes.add(new Vector2[3]);

        // For every shape add each vertex to the array
        for(int i = 0; i < shapes.size(); i++) {

            PolygonShape shape = (PolygonShape) body.getFixtureList().get(i).getShape();

            for(int j = 0; j < shape.getVertexCount(); j++) {

                shapes.get(i)[j] = new Vector2();
                shape.getVertex(j, shapes.get(i)[j]);
            }
        }

        // Width is the distance between leftCorner[0] and rightCorner[0]
        width = Math.abs(shapes.get(1)[1].x) + Math.abs(shapes.get(3)[0].x);

        // Height is the distance between leftCorner[0] and leftWall[1]
        height = Math.abs(shapes.get(1)[0].y) + Math.abs(shapes.get(0)[0].y);
    }
}
