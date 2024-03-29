
package com.heroesoffrahmos.scenario.transition;


public interface ITransitionHandler
{
	public static final float FADE_TIME = 1.0f;
	public static final float TOTAL_TIME = 2 * FADE_TIME;

	public void start();

	public boolean isComplete();
}
