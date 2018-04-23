package com.beemelonstudio.lanemania.animations.background;

import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.Viewport;

public class TumbleweedAnimation {

    protected TextureRegion tumbleweed;
    protected float weedX;
    protected float weedY;
    protected PolygonSpriteBatch batch;
    protected Viewport backgroundViewport;
    protected float width = 10f;
    protected float height = 10f;
    protected float originX = width/2;
    protected float originY = height/2;


    public TumbleweedAnimation(TextureRegion thumbleweed, float weedX, float weedY, PolygonSpriteBatch batch, Viewport backgroundViewport){
        this.tumbleweed = tumbleweed;
        this.weedX = weedX;
        this.weedY = weedY;
        this.batch = batch;
        this.backgroundViewport = backgroundViewport;
    }

    public void update(float delta){
        weedX += 20*delta;

    }

    public void render(){
        batch.draw(tumbleweed, weedX, weedY, width, height);
        if (weedX > backgroundViewport.getScreenWidth()) {
            weedX = -250f;
        }
    }
}
