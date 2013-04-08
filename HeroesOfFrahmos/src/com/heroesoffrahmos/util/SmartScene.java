
package com.heroesoffrahmos.util;

import java.util.ArrayList;

import org.andengine.entity.Entity;
import org.andengine.entity.IEntity;
import org.andengine.entity.scene.Scene;

public class SmartScene extends Scene
{
	public enum Layer
	{
		BACKGROUND,
		OBJECTS_IN_BACKGROUND,
		BEHIND_ACTORS,
		ACTORS,
		ABOVE_ACTORS
	}

	private ArrayList<Entity> layers;

	public SmartScene()
	{
		super();
		layers = new ArrayList<Entity>();
		for (int i = 0; i < Layer.values().length; i++)
		{
			Entity layer = new Entity();
			layers.add(layer);
			attachChild(layer);
		}
	}

	public void attachChild(Layer layer, IEntity child)
	{
		layers.get(layer.ordinal()).attachChild(child);
	}

	public void detachChild(Layer layer, IEntity child)
	{
		layers.get(layer.ordinal()).detachChild(child);
	}
}
