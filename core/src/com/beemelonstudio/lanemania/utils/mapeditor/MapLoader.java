package com.beemelonstudio.lanemania.utils.mapeditor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Sort;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.crypto.AEADBadTagException;

/**
 * Created by Jann on 27.01.18.
 */

public class MapLoader {

    private boolean debug = false;

    public Array<Array<String>> worlds;

    public MapLoader() {

        worlds = new Array<Array<String>>();
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

        Map tempWorlds = new HashMap<String, Array<String>>();

        FileHandle[] worldsArray = debug ? Gdx.files.local("./maps").list() : Gdx.files.internal("maps").list();
        for(int i = worldsArray.length-1; i >= 0; i--) {

            if(worldsArray[i].isDirectory()) {
                Array<String> maps = new Array<String>();

                FileHandle[] mapsArray = Gdx.files.internal(worldsArray[i].path()).list();
                for(int j = mapsArray.length-1; j >= 0; j--) {

                    maps.add(mapsArray[j].path());
                }
                // Sort maps and add them to world
                Sort.instance().sort(maps);
                tempWorlds.put(worldsArray[i].name(), maps);
            }
        }
        // Sort world by name
        Map tw = new TreeMap<String, Array<String>>(tempWorlds);

        Iterator<Map.Entry<String, Array<String>>> it = tw.entrySet().iterator();
        while (it.hasNext())
            worlds.add(it.next().getValue());
    }
}
