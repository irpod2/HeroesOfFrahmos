
package com.heroesoffrahmos.util.content;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;

import com.heroesoffrahmos.map.path.Waypoint;

public class SpriteFactory extends ContentFactory
{
	public static void loadContent()
	{
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("sprites/");

		MapFactory.loadContent();
	}

	public static Rectangle createMapSprite(Waypoint curWp)
	{
		Rectangle spriteRect = new Rectangle(curWp.x, curWp.y, 20, 40, activity.getVertexBufferObjectManager());
		return spriteRect;
	}
}
