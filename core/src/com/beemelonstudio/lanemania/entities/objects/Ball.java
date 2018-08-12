package com.beemelonstudio.lanemania.entities.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.beemelonstudio.lanemania.LaneMania;
import com.beemelonstudio.lanemania.entities.Entity;
import com.beemelonstudio.lanemania.entities.types.EntityType;
import com.beemelonstudio.lanemania.utils.assets.Assets;

/**
 * Created by Jann on 09.01.2018.
 */

public class Ball extends Entity {

    private float radius;
    private Vector2 origin;

    public Vector3 worldPosition;
    public Vector3 screenPosition;

    public Ball() {
        super();
    }

    public Ball(Body body) {
        super(body);

        type = EntityType.BALL;

        textureAtlas = (TextureAtlas) Assets.get("general-theme");
        textureRegion = textureAtlas.findRegion("ball");

        origin = new Vector2(body.getPosition());
        worldPosition = new Vector3(0, 0, 0);
        screenPosition = new Vector3(0, 0, 0);

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

        worldPosition.set(x, y, 0);
    }

    @Override
    public void draw(PolygonSpriteBatch batch) {

        batch.draw(textureRegion, x - radius, y - radius, width, height);
    }

    public boolean checkOnScreen() {

        if (screenPosition.x < 0f - radius
                || screenPosition.x > Gdx.graphics.getWidth() + radius
                || screenPosition.y < 0f - radius
                || screenPosition.y > Gdx.graphics.getHeight() + radius
                ) {
            reset();
            return false;
        }
        return true;
    }

    public void reset() {

        if(body.getPosition().x != origin.x || body.getPosition().y != origin.y) {
            body.setTransform(origin, 0);
            body.setActive(true);
            body.setLinearVelocity(0f, 0f);
            body.setType(BodyDef.BodyType.StaticBody);
        }
    }
}
