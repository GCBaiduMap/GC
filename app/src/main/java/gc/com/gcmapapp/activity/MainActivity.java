package gc.com.gcmapapp.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.Button;
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
import gc.com.gcmapapp.bean.LocationInfo;
import gc.com.gcmapapp.bean.MapInfo;
import gc.com.gcmapapp.bean.MapResult;
import gc.com.gcmapapp.bean.Menu;
import gc.com.gcmapapp.http.Api;
import gc.com.gcmapapp.http.HttpUtil;
import gc.com.gcmapapp.http.ProgressSubscriber;
import gc.com.gcmapapp.utils.RegionParse;
import gc.com.gcmapapp.utils.ToastUtils;
import mapapi.clusterutil.clustering.Cluster;
import mapapi.clusterutil.clustering.ClusterItem;
import mapapi.clusterutil.clustering.ClusterManager;


public class MainActivity extends BaseActivity implements BaiduMap.OnMapLoadedCallback , AAH_FabulousFragment.Callbacks,AAH_FabulousFragment.AnimationListener{


    @BindView(R.id.bmapView)
    MapView bmapView;
    BaiduMap mBaiduMap;
    MapStatus ms;
    @BindView(R.id.memu)
    Button memu;
    @BindView(R.id.map_info)
    Button mapInfo;
    @BindView(R.id.coorinate_info)
    Button coorinateInfo;
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
        ms = new MapStatus.Builder().target(new LatLng(31.019261, 121.205807)).zoom(8).build();
        mBaiduMap = bmapView.getMap();
        mBaiduMap.setOnMapLoadedCallback(this);
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(ms));
        // 定义点聚合管理类ClusterManager
        mClusterManager = new ClusterManager<MyItem>(this, mBaiduMap);
        // 添加Marker点
        addMarkers();
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
    public void addMarkers() {
        // 添加Marker点
        LatLng llA = new LatLng(39.963175, 116.400244);
        LatLng llB = new LatLng(39.942821, 116.369199);
        LatLng llC = new LatLng(39.939723, 116.425541);
        LatLng llD = new LatLng(39.906965, 116.401394);
        LatLng llE = new LatLng(39.956965, 116.331394);
        LatLng llF = new LatLng(39.886965, 116.441394);
        LatLng llG = new LatLng(39.996965, 116.411394);

        List<MyItem> items = new ArrayList<MyItem>();
        items.add(new MyItem(llA));
        items.add(new MyItem(llB));
        items.add(new MyItem(llC));
        items.add(new MyItem(llD));
        items.add(new MyItem(llE));
        items.add(new MyItem(llF));
        items.add(new MyItem(llG));

        List<LocationInfo> locationInfos = RegionParse.getRegionBean(this);
        for (LocationInfo locationInfo : locationInfos) {
            items.add(new MyItem(new LatLng(locationInfo.getLen(), locationInfo.getLat())));
        }

        mClusterManager.addItems(items);

    }


    @OnClick(R.id.memu)
    public void getMenu(View view) {

        HttpUtil.getInstance().toSubscribe(Api.getDefault(context).getMenu(), new ProgressSubscriber<List<Menu>>(this) {
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

    @OnClick(R.id.map_info)
    public void getMapInfo(View view) {
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

        HttpUtil.getInstance().toSubscribe(Api.getDefault(context).getMapInfo(jsonId), new ProgressSubscriber<List<MapResult>>(this) {
            @Override
            protected void _onNext(List<MapResult> mapResults) {
                ToastUtils.showMessage(context, mapResults.get(0).getDetail_address());
            }

            @Override
            protected void _onError(String message) {

            }
        }, lifecycleSubject);
    }


    @OnClick(R.id.coorinate_info)
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
        dialogFrag.setMenus(menus);
        dialogFrag.show(getSupportFragmentManager(), dialogFrag.getTag());
    }


}

