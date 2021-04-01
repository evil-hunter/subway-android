package com.example.myapplication.ui.stations;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.myapplication.R;
import com.example.myapplication.utils.UITools;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class StationList extends Fragment {

    private static final String TAG = "stations";

    private View mView;
    private ViewPager mViewPager;
    private List<ImageView> images;
    private List<View> dots;
    private MaterialToolbar toolbar;
    private int currentItem;
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
        setView();
        return mView;
    }

    private void setView() {
        mViewPager = (ViewPager)mView.findViewById(R.id.m_swiper);
        toolbar = mView.findViewById(R.id.stationToolbar);
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