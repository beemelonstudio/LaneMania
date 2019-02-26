package com.beemelonstudio.lanemania.screens.custombuttons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;
import com.beemelonstudio.lanemania.LaneMania;
import com.beemelonstudio.lanemania.utils.assets.Assets;

/**
 * Created by Jann on 13.01.18.
 */

public class BmsImageButton extends ImageButton {

    public ImageButtonStyle imageButtonStyle;

    public BmsImageButton(Skin skin) {
        super(skin);
    }

    public BmsImageButton(Skin skin, TextureRegion textureRegion) {
        this(skin, textureRegion, "default");
    }

    public BmsImageButton(Skin skin, String style) {
        super(skin, style);
        imageButtonStyle = new ImageButtonStyle(skin.get(style, ImageButtonStyle.class));
//
//        float aspectRatio = imageButtonStyle.imageUp.getMinWidth()/imageButtonStyle.imageUp.getMinHeight();//d.getRegion().getRegionWidth()/d.getRegion().getRegionHeight();
//        float width = (float)Gdx.graphics.getWidth()*0.8f;
//        float height = width / aspectRatio;
//
//        imageButtonStyle.imageUp.setMinWidth(width);
//        imageButtonStyle.imageUp.setMinHeight(height);
//
//        this.setStyle(imageButtonStyle);
//        this.updateImage();
    }

    public BmsImageButton(Skin skin, TextureRegion textureRegion, String style) {
        super(skin);

        //textureRegion.setRegionWidth((int) (textureRegion.getRegionWidth() * 0.8f));
        //textureRegion.setRegionHeight((int) (textureRegion.getRegionHeight() * 0.8f));

        imageButtonStyle = new ImageButtonStyle(skin.get(style, ImageButtonStyle.class));
        imageButtonStyle.imageUp = new TextureRegionDrawable(textureRegion);
        imageButtonStyle.imageDown = imageButtonStyle.imageUp;

        this.setStyle(imageButtonStyle);
        this.updateImage();
    }
}
