package com.beemelonstudio.lanemania.animations.background;

import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.Viewport;

public class CloudAnimation {

    public CloudAnimation(TextureRegion cloud, float cloudX, float cloudY, float delta, PolygonSpriteBatch batch, Viewport backgroundViewport){
        cloudX += 10*delta;
        batch.draw(cloud, cloudX, cloudY, backgroundViewport.getScreenWidth()/2, backgroundViewport.getScreenHeight()/8);
        if (cloudX > backgroundViewport.getScreenWidth()) {
            cloudX = -200f;
        }
        return cloudX;
    }

}
