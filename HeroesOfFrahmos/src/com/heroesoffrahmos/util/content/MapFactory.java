
package com.heroesoffrahmos.util.content;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;

public class MapFactory extends SpriteFactory
{
	public static void loadContent()
	{

	}

	public static Sprite createWorldMap()
	{
		BitmapTextureAtlas mapAtlas = new BitmapTextureAtlas(
				activity.getTextureManager(), 2048, 2048);
		TextureRegion mapRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(mapAtlas, activity.getAssets(),
						"WorldMap.png", 0, 0);
		mapAtlas.load();
		Sprite mapSprite = new Sprite(0, 0, mapRegion,
				activity.getVertexBufferObjectManager());
		return mapSprite;
	}
}
