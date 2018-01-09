package com.beemelonstudio.lanemania.utils;

/**
 * Created by Stampler on 09.01.2018.
 */

public class AssetFile {

    public String path;
    public Class<?> type;

    public AssetFile(String path, Class<?> type) {
        this.path = path;
        this.type = type;
    }
}
