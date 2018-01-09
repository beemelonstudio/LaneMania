package com.beemelonstudio.lanemania.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.CircleMapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Cedric on 09.01.2018
 */

public class MapBodyBuilder {

    // The pixels per tile. If your tiles are 16x16, this is set to 16f
    private static float ppt = 1/500f;//32f;

    public static Array<Body> buildShapes(TiledMap map, float pixels, World world) {
        ppt = pixels;
        MapObjects objects = map.getLayers().get(3).getObjects();

        Array<Body> bodies = new Array<Body>();

        Vector2 position = new Vector2();

        Gdx.app.log("PPT", ppt +  "");

        for(MapObject object : objects) {

            if (object instanceof TextureMapObject) {
                continue;
            }

            position.setZero();
            Shape shape;

            if (object instanceof RectangleMapObject) {
                shape = getRectangle((RectangleMapObject)object);
                position.set(
                        ((RectangleMapObject)object).getRectangle().getX() * ppt,
                        ((RectangleMapObject)object).getRectangle().getY() * ppt
                );

                Gdx.app.log("Rectangle", position.y * ppt + " - " + position.x * ppt);
            }
            else if (object instanceof PolygonMapObject) {
                shape = getPolygon((PolygonMapObject)object);
                position.set(
                    ((PolygonMapObject)object).getPolygon().getX() * ppt,
                    ((PolygonMapObject)object).getPolygon().getY() * ppt
                );

                Gdx.app.log("Polygon", position.y * ppt + " - " + position.x * ppt);
            }
            else if (object instanceof PolylineMapObject) {
                shape = getPolyline((PolylineMapObject)object);
                position.set(
                    ((PolylineMapObject)object).getPolyline().getX() * ppt,
                    ((PolylineMapObject)object).getPolyline().getY() * ppt
                );

                Gdx.app.log("Polyline", "" + position.toString());
            }
            else if (object instanceof CircleMapObject) {
                shape = getCircle((CircleMapObject)object);
                position.set(
                    ((CircleMapObject)object).getCircle().x * ppt,
                    ((CircleMapObject)object).getCircle().y * ppt
                );

                Gdx.app.log("Circle", "" + position.toString());
            }
            else {
                Gdx.app.log("None", "No shape found!");
                continue;
            }

            BodyDef bd = new BodyDef();
            bd.type = BodyDef.BodyType.StaticBody;
            Gdx.app.log("Position", "" + position.toString());
            bd.position.set(position);
            Body body = world.createBody(bd);
            body.createFixture(shape, 1);

            bodies.add(body);

            shape.dispose();
        }
        return bodies;
    }

    private static PolygonShape getRectangle(RectangleMapObject rectangleObject) {
        Rectangle rectangle = rectangleObject.getRectangle();
        PolygonShape polygon = new PolygonShape();
        //Vector2 size = new Vector2((rectangle.x + rectangle.width * 0.5f) * ppt,
                //(rectangle.y + rectangle.height * 0.5f ) * ppt);
        polygon.setAsBox(rectangle.width * 0.5f * ppt,
                rectangle.height * 0.5f * ppt);//,
                //size,
                //0.0f);

        Gdx.app.log("box", rectangle.width * 0.5f * ppt + " - " + rectangle.height * 0.5f * ppt);
        return polygon;
    }

    private static CircleShape getCircle(CircleMapObject circleObject) {
        Circle circle = circleObject.getCircle();
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(circle.radius * ppt);
        circleShape.setPosition(new Vector2(circle.x * ppt, circle.y * ppt));
        return circleShape;
    }

    private static PolygonShape getPolygon(PolygonMapObject polygonObject) {
        PolygonShape polygon = new PolygonShape();
        float[] vertices = polygonObject.getPolygon().getTransformedVertices();

        float[] worldVertices = new float[vertices.length];

        for (int i = 0; i < vertices.length; ++i) {
            System.out.println(vertices[i]);
            worldVertices[i] = vertices[i] * ppt;
        }

        polygon.set(worldVertices);
        return polygon;
    }

    private static ChainShape getPolyline(PolylineMapObject polylineObject) {
        float[] vertices = polylineObject.getPolyline().getTransformedVertices();
        Vector2[] worldVertices = new Vector2[vertices.length / 2];

        for (int i = 0; i < vertices.length / 2; ++i) {
            worldVertices[i] = new Vector2();
            worldVertices[i].x = vertices[i * 2] * ppt;
            worldVertices[i].y = vertices[i * 2 + 1] * ppt;
        }

        ChainShape chain = new ChainShape();
        chain.createChain(worldVertices);
        return chain;
    }
}
