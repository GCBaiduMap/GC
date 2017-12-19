package gc.com.gcmapapp.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.allattentionhere.fabulousfilter.AAH_FabulousFragment;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import gc.com.gcmapapp.Fragment.MyFabFragment;
import gc.com.gcmapapp.R;
import gc.com.gcmapapp.application.Constants;
import gc.com.gcmapapp.bean.LocationInfo;
import gc.com.gcmapapp.bean.MapInfo;
import gc.com.gcmapapp.bean.MapResult;
import gc.com.gcmapapp.bean.Menu;
import gc.com.gcmapapp.http.Api;
import gc.com.gcmapapp.http.HttpUtil;
import gc.com.gcmapapp.http.ProgressSubscriber;
import gc.com.gcmapapp.utils.RegionParse;
import gc.com.gcmapapp.utils.SharePreferenceUtil;
import gc.com.gcmapapp.utils.ToastUtils;
import mapapi.clusterutil.clustering.Cluster;
import mapapi.clusterutil.clustering.ClusterItem;
import mapapi.clusterutil.clustering.ClusterManager;


public class MainActivity extends BaseActivity implements BaiduMap.OnMapLoadedCallback , AAH_FabulousFragment.Callbacks,AAH_FabulousFragment.AnimationListener{


    @BindView(R.id.bmapView)
    MapView bmapView;
    BaiduMap mBaiduMap;
    MapStatus ms;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    private ClusterManager<MyItem> mClusterManager;
    private List<Menu> menus;
    MyFabFragment dialogFrag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

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
                Toast.makeText(MainActivity.this,
                        "点击单个Item", Toast.LENGTH_SHORT).show();

                return false;
            }
        });
        dialogFrag = MyFabFragment.newInstance();
        dialogFrag.setParentFab(fab);
        getMenu();
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

    /**
     * 每个Marker点，包含Marker点坐标以及图标
     */
    public class MyItem implements ClusterItem {
        private final LatLng mPosition;

        public MyItem(LatLng latLng) {
            mPosition = latLng;
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
                items.add(new MyItem(new LatLng(mapResult.getLatitude(), mapResult.getLongitude())));
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
                if(menus != null && menus.size() >0){
                    getMapInfo();
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

    public void getMapInfo() {

        HttpUtil.getInstance().toSubscribe(Api.getDefault(context).getMapInfo(getJsonId()), new ProgressSubscriber<List<MapResult>>(this) {
            @Override
            protected void _onNext(List<MapResult> mapResults) {
                addMarkers(mapResults);
            }

            @Override
            protected void _onError(String message) {

            }
        }, lifecycleSubject);
    }


    public void getCoordinateInfo(View view) {

        HttpUtil.getInstance().toSubscribe(Api.getDefault(context).getCoordinateInfo(""), new ProgressSubscriber<List<Menu>>(this) {
            @Override
            protected void _onNext(List<Menu> menus) {
                ToastUtils.showMessage(context, menus.get(0).getChildren().get(0).getChildren().get(0).getMenuName());
                MainActivity.this.menus = menus;
            }

            @Override
            protected void _onError(String message) {

            }
        }, lifecycleSubject);
    }


    @OnClick(R.id.fab)
    public void showMenu(View view) {
        if(menus != null){
            dialogFrag.setMenus(menus);
            dialogFrag.show(getSupportFragmentManager(), dialogFrag.getTag());
        }
    }

    @OnClick(R.id.login_out)
    public void setHost(){
        finish();
    }


}

