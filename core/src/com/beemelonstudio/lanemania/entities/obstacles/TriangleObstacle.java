package com.beemelonstudio.lanemania.entities.obstacles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.EarClippingTriangulator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ShortArray;
import com.beemelonstudio.lanemania.entities.Entity;
import com.beemelonstudio.lanemania.entities.types.EntityType;

/**
 * Created by Jann on 12.02.18.
 */

public class TriangleObstacle extends Entity {

    private PolygonRegion polygonRegion;
    private PolygonSprite polygonSprite;
    private float[] vertices;
    private ShortArray triangles;

    public TriangleObstacle(Body body, String name, float speed, float rotationSpeed, boolean circle, float timer) {
        super(body);

        this.name = name;
        this.speed = speed / 100;
        this.rotationSpeed = rotationSpeed;
        this.circle = circle;
        this.timer = timer;

        calculateSizes();

        EarClippingTriangulator triangulator = new EarClippingTriangulator();
        triangles = triangulator.computeTriangles(vertices);

        type = EntityType.OBSTACLE;
        textureRegion = textureAtlas.findRegion("rectangle");
        polygonRegion = new PolygonRegion(textureRegion, vertices, triangles.toArray());
        polygonSprite = new PolygonSprite(polygonRegion);
        polygonSprite.setPosition(x, y);

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

        polygonSprite.draw(batch);
    }

    /**
     * Retrieve PolygonShape vertices
     */
    private void calculateSizes(){

        x = body.getPosition().x;
        y = body.getPosition().y;

        Array<Vector2> verts = new Array<Vector2>();

        PolygonShape shape = (PolygonShape) body.getFixtureList().get(0).getShape();

        // Get vertices from box2d object
        for(int i = 0; i < shape.getVertexCount(); i++) {
            verts.add(new Vector2());
            shape.getVertex(i, verts.get(i));
        }

        // Transform vertices to float[]
        vertices = new float[verts.size * 2];
        int j = 0;
        for(int i = 0; i < verts.size; i++) {
            vertices[j] = x + verts.get(i).x;
            vertices[j+1] = y + verts.get(i).y;

            j+=2;
        }
    }
}
