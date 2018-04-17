package com.beemelonstudio.lanemania.screens.custombuttons;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.beemelonstudio.lanemania.LaneMania;

/**
 * Created by Jann on 13.01.18.
 */

public class BmsImageButton extends ImageButton {

    public BmsImageButton(Skin skin) {
        super(skin);
    }

    public BmsImageButton(Skin skin, TextureRegion textureRegion) {
        this(skin, textureRegion, "default");
    }

    public BmsImageButton(Skin skin, TextureRegion textureRegion, String style) {
        super(skin);

        //textureRegion.setRegionWidth((int) (textureRegion.getRegionWidth() * 0.8f));
        //textureRegion.setRegionHeight((int) (textureRegion.getRegionHeight() * 0.8f));

        ImageButtonStyle imageButtonStyle = new ImageButtonStyle(skin.get(style, ImageButtonStyle.class));
        imageButtonStyle.imageUp = new TextureRegionDrawable(textureRegion);
        imageButtonStyle.imageDown = imageButtonStyle.imageUp;

        this.setStyle(imageButtonStyle);
        this.updateImage();
    }
}
