package com.beemelonstudio.lanemania.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Sort;

/**
 * Created by Jann on 27.01.18.
 */

public class MapLoader {

    private boolean debug = false;

    public Array<Array<String>> worlds;

    public MapLoader() {
    }

    public String getMap(int world, int map) {

        return worlds.get(world).get(map);
    }

    /**
     * Loads the worlds and corresponding maps and puts them in a 2D Array
     * The debug boolean decides whether the internal or local path should be used
     * true = local, false = internal
     */
    public void loadMaps() {

        worlds = new Array<Array<String>>();

        FileHandle[] worldsArray = debug ? Gdx.files.local("./maps").list() : Gdx.files.internal("maps").list();
        for(int i = worldsArray.length-1; i >= 0; i--) {

            if(worldsArray[i].isDirectory()) {
                Array<String> maps = new Array<String>();

                FileHandle[] mapsArray = Gdx.files.internal(worldsArray[i].path()).list();
                for(int j = mapsArray.length-1; j >= 0; j--) {

                    maps.add(mapsArray[j].path());
                }
                Sort.instance().sort(maps);
                worlds.add(maps);
            }
        }
    }
}
