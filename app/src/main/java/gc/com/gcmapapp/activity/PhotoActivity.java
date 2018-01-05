package gc.com.gcmapapp.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.github.chrisbanes.photoview.PhotoView;
import com.nostra13.universalimageloader.core.ImageLoader;

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


    @BindView(R.id.back_ib)
    ImageButton backIb;
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
        if (!TextUtils.isEmpty(getIntent().getStringExtra("img_id"))) {
            String url = context.getString(R.string.img_url, Url.BASE_URL, getIntent().getStringExtra("img_id"));
            Log.i("Coordination", "url:" + url);
            imageLoaderUtil.displayImage(url, ivPhoto, ImageLoader.getInstance());
        } else {
            ivPhoto.setImageResource(R.mipmap.ic_default_big);
        }
    }

    @OnClick(R.id.back_ib)
    public void back(View view) {
        this.finish();
    }


}

