package com.example.myapplication.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.myapplication.R;
import com.example.myapplication.ui.culture.Culture;
import com.example.myapplication.ui.personalcenter.PersonalCenter;
import com.example.myapplication.ui.router.Router;
import com.example.myapplication.ui.stations.StationList;
import com.example.myapplication.utils.MyViewPager;
import com.example.myapplication.utils.UITools;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private static final String TAG = "HomeFragment";

    private MyViewPager viewPager;
    private BottomNavigationView bottomNavigationView;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        viewPager = root.findViewById(R.id.viewpage);
        bottomNavigationView = root.findViewById(R.id.bv);

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated");

        //状态栏文字透明
        UITools.makeStatusBarTransparent(getActivity());

        //修复标题栏与状态栏重叠
        UITools.setMIUI(getActivity(), true);

        initViewPage();
        initBottomNav();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume()");
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    public void initViewPage() {
        final ArrayList<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new StationList());
        fragmentList.add(new Router());
        fragmentList.add(new Culture());
        fragmentList.add(new PersonalCenter());
        viewPager.setAdapter(new FragmentStatePagerAdapter(getChildFragmentManager()) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return fragmentList.get(position);
            }

            @Override
            public int getCount() {
                return fragmentList.size();
            }
        });

        //滑动事件监听
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //预加载界面
        viewPager.setOffscreenPageLimit(fragmentList.size() - 1);
    }



    /**
     * 底部导航栏初始化
     */
    private void initBottomNav() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.navigation_route:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.navigation_culture:
                        viewPager.setCurrentItem(2);
                        break;
                    case R.id.navigation_user:
                        viewPager.setCurrentItem(3);
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }

}
