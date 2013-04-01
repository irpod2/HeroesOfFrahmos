
package com.heroesoffrahmos.scenario;

import org.andengine.entity.scene.Scene;


public interface IScenario
{
	public void prepareStart();

	public void start();

	public void prepareEnd();

	public void end();

	public Scene getScene();
}
