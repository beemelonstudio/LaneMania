package com.beemelonstudio.lanemania.utils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.FloatArray;

/**
 * Created by Stampler on 09.01.2018
 */

public class VectorUtils {

    public static Array<Vector2> floatArrayToVector2(FloatArray vertices) {

        Array<Vector2> result = new Array<Vector2>();
        for (int i = 0; i < vertices.size/2; i++) {
            float x = vertices.get(i * 2);
            float y = vertices.get(i * 2 + 1);
            result.add(new Vector2(x, y));
        }
        return result;
    }

    public static FloatArray vector2ToFloatArray(Array<Vector2> vectors) {

        FloatArray result = new FloatArray();
        for (int i = 0; i < vectors.size; i++) {
            result.add(vectors.get(i).x);
            result.add(vectors.get(i).y);
        }
        return result;
    }

    public static Vector2[] libgdxArrayToJavaArray(Array<Vector2> arrayLibGDX) {

        Vector2[] arrayJava = new Vector2[arrayLibGDX.size];
        for(int i = 0; i<arrayLibGDX.size; i++) {
            arrayJava[i] = arrayLibGDX.get(i);
        }

        return arrayJava;
    }
}
