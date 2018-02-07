package com.beemelonstudio.lanemania.utils;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.beemelonstudio.lanemania.entities.LineType;

/**
 * Created by Jann on 09.01.2018.
 */

public class BodyFactory {

    private static float DEGTORAD = (3.14f/180f);

    private static World world;

    public BodyFactory(World world) {
        this.world = world;
    }

    public static void initialize(World world) {
        BodyFactory.world = world;
    }

    private static FixtureDef createFixture(LineType lineType, Shape shape) {

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;

        switch (lineType) {

            case ELASTIC:
                fixtureDef.density = 0.5f;
                fixtureDef.friction = 0.5f;
                fixtureDef.restitution = 0.8f;
                break;

            case SOLID:
                fixtureDef.density = 1f;
                fixtureDef.friction = 0.3f;
                fixtureDef.restitution = 0.1f;
                break;

            default:
        }

        return fixtureDef;
    }

    private static FixtureDef createFixture(ObstacleType lineType, Shape shape) {

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;

        switch (lineType) {

            case SOLID:
                fixtureDef.density = 1f;
                fixtureDef.friction = 0.3f;
                fixtureDef.restitution = 0.1f;
                break;

            default:
        }

        return fixtureDef;
    }

    public static Body createRectangle(float x, float y, float width, float height, float rotation, BodyDef.BodyType bodyType, ObstacleType obstacleType) {

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = bodyType;
        bodyDef.fixedRotation = false;

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2, height / 2);

        Body body = world.createBody(bodyDef);

        // Adjusts positions from Tiled to Box2D positions
        body.setTransform(x + width / 2, y + height / 2, 0);

        // Setup and save current values
        float angle = -rotation * DEGTORAD;
        Vector2 localPosition = new Vector2(width / 2, height / 2);
        Vector2 positionBefore = body.getWorldPoint(localPosition).cpy();

        // Transform / Rotate
        body.setTransform(body.getPosition(), angle);

        // Save new position after rotation
        Vector2 positionAfter = body.getWorldPoint(localPosition).cpy();

        // Calculate new position (before - after) and rotate
        Vector2 newPosition = body.getPosition().add(positionBefore).sub(positionAfter);
        body.setTransform(newPosition, angle);

        body.createFixture(createFixture(obstacleType, shape));

        shape.dispose();

        return body;
    }

    public static Body createLine(float x, float y, float width, float height, float rotation, BodyDef.BodyType bodyType, LineType lineType){

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = bodyType;
        bodyDef.position.x = x;
        bodyDef.position.y = y;
        bodyDef.fixedRotation = false;

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2, height / 2);

        Body body = world.createBody(bodyDef);
        body.setTransform(body.getPosition(), rotation * DEGTORAD);
        body.createFixture(createFixture(lineType, shape));

        shape.dispose();

        return body;
    }

    public static Body createBall(float x, float y) {

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);

        Body body = world.createBody(bodyDef);

        CircleShape shape = new CircleShape();
        shape.setRadius(0.025f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        fixtureDef.friction = 0.2f;
        fixtureDef.restitution = 0.1f;
        body.createFixture(fixtureDef);

        shape.dispose();

        return body;
    }

    public static Body createGoal(float x, float y) {

        // Define and create body
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x, y);
        Body body = world.createBody(bodyDef);

        // Define and create fixture
        PolygonShape polygonShape = new PolygonShape();
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = polygonShape;

        // Left wall
        polygonShape.set(new Vector2[]{
                new Vector2(-0.04f, 0.07f),
                new Vector2(-0.04f, -0.1f),
                new Vector2(-0.05f, -0.1f),
                new Vector2(-0.05f, 0.1f)
        });

        body.createFixture(fixtureDef);

        // Right wall
        polygonShape.set(new Vector2[]{
                new Vector2(0.04f, 0.07f),
                new Vector2(0.04f, -0.1f),
                new Vector2(0.05f, -0.1f),
                new Vector2(0.05f, 0.1f)
        });

        body.createFixture(fixtureDef);

        // Floor
        polygonShape.set(new Vector2[]{
                new Vector2(-0.04f, -0.08f),
                new Vector2(-0.04f, -0.1f),
                new Vector2(0.04f, -0.1f),
                new Vector2(0.04f, -0.08f)
        });

        body.createFixture(fixtureDef);

        // Floor
        polygonShape.set(new Vector2[]{
                new Vector2(-0.04f, -0.04f),
                new Vector2(-0.04f, -0.05f),
                new Vector2(0.04f, -0.05f),
                new Vector2(0.04f, -0.04f)
        });

        fixtureDef.isSensor = true;
        body.createFixture(fixtureDef);

        polygonShape.dispose();

        return body;
    }
}
