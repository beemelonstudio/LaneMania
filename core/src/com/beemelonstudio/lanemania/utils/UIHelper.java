package com.beemelonstudio.lanemania.utils;

import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.beemelonstudio.lanemania.screens.custombuttons.BmsImageButton;
import com.beemelonstudio.lanemania.screens.custombuttons.BmsTextButton;
import com.beemelonstudio.lanemania.utils.assets.Assets;

public class UIHelper {

    private static Skin skin = (Skin) Assets.get("beemelonSkin");

    public static Cell addToTable(Table table, BmsImageButton button, float width) {
        float aspectRatio = button.imageButtonStyle.up.getMinWidth()/button.imageButtonStyle.up.getMinHeight();
        float height = width / aspectRatio;

        return table.add(button).width(width).height(height);
    }

    public static Cell addToTable(Table table, BmsTextButton button, float width) {
        float aspectRatio = button.textButtonStyle.up.getMinWidth()/button.textButtonStyle.up.getMinHeight();
        float height = width / aspectRatio;

        return table.add(button).width(width).height(height);
    }

    public static Cell addToTable(Table table, Label label, float width) {
        Label.LabelStyle style = new Label.LabelStyle(skin.get("default", Label.LabelStyle.class));
        float aspectRatio = style.background.getMinWidth()/style.background.getMinHeight();
        float height = width / aspectRatio;
        style.font = Assets.font;
        style.fontColor = Assets.font.getColor();
        label.setStyle(style);

        label.setAlignment(Align.center);

        return table.add(label).width(width).height(height);
    }

    public static Cell addToTable(Table table, Image image, float width) {
        float aspectRatio = image.getDrawable().getMinWidth()/image.getDrawable().getMinHeight();//image.getImageWidth()/image.getImageHeight();
        float height = width / aspectRatio;

        return table.add(image).width(width).height(height);
    }
}
