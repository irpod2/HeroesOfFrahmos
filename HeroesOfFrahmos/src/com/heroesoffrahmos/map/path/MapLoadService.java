
package com.heroesoffrahmos.map.path;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.Context;
import android.content.res.AssetManager;

public class MapLoadService
{
	// Loads paths from designated file
	public static MapBundle loadMap(Context context, String filename)
	{
		MapBundle bundle = null;
		BufferedReader reader = null;
		try
		{
			AssetManager assets = context.getAssets();
			InputStream sin = assets.open(filename);
			reader = new BufferedReader(new InputStreamReader(sin));
			String line = null;
			StringBuilder stringBuilder = new StringBuilder();
			String ls = System.getProperty("line.separator");
			while ((line = reader.readLine()) != null)
			{
				stringBuilder.append(line);
				stringBuilder.append(ls);
			}

			bundle = MapBundle.createFromString(stringBuilder.toString());
		}
		catch (FileNotFoundException fnfe)
		{
			fnfe.printStackTrace();
			/*
			 * Toast.makeText( context,
			 * "Heroes of Frahmos has been corrupted. Please reinstall a fresh copy from the Google Playstore"
			 * , Toast.LENGTH_SHORT).show();
			 */
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (reader != null)
					reader.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		return bundle;
	}
}
