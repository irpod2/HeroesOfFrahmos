
package com.heroesoffrahmos;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.util.FPSLogger;
import org.andengine.ui.activity.BaseGameActivity;

import android.annotation.SuppressLint;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

import com.heroesoffrahmos.scenario.IScenario;
import com.heroesoffrahmos.scenario.WorldMapScenario;
import com.heroesoffrahmos.scenario.transition.ComposedTransitionHandler;
import com.heroesoffrahmos.scenario.transition.FadeInHandler;
import com.heroesoffrahmos.scenario.transition.ITransitionHandler;
import com.heroesoffrahmos.util.CallbackVoid;
import com.heroesoffrahmos.util.SmartScene;
import com.heroesoffrahmos.util.content.ContentFactory;

public class HeroesActivity extends BaseGameActivity
{
	public static final float DEFAULT_CAMERA_WIDTH = 800.0f;
	public static final float DEFAULT_CAMERA_HEIGHT = 480.0f;
	public static float cameraWidth;
	public static float cameraHeight;

	private Camera camera;
	private HUD hud;
	private SmartScene worldMapScene;

	// Scenarios/Transitions
	private IScenario currentScenario;
	private IScenario nextScenario;
	private ITransitionHandler transitionHandler;

	@SuppressWarnings("deprecation")
	@SuppressLint("NewApi")
	@Override
	public EngineOptions onCreateEngineOptions()
	{
		Display display = ((WindowManager) getSystemService(WINDOW_SERVICE))
				.getDefaultDisplay();

		// For Android API 13+ getWidth() and getHeight() are deprecated
		if (android.os.Build.VERSION.SDK_INT >= 13)
		{
			Point size = new Point();
			display.getSize(size);
			cameraWidth = size.x;
			cameraHeight = size.y;
		}
		// For APIs < 13, getSize(Point) doesn't exist. Nice forethought,
		// Android.
		else
		{
			cameraWidth = display.getWidth();
			cameraHeight = display.getHeight();
		}
		// If screen is oriented the wrong way
		if (cameraHeight > cameraWidth)
		{
			float temp = cameraHeight;
			cameraHeight = cameraWidth;
			cameraWidth = temp;
		}

		camera = new Camera(0, 0, cameraWidth, cameraHeight);

		return new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED,
				new RatioResolutionPolicy(cameraWidth, cameraHeight), camera);
	}

	@Override
	public void onCreateResources(
			OnCreateResourcesCallback pOnCreateResourcesCallback)
			throws Exception
	{
		ContentFactory.init(cameraWidth, cameraHeight, this);
		ContentFactory.loadContent();

		pOnCreateResourcesCallback.onCreateResourcesFinished();
	}

	@Override
	public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback)
			throws Exception
	{
		// logs the frame rate
		this.mEngine.registerUpdateHandler(new FPSLogger());
		hud = new HUD();
		camera.setHUD(hud);

		worldMapScene = new SmartScene();

		currentScenario = new WorldMapScenario(this, camera, worldMapScene);

		pOnCreateSceneCallback.onCreateSceneFinished(worldMapScene);
	}

	@Override
	public void onPopulateScene(Scene pScene,
			OnPopulateSceneCallback pOnPopulateSceneCallback) throws Exception
	{
		transitionHandler = new FadeInHandler(this, camera, currentScenario,
				doNothingCallback);
		transitionHandler.start();

		pOnPopulateSceneCallback.onPopulateSceneFinished();
	}

	// ===========================================================================
	// Control Flow via CallbackVoids
	// ===========================================================================

	private CallbackVoid doNothingCallback = new CallbackVoid()
	{
		@Override
		public void onCallback()
		{}
	};


	private CallbackVoid switchScenarioCallback = new CallbackVoid()
	{
		@Override
		public void onCallback()
		{
			currentScenario = nextScenario;
		}
	};

	private CallbackVoid prepareWorldMapCallback = new CallbackVoid()
	{
		@Override
		public void onCallback()
		{
			nextScenario = new WorldMapScenario(HeroesActivity.this, camera,
					worldMapScene);
			transitionHandler = new ComposedTransitionHandler(
					HeroesActivity.this, camera, currentScenario, nextScenario,
					switchScenarioCallback);
			transitionHandler.start();
		}
	};

	// ===========================================================================
	//
	// ===========================================================================
}
