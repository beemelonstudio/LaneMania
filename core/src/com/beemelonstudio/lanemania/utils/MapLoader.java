package com.beemelonstudio.lanemania.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Jann on 27.01.18.
 */

public class MapLoader {

    private boolean debug = true;

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
    }
}
