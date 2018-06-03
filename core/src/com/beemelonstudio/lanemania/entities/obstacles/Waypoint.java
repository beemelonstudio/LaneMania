package com.beemelonstudio.lanemania.entities.obstacles;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Jann on 16.05.18.
 */

public class Waypoint implements Comparable<Waypoint> {

    public String obstacle;
    public Vector2 position;
    public float order;
    public float timer;

    public Waypoint(String obstacle, Vector2 position, float order, float timer) {
        this.obstacle = obstacle;
        this.position = position;
        this.order = order;
        this.timer = timer;
    }

    @Override
    public int compareTo(Waypoint waypoint) {
        return (int) this.order - (int) waypoint.order;
    }
}
