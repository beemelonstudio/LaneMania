package com.beemelonstudio.lanemania.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;

import java.io.File;
import java.util.HashMap;

/**
 * Created by Jann on 09.01.2018.
 */

public class Assets {

    private static final AssetManager assetManager = new AssetManager();

    private static HashMap<String, AssetFile> files;

    public static void load(){

        //Enter all files from the assets folder in here
        //**Note that this is a very bad way to handle multiple files**
        files = new HashMap<String, AssetFile>();

        //TextureAtlases
        files.put("orange-theme",   new AssetFile("sprites/orange-theme/orange-theme.atlas",    TextureAtlas.class));

        //Skins
        files.put("defaultSkin",    new AssetFile("skins/default/uiskin.json",          Skin.class));
        files.put("pixthulhuSkin",  new AssetFile("skins/pixthulhu/pixthulhu-ui.json",  Skin.class));
        files.put("comicSkin",      new AssetFile("skins/comic/comic-ui.json",          Skin.class));

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

    public static Array<Array<String>> loadMaps() {

        Array<Array<String>> worlds = new Array<Array<String>>();

        FileHandle[] worldsArray = Gdx.files.internal("maps").list();
        for(FileHandle world : worldsArray) {

            if(world.isDirectory()) {
                Array<String> maps = new Array<String>();

                FileHandle[] mapsArray = Gdx.files.internal(world.path()).list();
                for(FileHandle map : mapsArray) {

                    maps.add(map.path());
                }
                worlds.add(maps);
            }
        }

        return worlds;
    }

    public static void dispose(){
        assetManager.dispose();
    }
}
