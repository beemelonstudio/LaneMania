package com.beemelonstudio.lanemania.animations.background;

import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.Viewport;

public class CloudAnimation {

    protected TextureRegion cloud;
    protected float cloudX;
    protected float cloudY;
    protected PolygonSpriteBatch batch;
    protected Viewport backgroundViewport;

    public CloudAnimation(TextureRegion cloud, float cloudX, float cloudY, PolygonSpriteBatch batch, Viewport backgroundViewport){
        this.cloud = cloud;
        this.cloudX = cloudX;
        this.cloudY = cloudY;
        this.batch = batch;
        this.backgroundViewport = backgroundViewport;
    }

    public void update(float delta){
        cloudX += 10*delta;
    }

    public void render(){
        batch.draw(cloud, cloudX, cloudY, backgroundViewport.getScreenWidth()/2, backgroundViewport.getScreenHeight()/8);
        if (cloudX > backgroundViewport.getScreenWidth()) {
            cloudX = -250f;
        }
    }
}
