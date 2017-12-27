package gc.com.gcmapapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import gc.com.gcmapapp.R;
import gc.com.gcmapapp.bean.CoordinationInfo;


public class CoordinationAdapter extends BaseAdapter {
    private Context mContext;
    private List<CoordinationInfo> coordinationInfos;
    private LayoutInflater mLayoutInflater;
    private int mSelected = -1;
    private int mSelectedColor;
    private int type;

    public CoordinationAdapter(Context context, List<CoordinationInfo> coordinationInfos) {
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        this.coordinationInfos = coordinationInfos;
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
            holder.detailAddressTv = (TextView) view.findViewById(R.id.detail_address_tv);
            holder.researchNumberTv = (TextView) view.findViewById(R.id.research_number_tv);
            holder.checkTimeTv = (TextView) view.findViewById(R.id.check_time_tv);
            holder.imgUrlTv = (TextView) view.findViewById(R.id.img_url_tv);
            holder.divider = view.findViewById(R.id.divider);
            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }
        holder.detailAddressTv.setText(coordinationInfos.get(position).getDetail_address());
        holder.researchNumberTv.setText(coordinationInfos.get(position).getResearch_number());
        holder.checkTimeTv.setText(coordinationInfos.get(position).getCheck_time());
        holder.imgUrlTv.setText(coordinationInfos.get(position).getImg_url());
        if (position == coordinationInfos.size() - 1) {
            holder.divider.setVisibility(View.GONE);
        } else {
            holder.divider.setVisibility(View.VISIBLE);
        }

        return view;
    }

    private class Holder {
        private TextView detailAddressTv;
        private TextView researchNumberTv;
        private TextView checkTimeTv;
        private TextView imgUrlTv;
        private View divider;
    }
}
