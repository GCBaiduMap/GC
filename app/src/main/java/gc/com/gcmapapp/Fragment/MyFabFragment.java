package gc.com.gcmapapp.Fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.util.ArrayMap;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.allattentionhere.fabulousfilter.AAH_FabulousFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import gc.com.gcmapapp.R;


/**
 * Created by krupenghetiya on 23/06/17.
 */

public class MyFabFragment extends AAH_FabulousFragment {


    ArrayMap<String, List<String>> applied_filters = new ArrayMap<>();
    List<TextView> textviews = new ArrayList<>();

    TabLayout tabs_types;

    ImageButton imgbtn_refresh, imgbtn_apply;
    private DisplayMetrics metrics;


    public static MyFabFragment newInstance() {
        MyFabFragment mff = new MyFabFragment();
        return mff;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //applied_filters = ((MainActivity) getActivity()).getApplied_filters();
        metrics = this.getResources().getDisplayMetrics();

        for (Map.Entry<String, List<String>> entry : applied_filters.entrySet()) {
            Log.d("k9res", "from activity: " + entry.getKey());
            for (String s : entry.getValue()) {
                Log.d("k9res", "from activity val: " + s);

            }
        }
    }

    @Override

    public void setupDialog(Dialog dialog, int style) {
        View contentView = View.inflate(getContext(), R.layout.filter_view, null);

        RelativeLayout rl_content = (RelativeLayout) contentView.findViewById(R.id.rl_content);
        LinearLayout ll_buttons = (LinearLayout) contentView.findViewById(R.id.ll_buttons);
        imgbtn_refresh = (ImageButton) contentView.findViewById(R.id.imgbtn_refresh);
        imgbtn_apply = (ImageButton) contentView.findViewById(R.id.imgbtn_apply);
        ViewPager vp_types = (ViewPager) contentView.findViewById(R.id.vp_types);
        tabs_types = (TabLayout) contentView.findViewById(R.id.tabs_types);

        imgbtn_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeFilter(applied_filters);
            }
        });
        imgbtn_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                for (TextView tv : textviews) {
//                    tv.setTag("unselected");
//                    tv.setBackgroundResource(R.drawable.chip_unselected);
//                    tv.setTextColor(ContextCompat.getColor(getContext(), R.color.filters_chips));
//                }
//                applied_filters.clear();
            }
        });



        //params to set
        setAnimationDuration(600); //optional; default 500ms
        setPeekHeight(300); // optional; default 400dp
        setCallbacks((Callbacks) getActivity()); //optional; to get back result
        setAnimationListener((AnimationListener) getActivity()); //optional; to get animation callbacks
        setViewgroupStatic(ll_buttons); // optional; layout to stick at bottom on slide
        setViewPager(vp_types); //optional; if you use viewpager that has scrollview
        setViewMain(rl_content); //necessary; main bottomsheet view
        setMainContentView(contentView); // necessary; call at end before super
        super.setupDialog(dialog, style); //call super at last
    }



}
