package com.beemelonstudio.lanemania.utils.mapeditor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Sort;
import com.beemelonstudio.lanemania.entities.Entity;
import com.beemelonstudio.lanemania.entities.obstacles.ArrowObstacle;
import com.beemelonstudio.lanemania.entities.obstacles.BulletObstacle;
import com.beemelonstudio.lanemania.entities.obstacles.Circle14Obstacle;
import com.beemelonstudio.lanemania.entities.obstacles.Circle18Obstacle;
import com.beemelonstudio.lanemania.entities.obstacles.CircleObstacle;
import com.beemelonstudio.lanemania.entities.obstacles.PickaxeObstacle;
import com.beemelonstudio.lanemania.entities.obstacles.RectangleObstacle;
import com.beemelonstudio.lanemania.entities.obstacles.SquareObstacle;
import com.beemelonstudio.lanemania.entities.obstacles.StoneObstacle;
import com.beemelonstudio.lanemania.entities.obstacles.TriangleObstacle;
import com.beemelonstudio.lanemania.entities.obstacles.Waypoint;
import com.beemelonstudio.lanemania.entities.types.ObstacleType;
import com.beemelonstudio.lanemania.utils.factories.BodyFactory;

import java.util.HashMap;

/**
 * Created by Jann and Cedric on 10.01.18.
 */

public class MapAnalyser {

    private float unitScale;

    private TiledMap map;
    private MapLayer objectsLayer;
    private MapLayer obstaclesLayer;
    private TiledMapTileLayer backgroundLayer;

    public PropertiesExtractor extractor;
    public HashMap<String, Object> mapProperties;

    public Body ball, goal;
    public Array<Entity> obstacles;
    public Array<Waypoint> waypoints;

    public MapAnalyser(TiledMap map, float unitScale) {
        this.map = map;
        this.unitScale = unitScale;

        extractor = new PropertiesExtractor();
        mapProperties = new HashMap<String, Object>();

        objectsLayer = map.getLayers().get("objects");
        obstaclesLayer = map.getLayers().get("obstacles");
        backgroundLayer = (TiledMapTileLayer) map.getLayers().get("background");

        obstacles = new Array<Entity>();
        waypoints = new Array<Waypoint>();
    }

    public void generateLevel() {
        loadObjects();
        loadObstacles();
        loadBackground();
        setupWaypoints();
    }

    public void loadMapProperties() {

        float star2 = map.getProperties().get("star2") != null ? (Float) map.getProperties().get("star2") : 3;
        mapProperties.put("star2", star2);

        float star3 = map.getProperties().get("star3") != null ? (Float) map.getProperties().get("star3") : 2;
        mapProperties.put("star3", star3);

        float starsNeeded = map.getProperties().get("starsNeeded") != null ? (Float) map.getProperties().get("starsNeeded") : 0;
        mapProperties.put("starsNeeded", starsNeeded);

        String preview = map.getProperties().get("preview") != null ? map.getProperties().get("preview").toString() : "w1l1";
        mapProperties.put("preview", preview);
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

            extractor.extract(object);

            if (object instanceof RectangleMapObject) {

                RectangleMapObject rectangleMapObject = (RectangleMapObject) object;
                Rectangle rectangle = rectangleMapObject.getRectangle();

                if(extractor.type.equals("rectangle")) {

                    body = BodyFactory.createRectangle(
                            rectangle.x * unitScale,
                            rectangle.y * unitScale,
                            rectangle.width * unitScale,
                            rotation,
                            BodyDef.BodyType.KinematicBody,
                            ObstacleType.SOLID);
                    //body.setUserData(type);

                    obstacles.add(new RectangleObstacle(body, extractor.name, extractor.speed, extractor.rotationSpeed, extractor.circle, extractor.timer));
                }
                else if (extractor.type.equals("square")) {

                    body = BodyFactory.createSquare(
                            rectangle.x * unitScale,
                            rectangle.y * unitScale,
                            rectangle.width * unitScale,
                            rotation,
                            BodyDef.BodyType.KinematicBody,
                            ObstacleType.SOLID);
                    //body.setUserData(type);

                    obstacles.add(new SquareObstacle(body, extractor.name, extractor.speed, extractor.rotationSpeed, extractor.circle, extractor.timer));
                }
                else if (extractor.type.equals("stone")) {

                    body = BodyFactory.createStone(
                            rectangle.x * unitScale,
                            rectangle.y * unitScale,
                            rectangle.width * unitScale,
                            rotation,
                            BodyDef.BodyType.KinematicBody,
                            ObstacleType.SOLID);
                    //body.setUserData(type);

                    obstacles.add(new StoneObstacle(body, extractor.name, rectangle.width * unitScale, extractor.speed, extractor.rotationSpeed, extractor.circle, extractor.timer));
                }
                else if (extractor.type.equals("pickaxe")) {

                    body = BodyFactory.createPickaxe(
                            rectangle.x * unitScale,
                            rectangle.y * unitScale,
                            rectangle.width * unitScale,
                            rotation,
                            BodyDef.BodyType.KinematicBody,
                            ObstacleType.SOLID);
                    //body.setUserData(type);

                    obstacles.add(new PickaxeObstacle(body, extractor.name, rectangle.width * unitScale, extractor.speed, extractor.rotationSpeed, extractor.circle, extractor.timer));
                }
                else if (extractor.type.equals("arrow")) {

                    body = BodyFactory.createArrow(
                            rectangle.x * unitScale,
                            rectangle.y * unitScale,
                            rectangle.width * unitScale,
                            rotation,
                            BodyDef.BodyType.KinematicBody,
                            ObstacleType.SOLID);
                    //body.setUserData(type);

                    obstacles.add(new ArrowObstacle(body, extractor.name, rectangle.width * unitScale, extractor.speed, extractor.rotationSpeed, extractor.circle, extractor.timer));
                }
                else if (extractor.type.equals("bullet")) {

                    body = BodyFactory.createBullet(
                            rectangle.x * unitScale,
                            rectangle.y * unitScale,
                            rectangle.width * unitScale,
                            rotation,
                            BodyDef.BodyType.KinematicBody,
                            ObstacleType.SOLID);
                    //body.setUserData(type);

                    obstacles.add(new BulletObstacle(body, extractor.name, rectangle.width * unitScale, extractor.speed, extractor.rotationSpeed, extractor.circle, extractor.timer));
                }
                else if (extractor.type.equals("waypoint")) {

                    waypoints.add(new Waypoint(extractor.obstacle, new Vector2(rectangle.x * unitScale, rectangle.y * unitScale), extractor.order, extractor.timer));
                }
            }
            else if (object instanceof EllipseMapObject) {

                //This creates a circle from the ellipse
                EllipseMapObject ellipseMapObject = (EllipseMapObject) object;
                Ellipse ellipse = ellipseMapObject.getEllipse();

                if (extractor.type.equals("circle14")) {

                    body = BodyFactory.createCircle14(
                            (ellipse.x + (ellipse.width / 2)) * unitScale,
                            (ellipse.y + (ellipse.height / 2)) * unitScale,
                            ellipse.width * unitScale,
                            rotation,
                            BodyDef.BodyType.KinematicBody,
                            ObstacleType.SOLID);
                    //body.setUserData(object.getProperties().get("type"));

                    obstacles.add(new Circle14Obstacle(body, extractor.name, ellipse.width * unitScale, extractor.speed, extractor.rotationSpeed, extractor.circle, extractor.timer));
                }
                else if (extractor.type.equals("circle18")) {

                    body = BodyFactory.createCircle18(
                            (ellipse.x + (ellipse.width / 2)) * unitScale,
                            (ellipse.y + (ellipse.height / 2)) * unitScale,
                            ellipse.width * unitScale,
                            rotation,
                            BodyDef.BodyType.KinematicBody,
                            ObstacleType.SOLID);
                    //body.setUserData(object.getProperties().get("type"));

                    obstacles.add(new Circle18Obstacle(body, extractor.name, ellipse.width * unitScale, extractor.speed, extractor.rotationSpeed, extractor.circle, extractor.timer));
                }
                else if (extractor.type.equals("circle")) {

                    body = BodyFactory.createCircle(
                            (ellipse.x + (ellipse.width / 2)) * unitScale,
                            (ellipse.y + (ellipse.height / 2)) * unitScale,
                            ellipse.width * unitScale,
                            BodyDef.BodyType.KinematicBody,
                            ObstacleType.SOLID);
                    //body.setUserData(object.getProperties().get("type"));

                    obstacles.add(new CircleObstacle(body, extractor.name, extractor.speed, extractor.rotationSpeed, extractor.circle, extractor.timer));
                }
            }
            else if (object instanceof PolygonMapObject) {

                PolygonMapObject polygonMapObjectMapObject = (PolygonMapObject) object;
                Polygon polygon = polygonMapObjectMapObject.getPolygon();

                // Adjust all vertices to box2d dimension
                float[] vertices = polygon.getVertices();
                for(int i = 0; i < vertices.length; i++)
                    vertices[i] *= unitScale;

                body = BodyFactory.createTriangle(
                        polygon.getX() * unitScale,
                        polygon.getY() * unitScale,
                        polygon.getVertices(),
                        BodyDef.BodyType.KinematicBody,
                        ObstacleType.SOLID);
                //body.setUserData(object.getProperties().get("type"));

                obstacles.add(new TriangleObstacle(body, extractor.name, extractor.speed, extractor.rotationSpeed, extractor.circle, extractor.timer));
            }
            /*
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

    /**
     * Sets up waypoints after all other objects are created to prevent OrderExceptions
     */
    private void setupWaypoints() {

        for(Waypoint waypoint : waypoints) {

            for(Entity e : obstacles) {

                // If an obstacle has a corresponding way point, add it to it
                if (e.name != null && e.name.equals(waypoint.obstacle)) {

                    // Add the obstacle's starting position as the first waypoint
                    if(e.waypoints.size == 0)
                        e.waypoints.add(new Waypoint(e.name, e.origin, 0, e.timer));

                    e.waypoints.add(waypoint);
                }
            }
        }
        for(Entity e : obstacles) {
            e.waypoints.sort();
        }
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
