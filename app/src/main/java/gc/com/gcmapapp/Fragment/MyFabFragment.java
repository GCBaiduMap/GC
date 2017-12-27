package gc.com.gcmapapp.Fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.util.ArrayMap;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.allattentionhere.fabulousfilter.AAH_FabulousFragment;
import com.google.android.flexbox.FlexboxLayout;
import com.google.gson.Gson;
import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import gc.com.gcmapapp.R;
import gc.com.gcmapapp.bean.MapInfo;
import gc.com.gcmapapp.bean.Menu;
import gc.com.gcmapapp.holder.IconTreeItemHolder;
import gc.com.gcmapapp.holder.ProfileHolder;
import gc.com.gcmapapp.holder.SelectableHeaderHolder;
import gc.com.gcmapapp.holder.SelectableItemHolder;
import gc.com.gcmapapp.utils.TreeUtils;


/**
 * Created by krupenghetiya on 23/06/17.
 */

public class MyFabFragment extends AAH_FabulousFragment {


    ArrayMap<String, List<String>> applied_filters = new ArrayMap<>();
    List<TextView> textviews = new ArrayList<>();


    ImageButton  imgbtn_apply;
    private AndroidTreeView tView;
    private RelativeLayout containerView;
    ViewPager vpContainer;
    SectionsPagerAdapter mAdapter;
    private List<Menu> menus;
    TreeNode root;


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
        imgbtn_apply = (ImageButton) contentView.findViewById(R.id.imgbtn_apply);
        vpContainer = contentView.findViewById(R.id.vp_container);
        mAdapter = new SectionsPagerAdapter();
        vpContainer.setOffscreenPageLimit(1);
        vpContainer.setAdapter(mAdapter);
        //mAdapter.notifyDataSetChanged();

        imgbtn_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                closeFilter(applied_filters);
                List<MapInfo> mapInfos = new ArrayList<>();
                for(TreeNode firstTreeNode : root.getChildren()){
                    if(TreeUtils.isSelected(firstTreeNode)){
                        MapInfo firstMapInfo = new MapInfo();
                        firstMapInfo.setProject_id(((IconTreeItemHolder.IconTreeItem)firstTreeNode.getValue()).id);
                        List<MapInfo.SecondMap> secondMaps = new ArrayList<>();
                        for(TreeNode secondTreeNode: firstTreeNode.getChildren()){
                            if(TreeUtils.isSelected(secondTreeNode)){
                                MapInfo.SecondMap secondMapInfo = new MapInfo.SecondMap();
                                secondMapInfo.setAttribute_id(((IconTreeItemHolder.IconTreeItem)secondTreeNode.getValue()).id);
                                List<MapInfo.ThirdMap> thirdMaps = new ArrayList<>();
                                for(TreeNode thirdTreeNode: secondTreeNode.getChildren()){
                                    if(thirdTreeNode.isSelected()) {
                                        MapInfo.ThirdMap thirdMap = new MapInfo.ThirdMap();
                                        thirdMap.setCondition_id(((IconTreeItemHolder.IconTreeItem) thirdTreeNode.getValue()).id);
                                        thirdMaps.add(thirdMap);
                                    }
                                }
                                secondMapInfo.setConditions(thirdMaps);
                                secondMaps.add(secondMapInfo);
                            }
                        }
                        firstMapInfo.setAttributes(secondMaps);
                        mapInfos.add(firstMapInfo);
                    }

                }
                Gson gson = new Gson();
                String jsonId = gson.toJson(mapInfos);
                closeFilter(jsonId);
            }
        });


        //params to set
        setAnimationDuration(600); //optional; default 500ms
        setPeekHeight(300); // optional; default 400dp
        setCallbacks((Callbacks) getActivity()); //optional; to get back result
        setAnimationListener((AnimationListener) getActivity()); //optional; to get animation callbacks
        setViewPager(vpContainer);
        setViewgroupStatic(ll_buttons); // optional; layout to stick at bottom on slide
        setViewMain(rl_content); //necessary; main bottomsheet view
        setMainContentView(contentView); // necessary; call at end before super
        super.setupDialog(dialog, style); //call super at last
    }

    private void iniMenu(){
        root = TreeNode.root();
        for(Menu menu: menus){
            TreeNode firstMemuNode = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_sd_storage, menu.getMenuName(), menu.getId())).setViewHolder(new ProfileHolder(getActivity()));
            for(Menu.SecondMenu secondMenu : menu.getChildren()){
                TreeNode secondMemuNode = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, secondMenu.getMenuName(), secondMenu.getId())).setViewHolder(new SelectableHeaderHolder(getActivity()));
                for(Menu.ThirdMenu thirdMenu : secondMenu.getChildren()){
                    TreeNode thirdMemuNode = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, thirdMenu.getMenuName(), thirdMenu.getId())).setViewHolder(new SelectableItemHolder(getActivity()));
                    secondMemuNode.addChild(thirdMemuNode);
                }
                firstMemuNode.addChild(secondMemuNode);
            }
            root.addChildren(firstMemuNode);
        }
        tView = new AndroidTreeView(getActivity(), root);
        tView.setDefaultAnimation(true);
        tView.setUse2dScroll(true);
        tView.setSelectionModeEnabled(true);
    }

    private void fillFolder(TreeNode folder) {
        TreeNode file1 = new TreeNode("File1").setViewHolder(new SelectableItemHolder(getActivity()));
        TreeNode file2 = new TreeNode("File2").setViewHolder(new SelectableItemHolder(getActivity()));
        TreeNode file3 = new TreeNode("File3").setViewHolder(new SelectableItemHolder(getActivity()));
        folder.addChildren(file1, file2, file3);
    }

    public class SectionsPagerAdapter extends PagerAdapter {

        @Override
        public Object instantiateItem(ViewGroup collection, int position) {
            iniMenu();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.view_filters_sorters, collection, false);
            FlexboxLayout fbl = (FlexboxLayout) layout.findViewById(R.id.fbl);
            fbl.addView(tView.getView());
            vpContainer.addView(layout);
            return layout;

        }

        @Override
        public void destroyItem(ViewGroup collection, int position, Object view) {
            collection.removeView((View) view);
        }

        @Override
        public int getCount() {
            return 1;
        }


        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

    }

    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }
}
