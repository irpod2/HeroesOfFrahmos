
package com.heroesoffrahmos.map;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.sprite.Sprite;

import com.heroesoffrahmos.map.path.Waypoint;
import com.heroesoffrahmos.util.content.ContentFactory;
import com.heroesoffrahmos.util.content.SpriteFactory;

public class MapWalker implements IUpdateHandler
{
	private final float SPEED = ContentFactory.SIZE_RATIO * 80.0f;
	private Rectangle sprite;
	private Waypoint curWp;
	private Waypoint destWp;
	private Waypoint nextWp;
	private float timeElapsed = 0.0f;
	private float timeToArrive = Float.MAX_VALUE;
	private float xDist;
	private float yDist;

	public MapWalker(Waypoint location, Sprite map)
	{
		curWp = location;
		destWp = location;
		nextWp = location;
		sprite = SpriteFactory.createMapSprite(curWp);
		map.attachChild(sprite);
		setNextWaypoint();
	}

	public void setDestination(Waypoint dest)
	{
		destWp = dest;
		sprite.setPosition(curWp.x - sprite.getWidthScaled() / 2.0f, curWp.y
				- sprite.getHeightScaled());
		setNextWaypoint();
	}

	private void setNextWaypoint()
	{
		curWp = nextWp;
		// TODO: REMOVE!
		nextWp = destWp;
		if (nextWp == curWp)
			return;
		xDist = nextWp.x - curWp.x;
		yDist = nextWp.y - curWp.y;
		float magnitude = (float) Math.hypot(xDist, yDist);
		timeToArrive = magnitude / SPEED;
		timeElapsed = 0.0f;
	}

	@Override
	public void onUpdate(float pSecondsElapsed)
	{
		if (nextWp != curWp)
		{
			timeElapsed += pSecondsElapsed;
			if (timeElapsed >= timeToArrive)
			{
				sprite.setX(nextWp.x - sprite.getWidthScaled() / 2.0f);
				sprite.setY(nextWp.y - sprite.getHeightScaled());
				setNextWaypoint();
			}
			else
			{
				sprite.setX(curWp.x - sprite.getWidthScaled() / 2.0f + xDist
						* timeElapsed / timeToArrive);
				sprite.setY(curWp.y - sprite.getHeightScaled() + yDist
						* timeElapsed / timeToArrive);
			}
		}
	}

	@Override
	public void reset()
	{
		sprite.setPosition(curWp.x - sprite.getWidthScaled() / 2.0f, curWp.y
				- sprite.getHeightScaled());
	}
}
