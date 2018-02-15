package com.beemelonstudio.lanemania.entities.obstacles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.math.EarClippingTriangulator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ShortArray;
import com.beemelonstudio.lanemania.entities.Entity;

/**
 * Created by Jann on 12.02.18.
 */

public class TriangleObstacle extends Entity {

    private PolygonRegion texture;
    private float[] vertices;
    private ShortArray triangles;

    public TriangleObstacle(Body body) {
        super(body);

        calculateSizes();

        EarClippingTriangulator triangulator = new EarClippingTriangulator();
        triangles = triangulator.computeTriangles(vertices);

        textureRegion = textureAtlas.findRegion("rectangle");
        texture = new PolygonRegion(textureRegion, vertices, triangles.toArray());
    }

    @Override
    public void act(float delta) {

        x = body.getPosition().x;
        y = body.getPosition().y;
    }

    @Override
    public void draw(PolygonSpriteBatch batch) {

        batch.draw(texture, x, y);
    }

    /**
     * Retrieve PolygonShape vertices and calculate width and height
     */
    private void calculateSizes(){

        x = body.getPosition().x;
        y = body.getPosition().y;

        Array<Vector2> verts = new Array<Vector2>();

        PolygonShape shape = (PolygonShape) body.getFixtureList().get(0).getShape();

        for(int i = 0; i < shape.getVertexCount(); i++) {
            verts.add(new Vector2());
            shape.getVertex(i, verts.get(i));
        }

        vertices = new float[verts.size * 2];
        int j = 0;
        for(int i = 0; i < verts.size; i++) {
            vertices[j] = verts.get(i).x > 0 ? x - verts.get(i).x : x + verts.get(i).x;
            vertices[j+1] = verts.get(i).y > 0 ? y - verts.get(i).y : y + verts.get(i).y;

            j+=2;
        }

        Gdx.app.log("Pos", x + " - " + y);

        for(int t = 0; t < vertices.length; t++) {
            Gdx.app.log("V", vertices[t]+"");
        }
    }
}
