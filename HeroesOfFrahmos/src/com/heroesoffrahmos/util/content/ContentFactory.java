
package com.heroesoffrahmos.util.content;

import org.andengine.ui.activity.BaseGameActivity;

import com.heroesoffrahmos.HeroesActivity;

public class ContentFactory
{
	public static final String MAP_SUBDIR = "maps/";
	public static final String WORLD_MAP_FILENAME = "worldmap.map";
	public static float SIZE_RATIO;
	protected static float cameraWidth;
	protected static float cameraHeight;
	protected static BaseGameActivity activity;

	public static void init(float camWidth, float camHeight,
			BaseGameActivity bga)
	{
		cameraWidth = camWidth;
		cameraHeight = camHeight;
		SIZE_RATIO = HeroesActivity.DEFAULT_CAMERA_WIDTH
				/ HeroesActivity.cameraWidth;
		activity = bga;
	}

	public static void loadContent()
	{

		SpriteFactory.loadContent();
		TextFactory.loadContent();
	}
}
