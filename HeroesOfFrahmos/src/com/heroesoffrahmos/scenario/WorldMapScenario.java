
package com.heroesoffrahmos.scenario;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.Scene;
import org.andengine.ui.activity.BaseGameActivity;

import com.heroesoffrahmos.map.WorldMap;
import com.heroesoffrahmos.util.SmartScene;
import com.heroesoffrahmos.util.SmartScene.Layer;

public class WorldMapScenario implements IScenario
{
	private BaseGameActivity activity;
	private SmartScene scene;
	private WorldMap worldMap;

	public WorldMapScenario(BaseGameActivity bga, Camera cam, SmartScene myScene)
	{
		activity = bga;
		scene = myScene;

		worldMap = new WorldMap(activity, cam);
	}

	@Override
	public void prepareStart()
	{
		scene.attachChild(Layer.BACKGROUND, worldMap);
		scene.registerTouchArea(worldMap);
	}

	@Override
	public void start()
	{

	}

	@Override
	public void prepareEnd()
	{
		scene.detachChild(Layer.BACKGROUND, worldMap);
	}

	@Override
	public void end()
	{

	}

	@Override
	public Scene getScene()
	{
		return scene;
	}

}
