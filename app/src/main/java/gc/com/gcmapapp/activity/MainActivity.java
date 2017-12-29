package gc.com.gcmapapp.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.allattentionhere.fabulousfilter.AAH_FabulousFragment;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.CityInfo;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.google.gson.Gson;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import gc.com.gcmapapp.Fragment.MyFabFragment;
import gc.com.gcmapapp.R;
import gc.com.gcmapapp.adapter.CustomSuggestionsAdapter;
import gc.com.gcmapapp.application.Constants;
import gc.com.gcmapapp.bean.CoordinationInfo;
import gc.com.gcmapapp.bean.LocationInfo;
import gc.com.gcmapapp.bean.MapInfo;
import gc.com.gcmapapp.bean.MapResult;
import gc.com.gcmapapp.bean.Menu;
import gc.com.gcmapapp.holder.IconTreeItemHolder;
import gc.com.gcmapapp.holder.ProfileHolder;
import gc.com.gcmapapp.holder.SelectableHeaderHolder;
import gc.com.gcmapapp.holder.SelectableItemHolder;
import gc.com.gcmapapp.http.Api;
import gc.com.gcmapapp.http.HttpUtil;
import gc.com.gcmapapp.http.ProgressSubscriber;
import gc.com.gcmapapp.utils.RegionParse;
import gc.com.gcmapapp.utils.SharePreferenceUtil;
import gc.com.gcmapapp.utils.ToastUtils;
import gc.com.gcmapapp.utils.TreeUtils;
import gc.com.gcmapapp.view.ShowCoordinateInfoDialog;
import mapapi.clusterutil.clustering.Cluster;
import mapapi.clusterutil.clustering.ClusterItem;
import mapapi.clusterutil.clustering.ClusterManager;
import mapapi.overlayutil.PoiOverlay;


public class MainActivity extends BaseActivity implements BaiduMap.OnMapLoadedCallback , AAH_FabulousFragment.Callbacks,AAH_FabulousFragment.AnimationListener, OnGetSuggestionResultListener, OnGetPoiSearchResultListener, MaterialSearchBar.OnSearchActionListener, CustomSuggestionsAdapter.OnSuggestSelectedListener {


    @BindView(R.id.bmapView)
    MapView bmapView;
    @BindView(R.id.searchBar)
    MaterialSearchBar searchBar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.menu_container)
    RelativeLayout menuContainer;
    @BindView(R.id.imgbtn_apply)
    ImageButton imgbtnApply;
    BaiduMap mBaiduMap;
    MapStatus ms;
    private ClusterManager<MyItem> mClusterManager;
    private List<Menu> menus;
    private SuggestionSearch mSuggestionSearch = null;
    private PoiSearch mPoiSearch = null;
    private List<String> suggest;
    private ArrayAdapter<String> sugAdapter = null;
    TreeNode root;
    private AndroidTreeView tView;
    private CustomSuggestionsAdapter customSuggestionsAdapter;
    private boolean isSelectSuggestion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        iniView();
        mBaiduMap = bmapView.getMap();
        mBaiduMap.setOnMapLoadedCallback(this);

        // 定义点聚合管理类ClusterManager
        mClusterManager = new ClusterManager<MyItem>(this, mBaiduMap);
        // 添加Marker点
        //addMarkers();
        // 设置地图监听，当地图状态发生改变时，进行点聚合运算
        mBaiduMap.setOnMapStatusChangeListener(mClusterManager);
        // 设置maker点击时的响应
        mBaiduMap.setOnMarkerClickListener(mClusterManager);

        mClusterManager.setOnClusterClickListener(new ClusterManager.OnClusterClickListener<MyItem>() {
            @Override
            public boolean onClusterClick(Cluster<MyItem> cluster) {
                Toast.makeText(MainActivity.this,
                        "有" + cluster.getSize() + "个点", Toast.LENGTH_SHORT).show();
                float zoom = mBaiduMap.getMapStatus().zoom;
                if (zoom < 12)
                    zoom = 12;
                ms = new MapStatus.Builder().target(cluster.getPosition()).zoom(zoom).build();
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(ms));

                return false;
            }
        });
        mClusterManager.setOnClusterItemClickListener(new ClusterManager.OnClusterItemClickListener<MyItem>() {
            @Override
            public boolean onClusterItemClick(MyItem item) {
//                Toast.makeText(MainActivity.this,
//                        "点击单个Item", Toast.LENGTH_SHORT).show();
                getCoordinateInfo(item.id);

                return false;
            }
        });
        getMenu();
    }

    private void iniView(){
        /**
         * 当输入关键字变化时，动态更新建议列表
         */
//        searchKey.addTextChangedListener(new TextWatcher() {
//
//            @Override
//            public void afterTextChanged(Editable arg0) {
//
//            }
//
//            @Override
//            public void beforeTextChanged(CharSequence arg0, int arg1,
//                                          int arg2, int arg3) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence cs, int arg1, int arg2,
//                                      int arg3) {
//                if (cs.length() <= 0) {
//                    return;
//                }
//
//                /**
//                 * 使用建议搜索服务获取建议列表，结果在onSuggestionResult()中更新
//                 */
//                mSuggestionSearch
//                        .requestSuggestion((new SuggestionSearchOption())
//                                .keyword(cs.toString()).city("上海"));
//            }
//        });
        searchBar = (MaterialSearchBar) findViewById(R.id.searchBar);
        CardView cardView = searchBar.findViewById(R.id.mt_container);
        cardView.setRadius(0);
        searchBar.setOnSearchActionListener(this);
        searchBar.inflateMenu(R.menu.main);
        searchBar.setText("");
        Log.d("LOG_TAG", getClass().getSimpleName() + ": text " + searchBar.getText());
        searchBar.setCardViewElevation(10);
        searchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() <= 0) {
                    return;
                }

                /**
                 * 使用建议搜索服务获取建议列表，结果在onSuggestionResult()中更新
                 */
                if(!isSelectSuggestion)
                mSuggestionSearch
                        .requestSuggestion((new SuggestionSearchOption())
                                .keyword(charSequence.toString()).city("上海"));
                else
                    isSelectSuggestion = false;
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }

        });
        searchBar.getMenu().setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if("注销".equals(item.getTitle())){
                    MainActivity.this.finish();
                    Intent intent = new Intent(context, LoginActivity.class);
                    context.startActivity(intent);
                }
                return false;
            }
        });
        // 初始化建议搜索模块，注册建议搜索事件监听
        mSuggestionSearch = SuggestionSearch.newInstance();
        mSuggestionSearch.setOnGetSuggestionResultListener(this);
        // 初始化搜索模块，注册搜索事件监听
        mPoiSearch = PoiSearch.newInstance();
        mPoiSearch.setOnGetPoiSearchResultListener(this);


    }


    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        bmapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        bmapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        bmapView.onPause();
    }

    @Override
    public void onMapLoaded() {
        ms = new MapStatus.Builder().zoom(9).build();
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(ms));
    }

    @Override
    public void onResult(Object result) {
       String jsonId = (String) result;
        Log.i("MainActivity", "onResult jsonId:" + jsonId);
        if(!TextUtils.isEmpty(jsonId)){
            getMapInfo(jsonId);
        }
    }

    @OnClick(R.id.imgbtn_apply)
    public void getMapInfo(View view){
        drawer.closeDrawer(GravityCompat.START);
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
        if(!TextUtils.isEmpty(jsonId)){
            getMapInfo(jsonId);
        }
    }

    @Override
    public void onOpenAnimationStart() {

    }

    @Override
    public void onOpenAnimationEnd() {

    }

    @Override
    public void onCloseAnimationStart() {

    }

    @Override
    public void onCloseAnimationEnd() {

    }

    @Override
    public void onGetSuggestionResult(SuggestionResult res) {
        if (res == null || res.getAllSuggestions() == null) {
            searchBar.hideSuggestionsList();
            return;
        }
        suggest = new ArrayList<String>();
        for (SuggestionResult.SuggestionInfo info : res.getAllSuggestions()) {
            if (info.key != null) {
                suggest.add(info.key);
            }
        }
        if(customSuggestionsAdapter == null){
            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            customSuggestionsAdapter = new CustomSuggestionsAdapter(inflater, this);
            searchBar.setCustomSuggestionAdapter(customSuggestionsAdapter);
        }
        customSuggestionsAdapter.setSuggestions(suggest);
        if(!searchBar.isSuggestionsVisible())
          searchBar.showSuggestionsList();
        else
            customSuggestionsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onGetPoiResult(PoiResult result) {
        if (result == null || result.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
            ToastUtils.showMessage(context, "未找到结果");
            searchBar.hideSuggestionsList();
            return;
        }
        if (result.error == SearchResult.ERRORNO.NO_ERROR) {
            mBaiduMap.clear();
            PoiOverlay overlay = new MyPoiOverlay(mBaiduMap);
            mBaiduMap.setOnMarkerClickListener(overlay);
            overlay.setData(result);
            overlay.addToMap();
            overlay.zoomToSpan();
            return;
        }
        if (result.error == SearchResult.ERRORNO.AMBIGUOUS_KEYWORD) {

            ToastUtils.showMessage(context, "未找到结果");
            return;
        }
    }

    @Override
    public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

    }

    @Override
    public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

    }

    @Override
    public void onSearchStateChanged(boolean enabled) {

    }

    @Override
    public void onSearchConfirmed(CharSequence text) {
        String keystr = searchBar.getText().toString();
        mPoiSearch.searchInCity((new PoiCitySearchOption())
                .city("上海").keyword(keystr).pageNum(0));
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if(imm != null){
            imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(),
                    0);
        }
        if(searchBar.isSuggestionsVisible())
            searchBar.hideSuggestionsList();
    }

    @Override
    public void onButtonClicked(int buttonCode) {
        switch (buttonCode){
            case MaterialSearchBar.BUTTON_NAVIGATION:
                drawer.openDrawer(Gravity.LEFT);
                break;
            case MaterialSearchBar.BUTTON_SPEECH:
                break;
            case MaterialSearchBar.BUTTON_BACK:
                searchBar.disableSearch();
                break;
        }
    }

    @Override
    public void onSuggestSelected(String suggestion) {
        isSelectSuggestion = true;
        searchBar.setText(suggestion);
        mPoiSearch.searchInCity((new PoiCitySearchOption())
                .city("上海").keyword(suggestion).pageNum(0));
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if(imm != null){
            imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(),
                    0);
        }
        searchBar.hideSuggestionsList();
    }

    private class MyPoiOverlay extends PoiOverlay {

        public MyPoiOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public boolean onPoiClick(int index) {
            super.onPoiClick(index);
            PoiInfo poi = getPoiResult().getAllPoi().get(index);
            // if (poi.hasCaterDetails) {
            mPoiSearch.searchPoiDetail((new PoiDetailSearchOption())
                    .poiUid(poi.uid));
            // }
            return true;
        }
    }

    /**
     * 每个Marker点，包含Marker点坐标以及图标
     */
    public class MyItem implements ClusterItem {
        private final LatLng mPosition;
        private String id;

        public MyItem(LatLng latLng, String id) {
            mPosition = latLng;
            this.id = id;
        }

        @Override
        public LatLng getPosition() {
            return mPosition;
        }

        @Override
        public BitmapDescriptor getBitmapDescriptor() {
            return BitmapDescriptorFactory
                    .fromResource(R.mipmap.icon_gcoding);
        }
    }

    /**
     * 向地图添加Marker点
     */
    public void addMarkers(List<MapResult> mapResults) {
        if(mapResults != null && mapResults.size() >0){
            // 添加Marker点

            List<MyItem> items = new ArrayList<MyItem>();
            mClusterManager.clearItems();


//            List<LocationInfo> locationInfos = RegionParse.getRegionBean(this);
//            for (LocationInfo locationInfo : locationInfos) {
//                items.add(new MyItem(new LatLng(locationInfo.getLen(), locationInfo.getLat())));
//            }
            for(MapResult mapResult : mapResults){
                items.add(new MyItem(new LatLng(mapResult.getLatitude(), mapResult.getLongitude()), mapResult.getId()));
            }

            mClusterManager.addItems(items);
            ms = new MapStatus.Builder().target(new LatLng(31.23, 121.47)).zoom(10).build();
            mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(ms));
        }


    }


    public void getMenu() {
        HttpUtil.getInstance().toSubscribe(Api.getDefault(context).getMenu(), new ProgressSubscriber<List<Menu>>(this) {
            @Override
            protected void _onNext(List<Menu> menus) {
                MainActivity.this.menus = menus;
                iniMenu();
                if(menus != null && menus.size() >0){
                    getMapInfo(getJsonId());
                }
            }

            @Override
            protected void _onError(String message) {
            }
        }, lifecycleSubject);
    }

    private String getJsonId( ){
        List<MapInfo> mapInfos = new ArrayList<>();
        MapInfo mapInfo = new MapInfo();
        mapInfo.setProject_id(menus.get(0).getId());
        List<MapInfo.SecondMap> secondMaps = new ArrayList<>();
        for (Menu.SecondMenu secondMenu : menus.get(0).getChildren()) {
            MapInfo.SecondMap secondMap = new MapInfo.SecondMap();
            secondMap.setAttribute_id(secondMenu.getId());
            List<MapInfo.ThirdMap> thirdMaps = new ArrayList<>();
            for (Menu.ThirdMenu thirdMenu : secondMenu.getChildren()) {
                MapInfo.ThirdMap thirdMap = new MapInfo.ThirdMap();
                thirdMap.setCondition_id(thirdMenu.getId());
                thirdMaps.add(thirdMap);
            }
            secondMap.setConditions(thirdMaps);
            secondMaps.add(secondMap);
        }
        mapInfo.setAttributes(secondMaps);
        mapInfos.add(mapInfo);
        Gson gson = new Gson();
        String jsonId = gson.toJson(mapInfos);
        return jsonId;

    }

    public void getMapInfo(String jsonId) {

        HttpUtil.getInstance().toSubscribe(Api.getDefault(context).getMapInfo(jsonId), new ProgressSubscriber<List<MapResult>>(this) {
            @Override
            protected void _onNext(List<MapResult> mapResults) {
                mBaiduMap.clear();
                addMarkers(mapResults);
            }

            @Override
            protected void _onError(String message) {

            }
        }, lifecycleSubject);
    }


    public void getCoordinateInfo(String coordinateId) {

        HttpUtil.getInstance().toSubscribe(Api.getDefault(context).getCoordinateInfo(coordinateId), new ProgressSubscriber<List<CoordinationInfo>>(this) {
            @Override
            protected void _onNext(List<CoordinationInfo> coordinationInfos) {
                ShowCoordinateInfoDialog dialog = ShowCoordinateInfoDialog.createDialog(context);
                dialog.setCoordinations(coordinationInfos);
                dialog.show();
            }

            @Override
            protected void _onError(String message) {

            }
        }, lifecycleSubject);
    }




//    @OnClick(R.id.btn_search)
//    public void search(View v){
//        String keystr = searchKey.getText().toString();
//        mPoiSearch.searchInCity((new PoiCitySearchOption())
//                .city("上海").keyword(keystr).pageNum(0));
//    }


    private void iniMenu(){
        root = TreeNode.root();
        for(Menu menu: menus){
            TreeNode firstMemuNode = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_sd_storage, menu.getMenuName(), menu.getId())).setViewHolder(new ProfileHolder(context));
            for(Menu.SecondMenu secondMenu : menu.getChildren()){
                TreeNode secondMemuNode = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, secondMenu.getMenuName(), secondMenu.getId())).setViewHolder(new SelectableHeaderHolder(context));
                for(Menu.ThirdMenu thirdMenu : secondMenu.getChildren()){
                    TreeNode thirdMemuNode = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, thirdMenu.getMenuName(), thirdMenu.getId())).setViewHolder(new SelectableItemHolder(context));
                    secondMemuNode.addChild(thirdMemuNode);
                }
                firstMemuNode.addChild(secondMemuNode);
            }
            root.addChildren(firstMemuNode);
        }
        tView = new AndroidTreeView(context, root);
        tView.setDefaultAnimation(true);
        tView.setUse2dScroll(true);
        tView.setSelectionModeEnabled(true);
        menuContainer.addView(tView.getView());
    }




}

