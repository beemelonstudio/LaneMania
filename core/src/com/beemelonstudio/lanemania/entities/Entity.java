package com.beemelonstudio.lanemania.entities;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.beemelonstudio.lanemania.utils.Assets;

/**
 * Created by Jann on 09.01.2018.
 */

public abstract class Entity implements Actable {

    protected float DEGTORAD = (3.1415f/180f);
    protected float RADTODEG = (180f/3.1415f);

    public EntityType type;

    protected TextureAtlas textureAtlas;
    protected TextureRegion textureRegion;

    protected Body body;

    public float x, y;
    public float width, height;

    public Entity() {
        loadTextureAtlas();
    }

    public Entity(Body body) {
        this.body = body;

        loadTextureAtlas();
    }

    protected void loadTextureAtlas() {
        textureAtlas = (TextureAtlas) Assets.get("orange-theme");
    }
}
