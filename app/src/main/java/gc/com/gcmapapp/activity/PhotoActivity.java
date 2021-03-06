package gc.com.gcmapapp.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.bm.library.PhotoView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import gc.com.gcmapapp.R;
import gc.com.gcmapapp.http.Url;
import gc.com.gcmapapp.utils.ImageLoaderUtil;


/**
 * A login screen that offers login via email/password.
 */
public class PhotoActivity extends BaseActivity {


    @BindView(R.id.iv_photo)
    PhotoView ivPhoto;

    ImageLoaderUtil imageLoaderUtil;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        ButterKnife.bind(this);
        imageLoaderUtil = new ImageLoaderUtil();
        iniView();
    }

    private void iniView() {
        ivPhoto.enable();
        if (!TextUtils.isEmpty(getIntent().getStringExtra("img_id"))) {
            String url = context.getString(R.string.img_url, Url.BASE_URL, getIntent().getStringExtra("img_id"));
            Log.i("Coordination", "url:" + url);
            imageLoaderUtil.displayImage(url, ivPhoto, ImageLoader.getInstance(), new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String s, View view) {

                }

                @Override
                public void onLoadingFailed(String s, View view, FailReason failReason) {
                    ivPhoto.setImageResource(R.mipmap.ic_default_big);
                }

                @Override
                public void onLoadingComplete(String s, View view, Bitmap bitmap) {

                }

                @Override
                public void onLoadingCancelled(String s, View view) {

                }
            });
        } else {
            ivPhoto.setImageResource(R.mipmap.ic_default_big);
        }
    }

}

