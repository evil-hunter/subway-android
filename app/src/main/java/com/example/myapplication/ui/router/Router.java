package com.example.myapplication.ui.router;
import android.app.Application;
import android.app.FragmentManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.MapFragment;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.SupportMapFragment;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.example.myapplication.R;
import com.example.myapplication.utils.UITools;
import com.google.android.material.appbar.MaterialToolbar;

public class Router extends Fragment implements OnGetGeoCoderResultListener {
    private static final String TAG = "ROUTER_MAP";
    private MapView mMapView = null;
    private BaiduMap mBaiduMap;
    private View view;
    private MaterialToolbar toolbar;
    //定位相关
    LocationClient mLocClient;
    //是否首次定位
    boolean isFirstLoc = true;
    LocationClientOption option = null;
    LatLng cenpt = new LatLng(108.563332,34.153990);  //设定中心点坐标
    //搜索模块
    GeoCoder mSearch = null;
    public MyLocationListener myListener = new MyLocationListener();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //在使用百度地图SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getActivity().getApplicationContext());
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_router,container,false);
        mMapView = (MapView) view.findViewById(R.id.baidu_mapView);
        mBaiduMap = mMapView.getMap();
        // 初始化搜索模块，注册事件监听
        mSearch = GeoCoder.newInstance();
        mSearch.setOnGetGeoCodeResultListener(this);
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        // 定位初始化
        mLocClient = new LocationClient(getActivity().getApplicationContext());
        mLocClient.registerLocationListener(myListener);//将原代码中public class MyLocationListener implements BDLocationListener改为 public class MyLocationListener extends BDAbstractLocationListener

        option = new LocationClientOption();
        Log.i(TAG, "==-->option:="+option);
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//设置定位模式：高精度，低功耗，仅设备
        option.setOpenGps(true);// 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        //  option.setScanSpan(1000);//设置每秒更新一次位置信息
        option.disableCache(false);// 禁止启用缓存定位
        option.setIsNeedLocationDescribe(true);//设置需要位置描述信息
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(true);//可选，默认false，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        option.setNeedDeviceDirect(false);//可选，设置是否需要设备方向结果
        option.setOpenAutoNotifyMode(); //设置打开自动回调位置模式，该开关打开后，期间只要定位SDK检测到位置变化就会主动回调给开发者，该模式下开发者无需再关心定位间隔是多少，定位SDK本身发现位置变化就会及时回调给开发者
        mLocClient.setLocOption(option);
        mLocClient.start();
        initView();
        return view;
    }

    public void initView() {
        toolbar = view.findViewById(R.id.router_toolbar);
        //状态栏文字透明
        UITools.makeStatusBarTransparent(getActivity());
        //修复标题栏与状态栏重叠
        UITools.fitTitleBar(getActivity(), toolbar);
    }

    /**
     * 定位SDK监听函数
     */
    public class MyLocationListener extends BDAbstractLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null || mMapView == null)
                return;
            //                        // 反Geo搜索
            mSearch.reverseGeoCode(new ReverseGeoCodeOption()
                    .location(mBaiduMap.getMapStatus().target));
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(location.getDirection()).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            // 设置地图中心点
            MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory
                    .newMapStatus(new MapStatus.Builder().target(latLng)
                            .overlook(-15).rotate(180).zoom(17).build());
            mBaiduMap.setMapStatus(mapStatusUpdate);
            if (isFirstLoc) {

                LatLng ll = new LatLng(location.getLatitude(),
                        location.getLongitude());
                MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
                mBaiduMap.animateMapStatus(u);
                if (mBaiduMap.getLocationData()!=null)
                    if (mBaiduMap.getLocationData().latitude==location.getLatitude()
                            &&mBaiduMap.getLocationData().longitude==location.getLongitude()) {
                        isFirstLoc = false;
                    }
            }



            StringBuffer sb = new StringBuffer(256);
            sb.append("time : ");
            sb.append(location.getTime());
            sb.append("\nerror code : ");
            sb.append(location.getLocType());
            sb.append("\nlatitude : ");
            sb.append(location.getLatitude());//获取维度
            sb.append("\nlontitude : ");
            sb.append(location.getLongitude());//获取经度
            sb.append("\nradius : ");
            sb.append(location.getRadius());//获取定位精度半径，单位是米
            if (location.getLocType() == BDLocation.TypeGpsLocation){//获取error code
                sb.append("\nspeed : ");
                sb.append(location.getSpeed());
                sb.append("\nsatellite : ");
                sb.append(location.getSatelliteNumber());
            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation){
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
            }
        }

        public void onReceivePoi(BDLocation poiLocation) {
        }
    }

    @Override
    public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {

    }

    @Override
    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {

    }
}
