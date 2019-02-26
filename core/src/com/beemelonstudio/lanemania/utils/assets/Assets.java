package com.beemelonstudio.lanemania.utils.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ObjectMap;

import java.util.HashMap;

/**
 * Created by Jann on 09.01.2018.
 */

public class Assets {

    private static final AssetManager assetManager = new AssetManager();

    private static HashMap<String, AssetFile> files;

    public static TextureAtlas currentWorldTextureAtlas;

    public static BitmapFont font;

    public static void load(){

        //Enter all files from the assets folder in here
        //**Note that this is a very bad way to handle multiple files**
        files = new HashMap<String, AssetFile>();

        //TextureAtlases
        files.put("wildwest-theme", new AssetFile("sprites/wildwest-theme/wildwest-theme.atlas", TextureAtlas.class));
        files.put("general-theme",  new AssetFile("sprites/general-theme/general-theme.atlas",   TextureAtlas.class));

        //Fonts
        //files.put("passion-one", new AssetFile("skins/beemelon/passion-one.fnt", BitmapFont.class));
        //files.put("passion-oneTTF", new AssetFile("skins/beemelon/passion-one.ttf", TrueT.class));

        //Skins
        files.put("beemelonSkin", new AssetFile("skins/beemelon/skin.json", Skin.class));

        //Sounds
        files.put("backgroundMenuMusic", new AssetFile("sounds/backgroundExample.mp3", Music.class));

        //Images
        //files.put("backgroundW1",   new AssetFile("images/background_w1.jpg",   Texture.class));

        //I18Ns
        //files.put("defaultI18N",     new AssetFile("i18N/prototype",  I18NBundle.class));

        //Loading files
        for(AssetFile asset : files.values()){
            assetManager.load(asset.path, asset.type);
        }

        assetManager.finishLoading();
        //while(!assetManager.update()){} TODO: Decide for one method

        font = generateFont();

        Assets.currentWorldTextureAtlas = (TextureAtlas) Assets.get("wildwest-theme");
    }

    public static Object get(String hashmapKey){
        return assetManager.get(files.get(hashmapKey).path, files.get(hashmapKey).type);
    }

    private static BitmapFont generateFont(){
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("skins/beemelon/passion-one-regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = (int) (38 * Gdx.graphics.getDensity());
        parameter.color = new Color(255f / 255f, 165f / 255f, 23f / 255f, 1f); // Divide by 255f to get a value between 0 and 1
        BitmapFont bitmapFont = generator.generateFont(parameter);
        generator.dispose();
        return bitmapFont;
    }

    /*
    private static void generateFont() {

        // Load and generate font
        FileHandleResolver resolver = new InternalFileHandleResolver();
        assetManager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
        assetManager.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));
        FreetypeFontLoader.FreeTypeFontLoaderParameter parameter = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        parameter.fontFileName = "skins/beemelon/passion-one-regular.ttf";
        parameter.fontParameters.size = 50;
        parameter.fontParameters.color = Color.BLACK;
        files.put("font", new AssetFile("font.ttf", BitmapFont.class));
        assetManager.load("font.ttf", BitmapFont.class, parameter);
        assetManager.finishLoading();

        // Add fonts to ObjectMap
        BitmapFont font = assetManager.get("font.ttf", BitmapFont.class);
        ObjectMap<String, Object> fontMap = new ObjectMap<String, Object>();
        fontMap.put("font-export.fnt", font);
        fontMap.put("title", font);

        SkinLoader.SkinParameter skinParameter = new SkinLoader.SkinParameter(fontMap);

        files.put("beemelonSkin", new AssetFile("skins/beemelon/skin.json", Skin.class));
        assetManager.load("skins/beemelon/skin.json", Skin.class, skinParameter);
        assetManager.finishLoading();
        /*
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("skins/beemelon/passion-one-regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 24;
        BitmapFont font = generator.generateFont(parameter);
        generator.dispose();

        Skin skin = new Skin();
        skin.add("font", font, BitmapFont.class);
        skin.add("title", font, BitmapFont.class);

        //Skin originalSkin = (Skin) get("beemelonSkin");
        skin.load(Gdx.files.internal(files.get("beemelonSkin").path));

        /*
        com.badlogic.gdx.graphics.g2d.BitmapFont: {
	font: {
		file: font-export.fnt
	}
	title: {
		file: font-title-export.fnt
	}
}

    }
    */

    public static void dispose(){
        assetManager.dispose();
    }
}
