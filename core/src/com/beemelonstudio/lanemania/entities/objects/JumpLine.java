package com.beemelonstudio.lanemania.entities.objects;

import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Pool;
import com.beemelonstudio.lanemania.entities.Entity;
import com.beemelonstudio.lanemania.entities.types.EntityType;
import com.beemelonstudio.lanemania.entities.types.LineType;
import com.beemelonstudio.lanemania.utils.factories.BodyFactory;

/**
 * Created by Jann on 09.01.2018.
 */

public class JumpLine extends Line {

    public void init(float x, float y) {

        start.set(x, y);
        end.set(x, y);

        lineType = LineType.JUMPLINE;
    }

    @Override
    protected void setupTextures() {

        loadTextureAtlas();
        textureRegion = textureAtlas.findRegion("jumpline");
        textureRegionShort = textureAtlas.findRegion("jumpline_short");
    }

    @Override
    public void act(float delta) {

        if(body != null) {
            x = body.getPosition().x;
            y = body.getPosition().y;
        }
    }

    @Override
    public void draw(PolygonSpriteBatch batch) {

        if(body != null) {
            if(width > 0.4f)
                batch.draw(textureRegion, x - width / 2, y - height / 2, width / 2, height / 2, width, height, 1, 1, rotation);
            else
                batch.draw(textureRegionShort, x - width / 2, y - height / 2, width / 2, height / 2, width, height, 1, 1, rotation);
        }
        else {
            if(width > 0.4f)
                batch.draw(textureRegion, end.x, end.y, 0, height / 2, width, height, 1, 1, rotation);
            else
                batch.draw(textureRegionShort, end.x, end.y, 0, height / 2, width, height, 1, 1, rotation);
        }
    }

    /**
     * Retrieve PolygonShape vertices and calculate width and height
     */
    @Override
    protected void calculateSizes(){

        Vector2[] vertices = new Vector2[4];

        PolygonShape shape = (PolygonShape) body.getFixtureList().get(0).getShape();

        for(int i = 0; i < shape.getVertexCount(); i++) {
            vertices[i] = new Vector2();
            shape.getVertex(i, vertices[i]);
        }

        height = Math.abs(vertices[0].x) + Math.abs(vertices[1].x);
        width = Math.abs(vertices[1].y) + Math.abs(vertices[2].y);

        rotation = body.getAngle() * RADTODEG;
    }

    public void setEnd(float x, float y) {

        end.x = x;
        end.y = y;

        float dx = end.x - start.x;
        float dy = end.y - start.y;
        width = (float) Math.sqrt(dx*dx + dy*dy);
        angle = Math.atan2(end.y - start.y, end.x - start.x);
        rotation = (float) Math.toDegrees(angle) + 180f;
    }
}
