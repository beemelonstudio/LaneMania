package com.beemelonstudio.lanemania.utils.mapeditor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.beemelonstudio.lanemania.utils.assets.Assets;

public class Level {

    public MapAnalyser mapAnalyser;

    public String worldName;
    public String name;
    public TextureRegionDrawable preview;
    public float star2, star3;
    public float starsNeeded;

    public TiledMap map;
    public float mapHeightInPixel;
    public float unitScale;

    public Level(String mapName) {

        // Remove unwanted path before and after the world name
        worldName = mapName.substring("maps/".length());
        worldName = worldName.substring(0, worldName.indexOf("/"));

        map = new TmxMapLoader().load(mapName);
        float mapWidth = (float) map.getProperties().get("width", Integer.class);
        float tileWidth = (float) map.getProperties().get("tilewidth", Integer.class);

        float mapHeight = (float) map.getProperties().get("height", Integer.class);
        float tileHeight = (float) map.getProperties().get("tileheight", Integer.class);
        mapHeightInPixel = mapHeight * tileHeight;

        unitScale = 1/(mapWidth * tileWidth);

        mapAnalyser = new MapAnalyser(map, unitScale);
        mapAnalyser.loadMapProperties();
        star2 = (Float) mapAnalyser.mapProperties.get("star2");
        star3 = (Float) mapAnalyser.mapProperties.get("star3");
        starsNeeded = (Float) mapAnalyser.mapProperties.get("starsNeeded");
        preview = new TextureRegionDrawable(Assets.currentWorldTextureAtlas.findRegion(mapAnalyser.mapProperties.get("preview").toString()));
    }

    public void setup() {
        mapAnalyser.generateLevel();
    }
}
