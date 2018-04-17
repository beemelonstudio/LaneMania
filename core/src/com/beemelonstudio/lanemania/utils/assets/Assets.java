package com.beemelonstudio.lanemania.utils.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.beemelonstudio.lanemania.utils.assets.AssetFile;

import java.io.File;
import java.util.HashMap;

/**
 * Created by Jann on 09.01.2018.
 */

public class Assets {

    private static final AssetManager assetManager = new AssetManager();

    private static HashMap<String, AssetFile> files;

    public static TextureAtlas currentWorldTextureAtlas;

    public static void load(){

        //Enter all files from the assets folder in here
        //**Note that this is a very bad way to handle multiple files**
        files = new HashMap<String, AssetFile>();

        //TextureAtlases
        files.put("orange-theme",   new AssetFile("sprites/orange-theme/orange-theme.atlas",    TextureAtlas.class));
        files.put("wildwest-theme", new AssetFile("sprites/wildwest-theme/wildwest-theme.atlas",TextureAtlas.class));
        files.put("general-theme",  new AssetFile("sprites/general-theme/general-theme.atlas",  TextureAtlas.class));

        //Skins
        files.put("defaultSkin",    new AssetFile("skins/default/uiskin.json",          Skin.class));
        files.put("pixthulhuSkin",  new AssetFile("skins/pixthulhu/pixthulhu-ui.json",  Skin.class));
        files.put("comicSkin",      new AssetFile("skins/comic/comic-ui.json",          Skin.class));
        files.put("beemelonSkin",   new AssetFile("skins/beemelon/skin.json",           Skin.class));

        //Sounds
        files.put("backgroundMenuMusic",    new AssetFile("sounds/backgroundExample.mp3",   Music.class));

        //I18Ns
        //files.put("defaultI18N",     new AssetFile("i18N/prototype",  I18NBundle.class));

        //Loading files
        for(AssetFile asset : files.values()){
            assetManager.load(asset.path, asset.type);
        }

        assetManager.finishLoading();
        //while(!assetManager.update()){} TODO: Decide for one method
    }

    public static Object get(String hashmapKey){
        return assetManager.get(files.get(hashmapKey).path, files.get(hashmapKey).type);
    }

    public static void dispose(){
        assetManager.dispose();
    }
}
