package gc.com.gcmapapp.application;

import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import com.baidu.mapapi.SDKInitializer;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;

import gc.com.gcmapapp.R;
import gc.com.gcmapapp.http.Url;
import gc.com.gcmapapp.utils.DeviceUtils;
import gc.com.gcmapapp.utils.SharePreferenceUtil;

public class MapApplication extends Application {


	public static final String FILE_DIR = Environment
			.getExternalStorageDirectory().getAbsolutePath() + "/map/";
	public static final String CACHE_DIR = FILE_DIR + "/cache/";

	@Override
	public void onCreate() {
		super.onCreate();
		SDKInitializer.initialize(getApplicationContext());
		String phoneId = DeviceUtils.getUniqueId(getApplicationContext());
		SharePreferenceUtil.put(getApplicationContext(), Constants.PHONE_ID, phoneId);
		// init image loader
		initImageLoader(this);
		String host = (String) SharePreferenceUtil.get(getApplicationContext(), Constants.HOST, "");
		if(TextUtils.isEmpty(host)){
			SharePreferenceUtil.put(getApplicationContext(), Constants.HOST, Url.HOST);
			Url.BASE_URL = getApplicationContext().getString(R.string.base_url, Url.HOST);
		}else{
			Url.BASE_URL = getApplicationContext().getString(R.string.base_url, host);
		}

	}

	public  void initImageLoader(Context context) {

		File cacheDir = StorageUtils.getOwnCacheDirectory(context,
				CACHE_DIR);
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context).threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.diskCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.writeDebugLogs().memoryCacheSize(8 * 1024 * 1024)
				.diskCacheSize(50 * 1024 * 1024)
				.diskCache(new UnlimitedDiskCache(cacheDir))
				.diskCacheFileNameGenerator(new HashCodeFileNameGenerator())
				.build();

		ImageLoader.getInstance().init(config);
	}

}