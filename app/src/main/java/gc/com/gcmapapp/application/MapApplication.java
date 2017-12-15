package gc.com.gcmapapp.application;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;

import gc.com.gcmapapp.utils.DeviceUtils;
import gc.com.gcmapapp.utils.SharePreferenceUtil;

public class MapApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		SDKInitializer.initialize(getApplicationContext());
		String phoneId = DeviceUtils.getUniqueId(getApplicationContext());
		SharePreferenceUtil.put(getApplicationContext(), Constants.PHONE_ID, phoneId);
	}

}