package com.beemelonstudio.lanemania.entities.objects;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.beemelonstudio.lanemania.entities.Entity;
import com.badlogic.gdx.utils.Pool;
import com.beemelonstudio.lanemania.entities.types.EntityType;
import com.beemelonstudio.lanemania.entities.types.LineType;
import com.beemelonstudio.lanemania.utils.factories.BodyFactory;

public abstract class Line extends Entity implements Pool.Poolable {

    protected TextureRegion textureRegionShort;

    protected LineType lineType;
    public Vector2 start, end;

    public float rotation;
    public double angle;
    protected float height = 0.025f;

    public Line() {

        setupTextures();

        start = new Vector2();
        end = new Vector2();
    }

    public Line(Body body) {
        super(body);

        setupTextures();

        start = new Vector2();
        end = new Vector2();

        calculateSizes();

        //body.getFixtureList().get(0).setUserData(type);
        body.setUserData(type);
    }

    public void build() {

        body = BodyFactory.createLine((start.x + end.x) / 2, (start.y + end.y) / 2, width, height, rotation, BodyDef.BodyType.StaticBody, lineType);
        body.setUserData(type);
    }

    @Override
    public void reset() {
        x = 0;
        y = 0;
        width = 0;

        start.set(0, 0);
        end.set(0, 0);
        rotation = 0;

        if(body != null) {
            body.getWorld().destroyBody(body);
            body = null;
        }
    }

    protected abstract void setupTextures();

    protected abstract void calculateSizes();
}
