package gc.com.gcmapapp.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

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


    @BindView(R.id.key1_tv)
     TextView key1TV;
    @BindView(R.id.value1_tv)
     TextView value1TV;
    @BindView(R.id.key2_tv)
    TextView key2TV;
    @BindView(R.id.value2_tv)
    TextView value2TV;
    @BindView(R.id.key3_tv)
    TextView key3TV;
    @BindView(R.id.value3_tv)
    TextView value3TV;
    @BindView(R.id.key4_tv)
    TextView key4TV;
    @BindView(R.id.value4_tv)
    TextView value4TV;

    @BindView(R.id.key5_tv)
    TextView key5TV;
    @BindView(R.id.value5_tv)
    TextView value5TV;

    @BindView(R.id.key6_tv)
    TextView key6TV;
    @BindView(R.id.value6_tv)
    TextView value6TV;

    @BindView(R.id.key7_tv)
    TextView key7TV;
    @BindView(R.id.value7_tv)
    TextView value7TV;

    @BindView(R.id.key8_tv)
    TextView key8TV;
    @BindView(R.id.value8_tv)
    TextView value8TV;

    @BindView(R.id.img_iv)
    ImageView imgIv;

    @BindView(R.id.back_ib)
    ImageButton backIb;
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
        if (!TextUtils.isEmpty(getIntent().getStringExtra("img_id"))) {
            String url = context.getString(R.string.img_url, Url.BASE_URL,  getIntent().getStringExtra("img_id"));
            Log.i("Coordination", "url:" + url);
            imageLoaderUtil.displayImage(url, imgIv, ImageLoader.getInstance(), new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String s, View view) {

                }

                @Override
                public void onLoadingFailed(String s, View view, FailReason failReason) {
                    imgIv.setImageResource(R.mipmap.ic_default_big);
                }

                @Override
                public void onLoadingComplete(String s, View view, Bitmap bitmap) {

                }

                @Override
                public void onLoadingCancelled(String s, View view) {

                }
            });
        } else {
            imgIv.setImageResource(R.mipmap.ic_default_big);
        }

        if(!getIntent().getStringExtra("getDetail_1_key").equals("null"))
        {
            key1TV.setText(getIntent().getStringExtra("getDetail_1_key")+": ");
        }

        if(!getIntent().getStringExtra("getDetail_1_value").equals("null"))
        {
            value1TV.setText(getIntent().getStringExtra("getDetail_1_value"));
        }

        if(!getIntent().getStringExtra("getDetail_2_key").equals("null"))
        {
            key2TV.setText(getIntent().getStringExtra("getDetail_2_key")+": ");
        }

        if(!getIntent().getStringExtra("getDetail_2_value").equals("null"))
        {
            value2TV.setText(getIntent().getStringExtra("getDetail_2_value"));
        }

        if(!getIntent().getStringExtra("getDetail_3_key").equals("null"))
        {
            key3TV.setText(getIntent().getStringExtra("getDetail_3_key")+": ");
        }

        if(!getIntent().getStringExtra("getDetail_3_value").equals("null"))
        {
            value3TV.setText(getIntent().getStringExtra("getDetail_3_value"));
        }

        if(!getIntent().getStringExtra("getDetail_4_key").equals("null"))
        {
            key4TV.setText(getIntent().getStringExtra("getDetail_4_key")+": ");
        }

        if(!getIntent().getStringExtra("getDetail_4_value").equals("null"))
        {
            value4TV.setText(getIntent().getStringExtra("getDetail_4_value"));
        }

        if(!getIntent().getStringExtra("getDetail_5_key").equals("null"))
        {
            key5TV.setText(getIntent().getStringExtra("getDetail_5_key")+": ");
        }

        if(!getIntent().getStringExtra("getDetail_5_value").equals("null"))
        {
            value5TV.setText(getIntent().getStringExtra("getDetail_5_value"));
        }

        if(!getIntent().getStringExtra("getDetail_6_key").equals("null"))
        {
            key6TV.setText(getIntent().getStringExtra("getDetail_6_key")+": ");
        }

        if(!getIntent().getStringExtra("getDetail_6_value").equals("null"))
        {
            value6TV.setText(getIntent().getStringExtra("getDetail_6_value"));
        }

        if(!getIntent().getStringExtra("getDetail_7_key").equals("null"))
        {
            key7TV.setText(getIntent().getStringExtra("getDetail_7_key")+": ");
        }

        if(!getIntent().getStringExtra("getDetail_7_value").equals("null"))
        {
            value7TV.setText(getIntent().getStringExtra("getDetail_7_value"));
        }

        if(!getIntent().getStringExtra("getDetail_8_key").equals("null"))
        {
            key8TV.setText(getIntent().getStringExtra("getDetail_8_key")+": ");
        }

        if(!getIntent().getStringExtra("getDetail_8_value").equals("null"))
        {
            value8TV.setText(getIntent().getStringExtra("getDetail_8_value"));
        }
    }

    @OnClick(R.id.back_ib)
    public void back(View view){
        this.finish();
    }

    @OnClick(R.id.img_iv)
    public void showImg(View view){
        Intent intent = new Intent(context, PhotoActivity.class);
        intent.putExtra("img_id", getIntent().getStringExtra("img_id"));
        startActivity(intent);
    }


}

