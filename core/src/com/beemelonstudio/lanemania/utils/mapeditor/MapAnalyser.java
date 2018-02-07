package com.beemelonstudio.lanemania.utils.mapeditor;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.CircleMapObject;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.utils.Array;
import com.beemelonstudio.lanemania.entities.Entity;
import com.beemelonstudio.lanemania.entities.types.ObstacleType;
import com.beemelonstudio.lanemania.entities.obstacles.RectangleObstacle;
import com.beemelonstudio.lanemania.utils.BodyFactory;

/**
 * Created by Jann and Cedric on 10.01.18.
 */

public class MapAnalyser {

    private float unitScale;

    private TiledMap map;
    private MapLayer objectsLayer;
    private MapLayer obstaclesLayer;
    private TiledMapTileLayer backgroundLayer;

    public Body ball, goal;
    public Array<Entity> obstacles;

    public MapAnalyser(TiledMap map, float unitScale) {
        this.map = map;
        this.unitScale = unitScale;

        objectsLayer = map.getLayers().get("objects");
        obstaclesLayer = map.getLayers().get("obstacles");
        backgroundLayer = (TiledMapTileLayer) map.getLayers().get("background");

        obstacles = new Array<Entity>();

        loadObjects();
        loadObstacles();
        loadBackground();
    }

    private void loadObjects() {

        for(MapObject object : objectsLayer.getObjects()) {

            if(object.getProperties().get("entity").toString().equals("ball")) {
                EllipseMapObject ellipse = (EllipseMapObject) object;
                ball = BodyFactory.createBall(
                        (ellipse.getEllipse().x + (ellipse.getEllipse().width) / 2) * unitScale,
                        (ellipse.getEllipse().y + (ellipse.getEllipse().height) / 2) * unitScale);
                continue;
            }
            if(object.getProperties().get("entity").toString().equals("goal")) {
                RectangleMapObject rectangle = (RectangleMapObject) object;
                goal = BodyFactory.createGoal(
                        (rectangle.getRectangle().x + (rectangle.getRectangle().width / 2)) * unitScale,
                        (rectangle.getRectangle().y + (rectangle.getRectangle().height / 2)) * unitScale);
                continue;
            }
        }
    }

    private void loadObstacles() {

        for(MapObject object : obstaclesLayer.getObjects()) {

            Body body;
            float rotation = 0f;

            if (object instanceof RectangleMapObject) {

                RectangleMapObject rectangleMapObject = (RectangleMapObject) object;
                Rectangle rectangle = rectangleMapObject.getRectangle();

                if (rectangleMapObject.getProperties().get("rotation", Float.class) != null)
                    rotation = rectangleMapObject.getProperties().get("rotation", Float.class);

                body = BodyFactory.createRectangle(
                        rectangle.x* unitScale,
                        rectangle.y * unitScale,
                        rectangle.width * unitScale,
                        rectangle.height * unitScale,
                        rotation,
                        BodyDef.BodyType.StaticBody,
                        ObstacleType.SOLID);
                body.setUserData(object.getProperties().get("type"));

                obstacles.add(new RectangleObstacle(body));
            }
            else if (object instanceof CircleMapObject) {

                CircleMapObject circleMapObject = (CircleMapObject) object;
                Circle circle = circleMapObject.getCircle();

                if (circleMapObject.getProperties().get("rotation", Float.class) != null)
                    rotation = circleMapObject.getProperties().get("rotation", Float.class);

                body = BodyFactory.createCircle(
                        circle.x * unitScale,
                        circle.y * unitScale,
                        circle.radius * unitScale,
                        BodyDef.BodyType.StaticBody,
                        ObstacleType.SOLID);
                body.setUserData(object.getProperties().get("type"));

                obstacles.add(new RectangleObstacle(body));
            }
            /*
            else if (object instanceof PolygonMapObject) {
                shape = getPolygon((PolygonMapObject)object);
                position.set(
                        ((PolygonMapObject)object).getPolygon().getX() * unitScale,
                        ((PolygonMapObject)object).getPolygon().getY() * unitScale
                );

                Gdx.app.log("Polygon", position.y * unitScale + " - " + position.x * unitScale);
            }
            else if (object instanceof PolylineMapObject) {
                shape = getPolyline((PolylineMapObject)object);
                position.set(
                        ((PolylineMapObject)object).getPolyline().getX() * unitScale,
                        ((PolylineMapObject)object).getPolyline().getY() * unitScale
                );

                Gdx.app.log("Polyline", "" + position.toString());
            }
            else if (object instanceof CircleMapObject) {
                shape = getCircle((CircleMapObject)object);
                position.set(
                        ((CircleMapObject)object).getCircle().x * unitScale,
                        ((CircleMapObject)object).getCircle().y * unitScale
                );

                Gdx.app.log("Circle", "" + position.toString());
            }
            else {
                Gdx.app.log("None", "No shape found!");
                continue;
            }

            shape.dispose();
            */
        }
    }

    private void loadBackground() {

    }

    /* TODO: Do we need this in the future?
    private PolygonShape getRectangle(RectangleMapObject rectangleObject) {
        Rectangle rectangle = rectangleObject.getRectangle();
        PolygonShape polygon = new PolygonShape();
        //Vector2 size = new Vector2((rectangle.x + rectangle.width * 0.5f) * unitScale,
        //(rectangle.y + rectangle.height * 0.5f ) * unitScale);
        polygon.setAsBox(rectangle.width * 0.5f * unitScale,
                rectangle.height * 0.5f * unitScale);//,
        //size,
        //0.0f);

        Gdx.app.log("box", rectangle.width * 0.5f * unitScale + " - " + rectangle.height * 0.5f * unitScale);
        return polygon;
    }

    private CircleShape getCircle(CircleMapObject circleObject) {
        Circle circle = circleObject.getCircle();
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(circle.radius * unitScale);
        circleShape.setPosition(new Vector2(circle.x * unitScale, circle.y * unitScale));
        return circleShape;
    }

    private PolygonShape getPolygon(PolygonMapObject polygonObject) {
        PolygonShape polygon = new PolygonShape();
        float[] vertices = polygonObject.getPolygon().getTransformedVertices();

        float[] worldVertices = new float[vertices.length];

        for (int i = 0; i < vertices.length; ++i) {
            System.out.println(vertices[i]);
            worldVertices[i] = vertices[i] * unitScale;
        }

        polygon.set(worldVertices);
        return polygon;
    }

    private ChainShape getPolyline(PolylineMapObject polylineObject) {
        float[] vertices = polylineObject.getPolyline().getTransformedVertices();
        Vector2[] worldVertices = new Vector2[vertices.length / 2];

        for (int i = 0; i < vertices.length / 2; ++i) {
            worldVertices[i] = new Vector2();
            worldVertices[i].x = vertices[i * 2] * unitScale;
            worldVertices[i].y = vertices[i * 2 + 1] * unitScale;
        }

        ChainShape chain = new ChainShape();
        chain.createChain(worldVertices);
        return chain;
    }
    */
}
