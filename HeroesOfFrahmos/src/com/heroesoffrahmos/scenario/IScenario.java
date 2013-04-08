
package com.heroesoffrahmos.scenario;

import org.andengine.entity.scene.Scene;


public interface IScenario
{
	// Called before fading in. Register any animation handlers
	public void prepareStart();

	// Full control is granted: register control handlers
	public void start();

	// Unregister any control handlers. Called before fading out
	public void prepareEnd();

	// Unregister any remaining handlers: screen is black
	public void end();

	// Scene (for transitioning)
	public Scene getScene();
}
