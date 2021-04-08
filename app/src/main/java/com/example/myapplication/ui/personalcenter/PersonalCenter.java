package com.example.myapplication.ui.personalcenter;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.ui.login.Login;
import com.example.myapplication.utils.UITools;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;

public class PersonalCenter extends Fragment {

    private static final String TAG = "Personalcenter";

    final ArrayList<Fragment> fragmentList = new ArrayList<>();

    private String isToken;
    private MaterialButton logonbtn;
    private MaterialToolbar toolbar;

    public static PersonalCenter newInstance() {
        return new PersonalCenter();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = LayoutInflater.from(getContext()).inflate(R.layout.fragment_personal_center, container, false);
        initView(root);

        return root;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (resultCode) {
            case 1:
                Toast.makeText(getContext(), "取消登录", Toast.LENGTH_SHORT).show();
                break;
            case 2:
                //gettoken
                Log.d(TAG, "PersonalCenter token -> " );
                //登录成功后执行保存token操作
                break;
            default:
                break;
        }
    }

    private void initView(View root) {

        toolbar = root.findViewById(R.id.personalCenterToolbar);

        logonbtn = root.findViewById(R.id.logonbtn);

        //状态栏文字透明
        UITools.makeStatusBarTransparent(getActivity());

        //修复标题栏与状态栏重叠
        UITools.fitTitleBar(getActivity(), toolbar);


        /**
         * toobar 菜单点击事件
         */
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.logout:
                        initLogout();
                        break;
                    default:
                        break;
                }
                return false;
            }
        });

        logonbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getContext(), Login.class);
                startActivityForResult(intent, 2);
            }
        });

    }

    /**
     * 初始化退出登录
     */
    private void initLogout() {
        new MaterialAlertDialogBuilder(getContext())
                .setTitle("确定要退出登录吗")
                .setMessage("退出登录后需要重新输入账号信息哦")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getContext(), "退出登录", Toast.LENGTH_LONG).show();

                        toolbar.getMenu().clear();

                        logonbtn.setVisibility(View.VISIBLE);

                        getParentFragmentManager().getFragments().get(1).onResume();

                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
    }

}
