package gc.com.gcmapapp.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.List;

import gc.com.gcmapapp.R;
import gc.com.gcmapapp.bean.CoordinationInfo;
import gc.com.gcmapapp.http.Url;
import gc.com.gcmapapp.utils.ImageLoaderUtil;


public class CoordinationAdapter extends BaseAdapter {
    private Context mContext;
    private List<CoordinationInfo> coordinationInfos;
    private LayoutInflater mLayoutInflater;
    ImageLoaderUtil imageLoaderUtil ;

    public CoordinationAdapter(Context context, List<CoordinationInfo> coordinationInfos) {
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        this.coordinationInfos = coordinationInfos;
        imageLoaderUtil = new ImageLoaderUtil();
    }


    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return coordinationInfos.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return coordinationInfos.get(position);
    }

    @Override
    public long getItemId(int listItems) {
        // TODO Auto-generated method stub
        return listItems;
    }

    @Override
    public View getView(int position, View view, ViewGroup arg2) {
        // TODO Auto-generated method stub
        Holder holder = null;
        if (null == view) {
            view = mLayoutInflater.inflate(R.layout.item_coordination, null);
            holder = new Holder();
            holder.value1_tv = (TextView)view.findViewById(R.id.value1_tv);
            holder.key2_tv = (TextView)view.findViewById(R.id.key2_tv);
            holder.value2_tv = (TextView)view.findViewById(R.id.value2_tv);
            holder.key3_tv = (TextView)view.findViewById(R.id.key3_tv);
            holder.value3_tv = (TextView)view.findViewById(R.id.value3_tv);
            holder.key4_tv = (TextView)view.findViewById(R.id.key4_tv);
            holder.value4_tv = (TextView)view.findViewById(R.id.value4_tv);
            holder.divider = view.findViewById(R.id.divider);
            holder.imgIv = view.findViewById(R.id.img_iv);
            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }

        if(!coordinationInfos.get(position).getDetail_1_value().equals("null"))
        {
            holder.value1_tv.setText(coordinationInfos.get(position).getDetail_1_value());
        }

        if(!coordinationInfos.get(position).getDetail_2_key().equals("null"))
        {
            holder.key2_tv.setText(coordinationInfos.get(position).getDetail_2_key()+": ");
        }

        if(!coordinationInfos.get(position).getDetail_2_value().equals("null"))
        {
            holder.value2_tv.setText(coordinationInfos.get(position).getDetail_2_value());
        }

        if(!coordinationInfos.get(position).getDetail_3_key().equals("null"))
        {
            holder.key3_tv.setText(coordinationInfos.get(position).getDetail_3_key()+": ");
        }

        if(!coordinationInfos.get(position).getDetail_3_value().equals("null"))
        {
            holder.value3_tv.setText(coordinationInfos.get(position).getDetail_3_value());
        }

        if(!coordinationInfos.get(position).getDetail_4_key().equals("null"))
        {
            holder.key4_tv.setText(coordinationInfos.get(position).getDetail_4_key()+": ");
        }

        if(!coordinationInfos.get(position).getDetail_4_value().equals("null"))
        {
            holder.value4_tv.setText(coordinationInfos.get(position).getDetail_4_value());
        }

        if (!TextUtils.isEmpty(coordinationInfos.get(position).getId())) {
            String url = mContext.getString(R.string.img_thumb_url, Url.BASE_URL,  coordinationInfos.get(position).getId());
            Log.i("Coordination", "url:" + url);
            imageLoaderUtil.displayImage(url, holder.imgIv, ImageLoader.getInstance(), new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String s, View view) {

                }

                @Override
                public void onLoadingFailed(String s, View view, FailReason failReason) {
                    ((ImageView)view).setImageResource(R.mipmap.ic_default);
                }

                @Override
                public void onLoadingComplete(String s, View view, Bitmap bitmap) {

                }

                @Override
                public void onLoadingCancelled(String s, View view) {

                }
            });
        } else {
            holder.imgIv
                    .setImageResource(R.mipmap.ic_default);
        }
        if (position == coordinationInfos.size() - 1) {
            holder.divider.setVisibility(View.GONE);
        } else {
            holder.divider.setVisibility(View.VISIBLE);
        }

        return view;
    }

    private class Holder {
        private TextView value1_tv;
        private TextView key2_tv;
        private TextView value2_tv;
        private TextView key3_tv;
        private TextView value3_tv;
        private TextView key4_tv;
        private TextView value4_tv;
        private View divider;
        private ImageView imgIv;
    }
}
