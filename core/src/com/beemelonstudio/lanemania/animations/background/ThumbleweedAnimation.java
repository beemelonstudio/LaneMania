package com.beemelonstudio.lanemania.animations.background;

import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.Viewport;

public class ThumbleweedAnimation {

    protected TextureRegion thumbleweed;
    protected float weedX;
    protected float weedY;
    protected PolygonSpriteBatch batch;
    protected Viewport backgroundViewport;

    public ThumbleweedAnimation(TextureRegion thumbleweed, float weedX, float weedY, PolygonSpriteBatch batch, Viewport backgroundViewport){
        this.thumbleweed = thumbleweed;
        this.weedX = weedX;
        this.weedY = weedY;
        this.batch = batch;
        this.backgroundViewport = backgroundViewport;
    }

    public void update(float delta){
        weedX += 10*delta;
        
    }

    public void render(){
        batch.draw(thumbleweed, weedX, weedY, backgroundViewport.getScreenWidth()/2, backgroundViewport.getScreenHeight()/8);
        if (weedX > backgroundViewport.getScreenWidth()) {
            weedX = -250f;
        }
    }
}
