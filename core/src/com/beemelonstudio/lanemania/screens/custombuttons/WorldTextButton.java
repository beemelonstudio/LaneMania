package com.beemelonstudio.lanemania.screens.custombuttons;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.github.czyzby.lml.parser.impl.tag.AbstractActorLmlTag;

import javax.xml.soap.Text;

/**
 * Created by Jann on 27.01.18.
 */

public class WorldTextButton extends TextButton {

    public Array<TextButton> mapButtons;

    public WorldTextButton(String text, Skin skin) {
        super(text, skin);

        mapButtons = new Array<TextButton>();
    }

    public void addMapButton(TextButton button) {

        String text = button.getText().toString();
        text = text.substring(15, text.length()-4);
        button.setText(text);

        button.setVisible(false);
        mapButtons.add(button);
    }

    public void setMapButtonVisibility(boolean visibility) {

        for(TextButton button : mapButtons)
            button.setVisible(visibility);
    }
}
