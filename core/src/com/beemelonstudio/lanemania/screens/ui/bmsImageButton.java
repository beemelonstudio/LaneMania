package com.beemelonstudio.lanemania.screens.ui;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Created by Jann on 13.01.18.
 */

public class bmsImageButton extends ImageButton {

    public bmsImageButton(Skin skin) {
        super(skin);
    }

    public bmsImageButton(Skin skin, TextureRegion textureRegion) {
        super(skin);

        //textureRegion.setRegionWidth((int) (textureRegion.getRegionWidth() * 0.8f));
        //textureRegion.setRegionHeight((int) (textureRegion.getRegionHeight() * 0.8f));

        ImageButtonStyle style = new ImageButtonStyle(skin.get("default", ImageButtonStyle.class));
        style.imageUp = new TextureRegionDrawable(textureRegion);

        this.setStyle(style);
        this.updateImage();
    }
}
