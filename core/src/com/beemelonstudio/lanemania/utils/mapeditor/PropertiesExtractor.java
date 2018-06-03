package com.beemelonstudio.lanemania.utils.mapeditor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;

/**
 * Created by Jann on 31.05.18.
 */

public class PropertiesExtractor {

    public String type, obstacle, entity, name;
    public float rotation, speed, rotationSpeed, order, timer;
    public boolean circle;

    public void extract(MapObject object) {

        if (object.getProperties().get("rotation", Float.class) != null)
            rotation = object.getProperties().get("rotation", Float.class);

        if (object.getProperties().get("speed", Float.class) != null)
            speed = object.getProperties().get("speed", Float.class);

        if (object.getProperties().get("rspeed", Float.class) != null)
            rotationSpeed = object.getProperties().get("rspeed", Float.class);

        if (object.getProperties().get("order", Float.class) != null)
            order = object.getProperties().get("order", Float.class);

        if (object.getProperties().get("timer", Float.class) != null)
            timer = object.getProperties().get("timer", Float.class);

        if (object.getProperties().get("circle", Boolean.class) != null)
            circle = object.getProperties().get("circle", Boolean.class);

        if(object.getProperties().get("type") != null)
            type = (String) object.getProperties().get("type");

        if(object.getProperties().get("obstacle") != null)
            obstacle = (String) object.getProperties().get("obstacle");

        if(object.getName() != null)
            name = object.getName();
    }
}
