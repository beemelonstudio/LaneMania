package com.beemelonstudio.lanemania.utils.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ObjectMap;

import java.util.HashMap;

/**
 * Created by Jann on 09.01.2018.
 */

public class Assets {

    private static final AssetManager assetManager = new AssetManager();

    private static HashMap<String, AssetFile> files;

    public static TextureAtlas generalTextureAtlas;
    public static TextureAtlas currentWorldTextureAtlas;

    public static Preferences preferences;

    public static BitmapFont font;
    public static BitmapFont starFont;

    public static String VERT_1;
    public static String FRAG_1;
    public static String VERT_BLUR;
    public static String FRAG_BLUR;

    public static void load(){

        //Enter all files from the assets folder in here
        //**Note that this is a very bad way to handle multiple files**
        files = new HashMap<String, AssetFile>();

        //TextureAtlases
        files.put("general-theme",  new AssetFile("sprites/general-theme/general-theme.atlas",   TextureAtlas.class));
        files.put("wildwest-theme", new AssetFile("sprites/wildwest-theme/wildwest-theme.atlas", TextureAtlas.class));

        //Fonts
        //files.put("passion-one", new AssetFile("skins/beemelon/passion-one.fnt", BitmapFont.class));
        //files.put("passion-oneTTF", new AssetFile("skins/beemelon/passion-one.ttf", TrueT.class));

        //Skins
        files.put("beemelonSkin", new AssetFile("skins/beemelon/skin.json", Skin.class));

        //Sounds
        files.put("backgroundMenuMusic", new AssetFile("sounds/backgroundExample.mp3", Music.class));

        //Images
        //files.put("backgroundW1",   new AssetFile("images/background_w1.jpg",   Texture.class));

        //I18Ns
        //files.put("defaultI18N",     new AssetFile("i18N/prototype",  I18NBundle.class));

        //Loading files
        for(AssetFile asset : files.values()){
            assetManager.load(asset.path, asset.type);
        }

        assetManager.finishLoading();
        //while(!assetManager.update()){} TODO: Decide for one method

        preferences = Gdx.app.getPreferences("Lanemania");

        font = generateFont();
        starFont = generateStarFont();

        Assets.generalTextureAtlas = (TextureAtlas) Assets.get("general-theme");
        Assets.currentWorldTextureAtlas = (TextureAtlas) Assets.get("wildwest-theme");

        VERT_1 = "attribute vec4 "+ShaderProgram.POSITION_ATTRIBUTE+";\n" +
                    "attribute vec4 "+ShaderProgram.COLOR_ATTRIBUTE+";\n" +
                    "attribute vec2 "+ShaderProgram.TEXCOORD_ATTRIBUTE+"0;\n" +

                    "uniform mat4 u_projTrans;\n" +
                    " \n" +
                    "varying vec4 vColor;\n" +
                    "varying vec2 vTexCoord;\n" +

                    "void main() {\n" +
                    "	vColor = "+ShaderProgram.COLOR_ATTRIBUTE+";\n" +
                    "	vTexCoord = "+ShaderProgram.TEXCOORD_ATTRIBUTE+"0;\n" +
                    "	gl_Position =  u_projTrans * " + ShaderProgram.POSITION_ATTRIBUTE + ";\n" +
                    "}";

        FRAG_1 = "#ifdef GL_ES\n" //
                + "#define LOWP lowp\n" //
                + "precision mediump float;\n" //
                + "#else\n" //
                + "#define LOWP \n" //
                + "#endif\n" + //
                "//texture 0\n" +
                "uniform sampler2D u_texture;\n" +
                "uniform vec2 resolution;\n" +
                "varying LOWP vec4 vColor;\n" +
                "varying vec2 vTexCoord;\n" +
                "\n" +
                "const float RADIUS = 0.75;\n" +
                "const float SOFTNESS = 0.45;\n" +
                "const vec3 SEPIA = vec3(1.2, 1.0, 0.8); \n" +
                "\n" +
                "void main() {\n" +
                "	vec4 texColor = texture2D(u_texture, vTexCoord);\n" +
                "	vec2 position = (gl_FragCoord.xy / resolution.xy) - vec2(0.5);\n" +
                "	float len = length(position);\n" +
                "	\n" +
                "	float vignette = smoothstep(RADIUS, RADIUS-SOFTNESS, len);\n" +
                "	\n" +
                "	texColor.rgb = mix(texColor.rgb, texColor.rgb * vignette, 0.5);\n" +
                " 	\n" +
                "	float gray = dot(texColor.rgb, vec3(0.299, 0.587, 0.114));\n" +
                "	\n" +
                "	vec3 sepiaColor = vec3(gray) * SEPIA;\n" +
                "   \n" +
                "	texColor.rgb = mix(texColor.rgb, sepiaColor, 0.75);\n" +
                "	\n" +
                "	gl_FragColor = texColor * vColor;\n" +
                "}";
    }

    public static Object get(String hashmapKey){
        return assetManager.get(files.get(hashmapKey).path, files.get(hashmapKey).type);
    }

    private static BitmapFont generateFont(){
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("skins/beemelon/passion-one-regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = (int) (38 * Gdx.graphics.getDensity());
        parameter.color = new Color(255f / 255f, 165f / 255f, 23f / 255f, 1f); // Divide by 255f to get a value between 0 and 1
        parameter.borderWidth = parameter.size / 10f;
        parameter.borderColor = Color.BLACK;
        BitmapFont bitmapFont = generator.generateFont(parameter);
        generator.dispose();
        return bitmapFont;
    }

    private static BitmapFont generateStarFont(){
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("skins/beemelon/passion-one-regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = (int) (38 * Gdx.graphics.getDensity());
        parameter.color = new Color(255f / 255f, 165f / 255f, 23f / 255f, 1f); // Divide by 255f to get a value between 0 and 1
        parameter.borderWidth = parameter.size / 10f;
        parameter.borderColor = Color.YELLOW;
        BitmapFont bitmapFont = generator.generateFont(parameter);
        generator.dispose();
        return bitmapFont;
    }

    /*
    private static void generateFont() {

        // Load and generate font
        FileHandleResolver resolver = new InternalFileHandleResolver();
        assetManager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
        assetManager.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));
        FreetypeFontLoader.FreeTypeFontLoaderParameter parameter = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        parameter.fontFileName = "skins/beemelon/passion-one-regular.ttf";
        parameter.fontParameters.size = 50;
        parameter.fontParameters.color = Color.BLACK;
        files.put("font", new AssetFile("font.ttf", BitmapFont.class));
        assetManager.load("font.ttf", BitmapFont.class, parameter);
        assetManager.finishLoading();

        // Add fonts to ObjectMap
        BitmapFont font = assetManager.get("font.ttf", BitmapFont.class);
        ObjectMap<String, Object> fontMap = new ObjectMap<String, Object>();
        fontMap.put("font-export.fnt", font);
        fontMap.put("title", font);

        SkinLoader.SkinParameter skinParameter = new SkinLoader.SkinParameter(fontMap);

        files.put("beemelonSkin", new AssetFile("skins/beemelon/skin.json", Skin.class));
        assetManager.load("skins/beemelon/skin.json", Skin.class, skinParameter);
        assetManager.finishLoading();
        /*
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("skins/beemelon/passion-one-regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 24;
        BitmapFont font = generator.generateFont(parameter);
        generator.dispose();

        Skin skin = new Skin();
        skin.add("font", font, BitmapFont.class);
        skin.add("title", font, BitmapFont.class);

        //Skin originalSkin = (Skin) get("beemelonSkin");
        skin.load(Gdx.files.internal(files.get("beemelonSkin").path));

        /*
        com.badlogic.gdx.graphics.g2d.BitmapFont: {
	font: {
		file: font-export.fnt
	}
	title: {
		file: font-title-export.fnt
	}
}

    }
    */

    public static void dispose(){
        assetManager.dispose();
    }
}
