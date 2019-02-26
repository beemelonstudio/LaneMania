package com.beemelonstudio.lanemania.screens.custombuttons;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.beemelonstudio.lanemania.utils.assets.Assets;

/**
 * Created by Jann on 13.01.18.
 */

public class BmsTextButton extends TextButton {

    public TextButtonStyle textButtonStyle;

    public BmsTextButton(String text, Skin skin) {
        this(text, skin, "default");


    }

    public BmsTextButton(String text, Skin skin, String style) {
        super(text, skin);

        textButtonStyle = new TextButtonStyle(skin.get(style, TextButtonStyle.class));
        textButtonStyle.font = Assets.font;
        textButtonStyle.fontColor = Assets.font.getColor();

        this.setStyle(textButtonStyle);
    }
}
