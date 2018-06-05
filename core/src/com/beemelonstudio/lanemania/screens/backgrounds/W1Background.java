package com.beemelonstudio.lanemania.screens.backgrounds;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;
import com.beemelonstudio.lanemania.animations.background.CloudAnimation;
import com.beemelonstudio.lanemania.animations.background.TumbleweedAnimation;
import com.beemelonstudio.lanemania.screens.GameScreen;
import com.beemelonstudio.lanemania.utils.assets.Assets;

public class W1Background {

    protected TextureAtlas textureAtlas;
    protected TextureRegion backgroundTexture;
    protected TiledDrawable backgroundTile;

    protected TextureRegion cloud1;
    protected float cloud1x;
    protected float cloud1y;
    protected TextureRegion cloud2;
    protected float cloud2x;
    protected float cloud2y;
    protected CloudAnimation animation1;
    protected CloudAnimation animation2;
    protected TextureRegion tumbleweed;
    protected float weedX;
    protected float weedY;
    protected TumbleweedAnimation animation3;

    public W1Background() {

        textureAtlas = (TextureAtlas) Assets.get("wildwest-theme");
        backgroundTexture = textureAtlas.findRegion("background");

        cloud1 = textureAtlas.findRegion("cloud1");
        cloud2 = textureAtlas.findRegion("cloud2");
        cloud1x = 0f;
        cloud2x = 300f;
        cloud1y = backgroundViewport.getScreenHeight() - 160;
        cloud2y = backgroundViewport.getScreenHeight() - 320;
        animation1 = new CloudAnimation(cloud1, cloud1x, cloud1y, batch, backgroundViewport);
        animation2 = new CloudAnimation(cloud2, cloud2x, cloud2y, batch, backgroundViewport);
        tumbleweed = textureAtlas.findRegion("tumbleweed");
        weedX = 0f;
        weedY = 110f;
        animation3 = new TumbleweedAnimation(tumbleweed, weedX, weedY, batch, backgroundViewport);
    }

    public void update() {
        animation1.update(delta);
        animation2.update(delta);
        animation3.update(delta);
        backgroundViewport.apply();
        batch.setProjectionMatrix(backgroundViewport.getCamera().combined);
        batch.begin();
        batch.draw(backgroundTexture, 0, 0, backgroundViewport.getScreenWidth(), backgroundViewport.getScreenHeight());
        animation1.render();
        animation2.render();
        animation3.render();
        batch.end();
    }
}
