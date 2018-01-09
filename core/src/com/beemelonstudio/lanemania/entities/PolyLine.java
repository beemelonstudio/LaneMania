package com.beemelonstudio.lanemania.entities;

import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.EarClippingTriangulator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.FloatArray;
import com.beemelonstudio.lanemania.utils.BodyFactory;

/**
 * Created by Stampler on 09.01.2018.
 */

public class PolyLine extends Entity {

    public Array<Vector2> path;
    public FloatArray vertices;

    private EarClippingTriangulator triangulator;
    private PolygonRegion polygonRegion;
    private PolygonSprite polygonSprite;

    public PolyLine(float x, float y) {

        loadTextureAtlas();
        textureRegion = textureAtlas.findRegion("rectangle_long");
        triangulator = new EarClippingTriangulator();

        vertices = new FloatArray();
        vertices.add(x);
        vertices.add(y);
    }

    public PolyLine(Body body) {
        super(body);

        path = new Array<Vector2>();

        vertices = new FloatArray();
    }

    @Override
    public void act(float delta) {

        //x = body.getPosition().x;
        //y = body.getPosition().y;
    }

    @Override
    public void draw(SpriteBatch batch) {}

    public void draw(PolygonSpriteBatch batch) {

        if(polygonSprite != null)
            polygonSprite.draw(batch);
    }

    public void insert(Vector2 vector) {

        insert(vector.x, vector.y);
    }

    public void insert(float x, float y) {

        if(vertices.size < 16) {
            vertices.add(x);
            vertices.add(y);
        }

        polygonRegion = new PolygonRegion(textureRegion, vertices.toArray(), triangulator.computeTriangles(vertices).toArray());
        polygonSprite = new PolygonSprite(polygonRegion);
    }

    public void build() {

        //Array<Vector2> vectors = VectorUtils.floatArrayToVector2(vertices);
        x = vertices.get(0);
        y = vertices.get(1);
        vertices.reverse();
        BodyFactory.createPolyLine(x, y, vertices.toArray(), BodyDef.BodyType.StaticBody, LineType.SOLID);
    }
}
