package com.beemelonstudio.lanemania.screens.custombuttons;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;

public class WorldImageButton extends BmsImageButton {

    public Array<BmsImageButton> mapButtons;

    public WorldImageButton(Skin skin, TextureRegion textureRegion) {
        super(skin, textureRegion, "transparent");

        mapButtons = new Array<BmsImageButton>();
    }

    public void addMapButton(BmsImageButton button, Skin skin, TextureAtlas textureAtlas, int i) {

        TextureRegion textureRegion = textureAtlas.findRegion("level" + (i+1) + "_w1");
        button = new BmsImageButton(skin, textureRegion, "transparent");

        button.setVisible(false);
        mapButtons.add(button);
    }

    public void setMapButtonVisibility(boolean visibility) {

        for(BmsImageButton button : mapButtons)
            button.setVisible(visibility);
    }
}
