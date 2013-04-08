
package com.heroesoffrahmos.util.content;

import org.andengine.entity.text.Text;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.util.color.Color;

public class TextFactory extends ContentFactory
{
	public static final int MAX_CHARACTERS = 50;
	public static float FONT_SIZE = 36.0f;
	private static Font impact;
	private static Font lucida;

	public static void loadContent()
	{
		FONT_SIZE *= SIZE_RATIO;

		FontFactory.setAssetBasePath("fonts/");

		// Lucida
		lucida = FontFactory.createFromAsset(activity.getFontManager(),
				activity.getTextureManager(), 256, 256, activity.getAssets(),
				"lucida.ttf", FONT_SIZE, true, Color.WHITE_ARGB_PACKED_INT);
		lucida.load();

		// Impact
		impact = FontFactory.createFromAsset(activity.getFontManager(),
				activity.getTextureManager(), 256, 256, activity.getAssets(),
				"impact.ttf", FONT_SIZE, true, Color.WHITE_ARGB_PACKED_INT);
		impact.load();
	}

	public static Text createText(float pX, float pY, String message,
			float scale)
	{
		Text txt = null;
		try
		{
			txt = new Text(pX, pY, lucida, message, MAX_CHARACTERS,
					activity.getVertexBufferObjectManager());
		}
		// when string is longer than MAX_CHARACTERS, exception is thrown
		catch (Exception e)
		{
			// So just take the substring under the max
			txt = new Text(pX, pY, lucida,
					message.substring(0, MAX_CHARACTERS), MAX_CHARACTERS,
					activity.getVertexBufferObjectManager());
			// But print the stack trace anyway
			e.printStackTrace();
		}
		txt.setScaleCenter(0, 0);
		txt.setScale(scale);
		return txt;
	}

	public static Text createSimpleText(String message)
	{
		return createText(0, 0, message, 1.0f);
	}
}
