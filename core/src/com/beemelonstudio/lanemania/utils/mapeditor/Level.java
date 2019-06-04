package com.beemelonstudio.lanemania.utils.mapeditor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.beemelonstudio.lanemania.utils.assets.Assets;

public class Level {

    public MapAnalyser mapAnalyser;
    private boolean isLoaded = false;
    public int gameplayOrder;

    public String worldName;
    public String name;
    public TextureRegionDrawable preview;
    public int star1, star2, star3;
    public float starsNeeded;
    public int stars;

    public TiledMap map;
    public float mapHeightInPixel;
    public float unitScale;

    public Level(String mapName) {

        // Remove unwanted path before and after the world name
        String prefixRemoved = mapName.substring("maps/".length());
        worldName = prefixRemoved.substring(0, prefixRemoved.indexOf("/"));
        name = prefixRemoved.substring(prefixRemoved.indexOf("/")+1, prefixRemoved.indexOf("."));

        map = new TmxMapLoader().load(mapName);
        float mapWidth = (float) map.getProperties().get("width", Integer.class);
        float tileWidth = (float) map.getProperties().get("tilewidth", Integer.class);

        float mapHeight = (float) map.getProperties().get("height", Integer.class);
        float tileHeight = (float) map.getProperties().get("tileheight", Integer.class);
        mapHeightInPixel = mapHeight * tileHeight;

        unitScale = 1/(mapWidth * tileWidth);

        mapAnalyser = new MapAnalyser(map, unitScale);
        mapAnalyser.loadMapProperties();
        gameplayOrder = ((Float) mapAnalyser.mapProperties.get("gameplayOrder")).intValue();
        star1 = ((Float) mapAnalyser.mapProperties.get("star1")).intValue();
        star2 = ((Float) mapAnalyser.mapProperties.get("star2")).intValue();
        star3 = ((Float) mapAnalyser.mapProperties.get("star3")).intValue();
        starsNeeded = (Float) mapAnalyser.mapProperties.get("starsNeeded");
        stars = Assets.preferences.getInteger(name, 0);
        preview = new TextureRegionDrawable(Assets.generalTextureAtlas.findRegion(mapAnalyser.mapProperties.get("preview").toString()));
    }

    public void setup() {
        if(!isLoaded) {
            mapAnalyser.generateLevel();
            isLoaded = true;
        }
    }

    public void saveStars(int amountOfStars) {
        int sumStars = Assets.preferences.getInteger("amountOfStars", 0);
        sumStars += amountOfStars;

        if(amountOfStars > Assets.preferences.getInteger(name, 0))
            Assets.preferences.putInteger(name, amountOfStars);
        Assets.preferences.putInteger("sumStars", sumStars);
        Assets.preferences.flush();
        stars = amountOfStars;
    }
}
