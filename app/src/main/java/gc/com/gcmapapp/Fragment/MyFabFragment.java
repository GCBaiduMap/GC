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
import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import gc.com.gcmapapp.R;
import gc.com.gcmapapp.holder.IconTreeItemHolder;
import gc.com.gcmapapp.holder.ProfileHolder;
import gc.com.gcmapapp.holder.SelectableHeaderHolder;
import gc.com.gcmapapp.holder.SelectableItemHolder;


/**
 * Created by krupenghetiya on 23/06/17.
 */

public class MyFabFragment extends AAH_FabulousFragment {


    ArrayMap<String, List<String>> applied_filters = new ArrayMap<>();
    List<TextView> textviews = new ArrayList<>();


    ImageButton imgbtn_refresh, imgbtn_apply;
    private AndroidTreeView tView;
    private RelativeLayout containerView;


    public static MyFabFragment newInstance() {
        MyFabFragment mff = new MyFabFragment();
        return mff;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override

    public void setupDialog(Dialog dialog, int style) {
        View contentView = View.inflate(getContext(), R.layout.filter_view, null);

        RelativeLayout rl_content = (RelativeLayout) contentView.findViewById(R.id.rl_content);
        LinearLayout ll_buttons = (LinearLayout) contentView.findViewById(R.id.ll_buttons);
        imgbtn_refresh = (ImageButton) contentView.findViewById(R.id.imgbtn_refresh);
        imgbtn_apply = (ImageButton) contentView.findViewById(R.id.imgbtn_apply);
        containerView = contentView.findViewById(R.id.container);

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

        iniMenu();



        //params to set
        setAnimationDuration(600); //optional; default 500ms
        setPeekHeight(300); // optional; default 400dp
        setCallbacks((Callbacks) getActivity()); //optional; to get back result
        setAnimationListener((AnimationListener) getActivity()); //optional; to get animation callbacks
        setViewgroupStatic(ll_buttons); // optional; layout to stick at bottom on slide
        setViewMain(rl_content); //necessary; main bottomsheet view
        setMainContentView(contentView); // necessary; call at end before super
        super.setupDialog(dialog, style); //call super at last
    }

    private void iniMenu(){
        TreeNode root = TreeNode.root();

        TreeNode s1 = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_sd_storage, "Storage1")).setViewHolder(new ProfileHolder(getActivity()));
        TreeNode s2 = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_sd_storage, "Storage2")).setViewHolder(new ProfileHolder(getActivity()));
        s1.setSelectable(false);
        s2.setSelectable(false);

        TreeNode folder1 = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "Folder 1")).setViewHolder(new SelectableHeaderHolder(getActivity()));
        TreeNode folder2 = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "Folder 2")).setViewHolder(new SelectableHeaderHolder(getActivity()));
        TreeNode folder3 = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "Folder 3")).setViewHolder(new SelectableHeaderHolder(getActivity()));

        fillFolder(folder1);
        fillFolder(folder2);
        fillFolder(folder3);

        s1.addChildren(folder1, folder2);
        s2.addChildren(folder3);

        root.addChildren(s1, s2);

        tView = new AndroidTreeView(getActivity(), root);
        tView.setDefaultAnimation(true);
        containerView.addView(tView.getView());
    }

    private void fillFolder(TreeNode folder) {
        TreeNode file1 = new TreeNode("File1").setViewHolder(new SelectableItemHolder(getActivity()));
        TreeNode file2 = new TreeNode("File2").setViewHolder(new SelectableItemHolder(getActivity()));
        TreeNode file3 = new TreeNode("File3").setViewHolder(new SelectableItemHolder(getActivity()));
        folder.addChildren(file1, file2, file3);
    }



}
