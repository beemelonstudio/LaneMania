package com.beemelonstudio.lanemania.entities.obstacles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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

        //calculateSizes();

        vertices = new float[]{0,0,-0.197f, 0.229f, 0.021f, -0.39f};

        EarClippingTriangulator triangulator = new EarClippingTriangulator();
        triangles = triangulator.computeTriangles(vertices);

        textureRegion = textureAtlas.findRegion("triangle");
        /*Pixmap pix = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pix.setColor(0xFF3369FF); // DE is red, AD is green and BE is blue.
        pix.fill();
        Texture texturee = new Texture(pix);
        TextureRegion textureRegion = new TextureRegion(texturee);*/
        texture = new PolygonRegion(textureRegion, vertices, triangles.toArray());

        for(int i = 0; i < vertices.length; i++) {
            Gdx.app.log("Verts", vertices[i]+" / "+texture.getVertices()[i]);
        }

        for(int j = 0; j < triangles.size; j++) {
            Gdx.app.log("Triangle", triangles.toArray()[j]+"");
        }
    }

    @Override
    public void act(float delta) {

        x = body.getPosition().x;
        y = body.getPosition().y;
    }

    @Override
    public void draw(PolygonSpriteBatch batch) {

        batch.draw(texture, x, y);

//        batch.draw(textureRegion, x, y, 0.1f, 0.1f);
  //      batch.draw(textureRegion, x-0.197f, y-0.229f, 0.1f, 0.1f);
    //    batch.draw(textureRegion, x+0.021f, y-0.39f, 0.1f, 0.1f);

        /*
        V: 0.44630462
        V: 0.5905334
        V: 0.4250521
        V: 0.9808084
        V: 0.22798271
        V: 0.7508938
         */
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

        vertices = new float[verts.size * 2 + 2];
        vertices[0] = 0;
        vertices[1] = 0;
        int j = 2;
        for(int i = 0; i < verts.size; i++) {
            vertices[j] = verts.get(i).x;
            vertices[j+1] = verts.get(i).y;

            j+=2;
        }

        for(int t = 0; t < vertices.length; t++) {
            Gdx.app.log("V", vertices[t]+"");
        }
    }
}
