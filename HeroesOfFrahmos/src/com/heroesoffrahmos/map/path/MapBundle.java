
package com.heroesoffrahmos.map.path;

import java.util.ArrayList;

public class MapBundle
{
	private final float CLOSENESS_THRESHOLD = 20.0f;
	// Basically just a container for paths that is also serializable
	public ArrayList<MapPath> paths;

	public MapBundle()
	{
		paths = new ArrayList<MapPath>();
	}

	public MapBundle(ArrayList<MapPath> p)
	{
		paths = p;
	}

	public String serialize()
	{
		String me = "$";
		for (MapPath p : paths)
		{
			me += p.serialize();
		}
		me += "$";
		return me;
	}

	public static MapBundle createFromString(String bundleString)
	{
		if (bundleString.length() > 1 && bundleString.charAt(0) == '$')
		{
			ArrayList<MapPath> paths = new ArrayList<MapPath>();
			String pathString = bundleString.substring(1);
			while (pathString.length() > 0 && pathString.charAt(0) == '<')
			{
				int closeBracket = pathString.indexOf('>') + 1;
				MapPath p = MapPath.createFromString(pathString.substring(0,
						closeBracket));
				if (p != null)
					paths.add(p);
				else
					break;
				pathString = pathString.substring(closeBracket);
			}
			if (pathString.charAt(0) == '$')
			{
				MapBundle bundle = new MapBundle(paths);
				return bundle;
			}
		}
		return null;
	}

	public int size()
	{
		return paths.size();
	}

	public MapPath getPath(int pathId)
	{
		return paths.get(pathId);
	}

	public Waypoint getClosestWaypoint(float x, float y)
	{
		Waypoint closest = null;
		float closestDist = Float.MAX_VALUE;
		for (MapPath path : paths)
		{
			Waypoint wp = path.getClosestWaypoint(x, y);
			float dist = (float) Math.hypot(x - wp.x, y - wp.y);
			if (dist < closestDist)
			{
				closestDist = dist;
				closest = wp;
			}
		}
		if (closestDist < CLOSENESS_THRESHOLD)
			return closest;
		else
			return null;
	}
}
