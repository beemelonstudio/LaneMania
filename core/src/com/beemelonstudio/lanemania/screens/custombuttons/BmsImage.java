package com.beemelonstudio.lanemania.screens.custombuttons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.beemelonstudio.lanemania.LaneMania;
import com.beemelonstudio.lanemania.utils.assets.Assets;

public class BmsImage extends Image {

    public ShaderProgram shader;
    private boolean blurred = false;

    public BmsImage(TextureRegionDrawable region) {
        super(region);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if(blurred && shader != null) {
            batch.setShader(shader);
            super.draw(batch, parentAlpha);
            batch.setShader(null);
        }
        else {
            super.draw(batch, parentAlpha);
        }
    }

    public void setBlurred(boolean blurred) {
        this.blurred = blurred;

        ShaderProgram.pedantic = false;

        if(shader == null)
            shader = new ShaderProgram(Assets.VERT_1, Assets.FRAG_1);

        if (!shader.isCompiled()) throw new GdxRuntimeException("Couldn't compile shader: " + shader.getLog());

        if (shader.getLog().length()!=0)
            System.out.println(shader.getLog());
    }
}
