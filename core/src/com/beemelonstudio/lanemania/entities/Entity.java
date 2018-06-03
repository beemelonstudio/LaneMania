package com.beemelonstudio.lanemania.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.beemelonstudio.lanemania.entities.obstacles.Waypoint;
import com.beemelonstudio.lanemania.entities.types.EntityType;
import com.beemelonstudio.lanemania.utils.assets.Assets;

/**
 * Created by Jann on 09.01.2018.
 */

public abstract class Entity implements Actable {

    protected float DEGTORAD = (3.1415f/180f);
    protected float RADTODEG = (180f/3.1415f);

    public String name;
    public EntityType type;

    protected TextureAtlas textureAtlas;
    protected TextureRegion textureRegion;

    public Body body;
    private Vector2 velocity;

    public Vector2 origin;
    public float x, y;
    public float width, height;
    public float rotation;
    public float speed = 0.2f;
    public float rotationSpeed;
    public float timer;

    public Array<Waypoint> waypoints;
    public int waypointCounter;
    public boolean circle;
    public int mover = -1;
    private boolean back = false;

    private float waitedTime = 0;
    private boolean waitingLocked = false;
    private boolean waiting = true;

    public Entity() {
        loadTextureAtlas();
    }

    public Entity(Body body) {
        this.body = body;

        type = EntityType.NONE;

        origin = body.getPosition().cpy();
        velocity = new Vector2();

        waypoints = new Array<Waypoint>();
        waypointCounter = 0;

        loadTextureAtlas();
    }

    protected void loadTextureAtlas() {
        textureAtlas = Assets.currentWorldTextureAtlas;
    }

    @Override
    public void draw(PolygonSpriteBatch batch) {
    }

    @Override
    public void act(float delta) {

        if(waypoints.size > 0) {

            if (waypoints.get(waypointCounter).timer != 0 && !waitingLocked) {

                Gdx.app.log("Waiting", waitedTime + " of " + waypoints.get(waypointCounter).timer);

                waitedTime += delta;
                if (waitedTime >= waypoints.get(waypointCounter).timer) {
                    waitedTime = 0;
                    waitingLocked = true;
                    waiting = false;
                }
            }

            if (waypoints.size > 1) {

                float distance = waypoints.get(waypointCounter).position.dst(body.getPosition());
                if (distance <= 1 / 12f) {

                    if (waypointCounter + 1 >= waypoints.size) {

                        if (circle) {
                            waypointCounter = 0;
                            return;
                        } else {
                            back = true;
                            mover = -1;
                        }
                    }

                    if (waypointCounter == 0) {
                        back = false;
                        mover = 1;
                    }

                    body.setLinearVelocity(0, 0);
                    body.setAngularVelocity(0);

                    waypointCounter += mover;
                    waitingLocked = false;
                    waiting = true;
                } else {

                    if (!waiting) {

                        velocity = waypoints.get(waypointCounter).position.cpy();
                        velocity.sub(body.getPosition());
                        velocity.nor();
                        velocity.scl(speed, speed);

                        body.setLinearVelocity(velocity);
                    }
                }
            }
        }

        x = body.getPosition().x;
        y = body.getPosition().y;
    }
}
