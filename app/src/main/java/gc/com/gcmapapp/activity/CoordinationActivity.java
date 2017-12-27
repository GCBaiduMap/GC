package gc.com.gcmapapp.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import gc.com.gcmapapp.R;
import gc.com.gcmapapp.application.Constants;
import gc.com.gcmapapp.bean.Login;
import gc.com.gcmapapp.http.Api;
import gc.com.gcmapapp.http.HttpUtil;
import gc.com.gcmapapp.http.ProgressSubscriber;
import gc.com.gcmapapp.http.Url;
import gc.com.gcmapapp.utils.ImageLoaderUtil;
import gc.com.gcmapapp.utils.SharePreferenceUtil;
import gc.com.gcmapapp.utils.ToastUtils;


/**
 * A login screen that offers login via email/password.
 */
public class CoordinationActivity extends BaseActivity {


    @BindView(R.id.detail_address_tv)
     TextView detailAddressTv;
    @BindView(R.id.research_number_tv)
     TextView researchNumberTv;
    @BindView(R.id.check_time_tv)
     TextView checkTimeTv;
    @BindView(R.id.img_url_tv)
     TextView imgUrlTv;
    @BindView(R.id.img_iv)
     ImageView imgIv;
    ImageLoaderUtil imageLoaderUtil ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordination);
        ButterKnife.bind(this);
        imageLoaderUtil = new ImageLoaderUtil();
        iniView();
    }

    private void iniView(){
        detailAddressTv.setText(getIntent().getStringExtra("detail_address"));
        researchNumberTv.setText(getIntent().getStringExtra("research_number"));
        checkTimeTv.setText(getIntent().getStringExtra("check_time"));
        imgUrlTv.setText(getIntent().getStringExtra("img_url"));
        if (!TextUtils.isEmpty(getIntent().getStringExtra("img_id"))) {
            String url = context.getString(R.string.img_url, Url.BASE_URL,  getIntent().getStringExtra("img_id"));
            Log.i("Coordination", "url:" + url);
            imageLoaderUtil.displayImage(url, imgIv, ImageLoader.getInstance());
        } else {
            imgIv.setImageResource(R.mipmap.ic_default_big);
        }
    }


}

