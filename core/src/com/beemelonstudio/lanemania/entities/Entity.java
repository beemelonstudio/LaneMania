package com.beemelonstudio.lanemania.entities;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.beemelonstudio.lanemania.entities.types.EntityType;
import com.beemelonstudio.lanemania.utils.assets.Assets;

/**
 * Created by Jann on 09.01.2018.
 */

public abstract class Entity implements Actable {

    protected float DEGTORAD = (3.1415f/180f);
    protected float RADTODEG = (180f/3.1415f);

    public EntityType type;

    protected TextureAtlas textureAtlas;
    protected TextureRegion textureRegion;

    public Body body;

    public float x, y;
    public float width, height;

    public Entity() {
        loadTextureAtlas();
    }

    public Entity(Body body) {
        this.body = body;

        type = EntityType.NONE;

        loadTextureAtlas();
    }

    protected void loadTextureAtlas() {
        textureAtlas = Assets.currentWorldTextureAtlas;
    }
}
