
package com.heroesoffrahmos.map;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.Entity;
import org.andengine.entity.scene.ITouchArea;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.ui.activity.BaseGameActivity;

import android.os.SystemClock;

import com.heroesoffrahmos.map.path.MapBundle;
import com.heroesoffrahmos.map.path.MapLoadService;
import com.heroesoffrahmos.map.path.MapPath;
import com.heroesoffrahmos.map.path.Waypoint;
import com.heroesoffrahmos.util.content.ContentFactory;
import com.heroesoffrahmos.util.content.MapFactory;

public class WorldMap extends Entity implements ITouchArea
{
	private static final long TAP_TIME = 200L;
	private static final float DRAG_THRESHOLD = 10.0f;

	private static MapBundle worldMapBundle;
	private Sprite mapSprite;
	private Camera camera;
	private MapWalker mapWalker;

	private boolean dragging = false;
	private long downTime = 0L;
	private float firstX;
	private float firstY;
	private float lastX;
	private float lastY;
	private float minX;
	private float minY;
	private float maxX;
	private float maxY;

	public WorldMap(BaseGameActivity activity, Camera cam)
	{
		super();
		camera = cam;

		if (worldMapBundle == null)
		{
			worldMapBundle = MapLoadService.loadMap(activity,
					ContentFactory.MAP_SUBDIR
							+ ContentFactory.WORLD_MAP_FILENAME);
		}


		mapSprite = MapFactory.createWorldMap();
		attachChild(mapSprite);

		minX = -mapSprite.getWidthScaled() + camera.getWidth();
		minY = -mapSprite.getHeightScaled() + camera.getHeight();
		maxX = 0.0f;
		maxY = 0.0f;

		/*
		 * Temporarily displaying blue rectangle in random location on map
		 * instead of character sprite
		 */
		MapPath path0 = worldMapBundle.getPath(0);
		Waypoint startingWp = path0.getWaypoint((int) (Math.random() * path0
				.size()));
		mapWalker = new MapWalker(startingWp, mapSprite);
		mapSprite.registerUpdateHandler(mapWalker);
	}

	@Override
	public boolean contains(float pX, float pY)
	{
		return (mapSprite != null && mapSprite.contains(pX, pY));
	}

	@Override
	public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float x, float y)
	{
		switch (pSceneTouchEvent.getAction())
		{
		case TouchEvent.ACTION_DOWN:
			downTime = SystemClock.elapsedRealtime();
			firstX = x;
			firstY = y;
			lastX = x;
			lastY = y;
			break;
		case TouchEvent.ACTION_MOVE:
			float dx = x - lastX;
			float dy = y - lastY;
			if (!dragging
					&& Math.hypot(x - firstX, y - firstY) > DRAG_THRESHOLD)
				dragging = true;
			if (dragging)
			{
				float nextX = mapSprite.getX() + dx;
				float nextY = mapSprite.getY() + dy;
				nextX = Math.max(minX, Math.min(maxX, nextX));
				nextY = Math.max(minY, Math.min(maxY, nextY));
				mapSprite.setX(nextX);
				mapSprite.setY(nextY);
			}
			lastX = x;
			lastY = y;
			break;
		case TouchEvent.ACTION_UP:
			long time = SystemClock.elapsedRealtime();
			long dt = time - downTime;
			float wpX = x - mapSprite.getX();
			float wpY = y - mapSprite.getY();
			if (!dragging && dt < TAP_TIME)
			{
				Waypoint dest = worldMapBundle.getClosestWaypoint(wpX, wpY);
				if (dest != null)
					mapWalker.setDestination(dest);
			}
			dragging = false;
			break;
		default:
		}
		return true;
	}
}
