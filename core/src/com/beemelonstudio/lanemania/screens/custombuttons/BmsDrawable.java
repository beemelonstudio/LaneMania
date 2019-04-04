package com.beemelonstudio.lanemania.screens.custombuttons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class BmsDrawable {

    public TextureRegion textureRegion;

    public float x, y;
    public float width, height;

    public float rotation;

    public BmsDrawable(TextureRegion textureRegion, float x, float y, float width, float height, float rotation) {
        this.textureRegion = textureRegion;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.rotation = rotation;
    }

    public void draw(PolygonSpriteBatch batch) {
        batch.draw(textureRegion, x, y, 0, 0, width, height, 1f, 1f, rotation);
    }
}
