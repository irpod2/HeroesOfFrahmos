
package com.heroesoffrahmos.scenario.transition;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.util.color.Color;

import com.heroesoffrahmos.scenario.IScenario;
import com.heroesoffrahmos.util.CallbackVoid;

public class FadeInHandler implements ITransitionHandler, IUpdateHandler
{
	protected BaseGameActivity activity;
	protected IScenario scenario;
	protected Rectangle fadeBox;
	protected HUD hud;
	protected CallbackVoid onFinishedCallback;
	protected float totalTimeElapsed;
	protected boolean complete = false;

	public FadeInHandler(BaseGameActivity bga, Camera cam, IScenario scno,
			CallbackVoid onFinishedCB)
	{
		activity = bga;
		scenario = scno;
		onFinishedCallback = onFinishedCB;
		hud = cam.getHUD();
		fadeBox = new Rectangle(0, 0, cam.getWidth(), cam.getHeight(),
				activity.getVertexBufferObjectManager());
		fadeBox.setColor(Color.BLACK);
		fadeBox.setAlpha(1.0f);
		totalTimeElapsed = 0;
	}

	public void start()
	{
		hud.attachChild(fadeBox);
		scenario.getScene().registerUpdateHandler(this);
		scenario.prepareStart();
	}

	public boolean isComplete()
	{
		return complete;
	}

	@Override
	public void onUpdate(float pSecondsElapsed)
	{
		totalTimeElapsed += pSecondsElapsed;
		if (totalTimeElapsed < FADE_TIME)
		{
			fadeBox.setAlpha(1.0f - (totalTimeElapsed / FADE_TIME));
		}
		else
		{
			fadeBox.setAlpha(0.0f);
			hud.detachChild(fadeBox);
			scenario.getScene().unregisterUpdateHandler(this);
			scenario.start();
			complete = true;
			onFinishedCallback.onCallback();
		}

	}

	@Override
	public void reset()
	{
		if (fadeBox != null)
		{
			fadeBox.setAlpha(1.0f);
			totalTimeElapsed = 0;
		}
		else if (onFinishedCallback != null)
		{
			onFinishedCallback.onCallback();
		}
	}
}
