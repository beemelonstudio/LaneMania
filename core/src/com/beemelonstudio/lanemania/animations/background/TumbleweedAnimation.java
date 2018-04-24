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
    protected float width;
    protected float height;
    protected float rotation;


    public TumbleweedAnimation(TextureRegion tumbleweed, float weedX, float weedY, PolygonSpriteBatch batch, Viewport backgroundViewport){
        this.tumbleweed = tumbleweed;
        this.weedX = weedX;
        this.weedY = weedY;
        this.batch = batch;
        this.backgroundViewport = backgroundViewport;
        width = backgroundViewport.getScreenWidth()/8;
        height = backgroundViewport.getScreenHeight()/12;
    }

    public void update(float delta){
        weedX += 40*delta;
        rotation += 10f;


    }

    public void render(){
        batch.draw(tumbleweed, weedX, weedY, width/2, height/2, backgroundViewport.getScreenWidth()/8, backgroundViewport.getScreenHeight()/12, 1f, 1f, rotation);
        if (weedX > backgroundViewport.getScreenWidth()) {
            weedX = -250f;
        }
    }
}
