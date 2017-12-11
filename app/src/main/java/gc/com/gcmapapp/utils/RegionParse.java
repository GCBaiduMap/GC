package gc.com.gcmapapp.utils;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import gc.com.gcmapapp.bean.LocationInfo;


public class RegionParse {


	public static String getJson(Context context) {

		StringBuilder stringBuilder = new StringBuilder();
		try {
			AssetManager assetManager = context.getAssets();
			BufferedReader bf = new BufferedReader(new InputStreamReader(
					assetManager.open("region.json")));
			String line;
			while ((line = bf.readLine()) != null) {
				stringBuilder.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return stringBuilder.toString();
	}

	public static List<LocationInfo>  getRegionBean(Context context){
		return GsonUtils.parseJsonArrayWithGson(getJson(context), LocationInfo[].class);
	}


}
