package com.example.myapplication.ui.culture;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.utils.UITools;
import com.google.android.material.appbar.MaterialToolbar;


public class Culture extends Fragment {

    private static final String TAG = "Personalcenter";

    ConstraintLayout constraintLayout;
    private MaterialToolbar toolbar;
    EditText startStation;
    EditText endStation;

    public static Culture newInstance() {
        return new Culture();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = LayoutInflater.from(getContext()).inflate(R.layout.fragment_culture, container, false);
        initView(root);

        return root;
    }

    private void initView(View root) {
        toolbar = root.findViewById(R.id.cultureToolbar);
        startStation = root.findViewById(R.id.startStationEdit);
        endStation = root.findViewById(R.id.endStationEdit);

        //状态栏文字透明
        UITools.makeStatusBarTransparent(getActivity());

        //修复标题栏与状态栏重叠
        UITools.fitTitleBar(getActivity(), toolbar);
    }
}
