package gc.com.gcmapapp.utils;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

public class ImageLoaderUtil {
	private static final String TAG = "ImageLoaderUtil";
	private DisplayImageOptions mOptions;

	public ImageLoaderUtil() {
		mOptions = new DisplayImageOptions.Builder().showImageOnLoading(null)
				.showImageForEmptyUri(null).showImageOnFail(null)
				.cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
				.imageScaleType(ImageScaleType.IN_SAMPLE_INT)
				.bitmapConfig(Config.RGB_565).build();
	}

	public void displayImage(final String url, final ImageView full_image,
							 final ImageLoader imageloader) {

		if (url.equals("https:null")) {
			return;
		}

		imageloader.displayImage(url, full_image, mOptions,
				new ImageLoadingListener() {

					@Override
					public void onLoadingStarted(String imageUri, View view) {
					}

					@Override
					public void onLoadingFailed(final String imageUri,
												final View view, FailReason failReason) {
						ImageView imageView = (ImageView) view;
						// if (imageView != null) {
						// imageView.setImageBitmap(null);
						// }

						switch (failReason.getType()) {
						case IO_ERROR:
							break;
						case DECODING_ERROR:
							break;

						case NETWORK_DENIED:
							break;

						case OUT_OF_MEMORY:
							break;

						case UNKNOWN:
							break;
						default:
							break;
						}
					}

					@Override
					public void onLoadingComplete(String imageUri, View view,
												  Bitmap loadedImage) {
						ImageView imageView = (ImageView) view;
						if (imageView != null) {
							imageView.setImageBitmap(loadedImage);
						}
					}

					@Override
					public void onLoadingCancelled(String imageUri, View view) {
						ImageView imageView = (ImageView) view;
						// if (imageView != null) {
						// imageView.setImageBitmap(null);
						// }
					}
				});

		try {
			// wait image decode complete
			Thread.sleep(50);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
