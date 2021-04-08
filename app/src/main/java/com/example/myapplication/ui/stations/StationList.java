package com.example.myapplication.ui.stations;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.adapter.LineAdapter;
import com.example.myapplication.adapter.StationAdapter;
import com.example.myapplication.api.VolleySingleton;
import com.example.myapplication.datasource.SubwayDataSource;
import com.example.myapplication.entity.LineBean;
import com.example.myapplication.entity.StationBean;
import com.example.myapplication.utils.UITools;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;

public class StationList extends Fragment {

    private static final String TAG = "stations";

    private View mView;
    private ViewPager mViewPager;
    private String[] station_line = new String[]{"机场线","一号线", "二号线", "三号线", "四号线", "五号线", "六号线","九号线"};
    private List<StationBean> stationData = new ArrayList<>();
    private StationViewModel stationViewModel;
    private List<LineBean> lineData;
    private List<ImageView> images;
    private List<View> dots;
    private MaterialToolbar toolbar;
    private RecyclerView lineList;
    private RecyclerView stationList;
    private LineAdapter lineAdapter;
    private StationAdapter stationAdapter;
    private int currentItem;
//    private SearchView searchView;
    //记录上次点击位置
    private int oldPosition = 0;
    //存放图片的id
    private int[] imageIds = new int[]{
            R.drawable.vp_1,
            R.drawable.vp_2,
            R.drawable.vp_3,
            R.drawable.vp_4,
    };

    private ViewPagerAdapter adapter;
    private ScheduledExecutorService scheduledExecutorService;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_station_list, null);
        initData();
        setView();
        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void initData() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                String url = "http://10.18.209.35:4000/home/stationlist";
                StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String resData;
                        Log.d("res",response);
                        JsonParser parser = new JsonParser();
                        JsonArray jsonArray = parser.parse(response).getAsJsonArray();
                        Log.d("jsonArray",jsonArray.toString());
                        Gson gson = new Gson();

                        for(JsonElement station : jsonArray) {
                            Log.d("station",station.toString());
                            StationBean stationBean = gson.fromJson(station, StationBean.class);
                            stationData.add(stationBean);
                            Log.d("Name",stationBean.getName());
                        }
                        resData = stationData.toString();
                        Toast.makeText(getActivity(),resData,Toast.LENGTH_LONG).show();
                        Log.d("Data",resData);
                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                error.printStackTrace();
                            }
                        }
        ) {
                    @Override
                    protected Map<String, String> getParams()
                    {
                        Map<String, String>  params = new HashMap<>();
                        // the POST parameters:
                        params.put("line", "0");
                        return params;
                    }
                };
                Volley.newRequestQueue(getActivity()).add(postRequest);
            }
        }).start();
    }

    private void setView() {
        mViewPager = (ViewPager)mView.findViewById(R.id.m_swiper);
        lineList = mView.findViewById(R.id.lineList);
        stationList = mView.findViewById(R.id.stationList);
//        searchView = mView.findViewById(R.id.search);
        toolbar = mView.findViewById(R.id.stationToolbar);
        lineData = new ArrayList<>();
        LineBean lineBean;
        for (int i = 0; i < 8; i++) {
            lineBean = new LineBean();
            lineBean.setName(station_line[i]);
            lineData.add(lineBean);
        }

        //创建布局管理
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        lineList.setLayoutManager(layoutManager);

        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this.getActivity());
        layoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
        stationList.setLayoutManager(layoutManager1);

        //创建适配器
        lineAdapter = new LineAdapter(R.layout.line_item, lineData);
        stationAdapter = new StationAdapter(R.layout.station_item,stationData);

        //给RecyclerView设置适配器
        lineList.setAdapter(lineAdapter);
        stationList.setAdapter(stationAdapter);

        //状态栏文字透明
        UITools.makeStatusBarTransparent(getActivity());
        //修复标题栏与状态栏重叠
        UITools.fitTitleBar(getActivity(), toolbar);


        //显示图片
        images = new ArrayList<ImageView>();
        for(int i = 0; i < imageIds.length; i++){
            ImageView imageView = new ImageView(getActivity());
            imageView.setBackgroundResource(imageIds[i]);
            images.add(imageView);
        }
        //显示小点
        dots = new ArrayList<View>();
        dots.add(mView.findViewById(R.id.dot_0));
        dots.add(mView.findViewById(R.id.dot_1));
        dots.add(mView.findViewById(R.id.dot_2));
        dots.add(mView.findViewById(R.id.dot_3));

        adapter = new ViewPagerAdapter();
        mViewPager.setAdapter(adapter);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                dots.get(position).setBackgroundResource(R.drawable.banner_focus);
                dots.get(oldPosition).setBackgroundResource(R.drawable.banner_blur);

                oldPosition = position;
                currentItem = position;
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public class ViewPagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return images.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView(images.get(position));
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            container.addView(images.get(position));
            return images.get(position);
        }
    }



    /*利用线程池执行轮播*/

    @Override
    public void onStart() {
        super.onStart();
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleWithFixedDelay(
                new ViewPageTask(),
                2,
                2,
                TimeUnit.SECONDS);
    }

    /*
    * 图片轮播任务
    * */
    public class ViewPageTask implements Runnable {
        @Override
        public void run() {
            currentItem = (currentItem + 1) % imageIds.length;
            mHandler.sendEmptyMessage(0);
        }
    }

    /*
    * 接收子线程传过来的数据
    * */
    private Handler mHandler = new Handler(){
        public void handleMessage(android.os.Message msg) {
            mViewPager.setCurrentItem(currentItem);
        };
    };

    @Override
    public void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        if(scheduledExecutorService != null){
            scheduledExecutorService.shutdown();
            scheduledExecutorService = null;
        }
    }

}