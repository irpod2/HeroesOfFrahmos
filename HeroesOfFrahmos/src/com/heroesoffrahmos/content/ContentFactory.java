
package com.heroesoffrahmos.content;

import org.andengine.ui.activity.BaseGameActivity;

public class ContentFactory
{
	protected static float cameraWidth;
	protected static float cameraHeight;
	protected static BaseGameActivity activity;

	public static void init(float camWidth, float camHeight,
			BaseGameActivity bga)
	{
		cameraWidth = camWidth;
		cameraHeight = camHeight;
		activity = bga;
	}

	public static void loadContent()
	{

	}
}
