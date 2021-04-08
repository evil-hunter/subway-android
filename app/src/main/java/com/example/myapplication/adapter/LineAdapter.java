package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.myapplication.R;
import com.example.myapplication.entity.LineBean;
import com.example.myapplication.entity.StationBean;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class LineAdapter extends BaseQuickAdapter<LineBean, BaseViewHolder> {

    public LineAdapter(@LayoutRes int layoutResId, @Nullable List<LineBean> data) {
        super(layoutResId, data);
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    @Override
    protected void convert(@NotNull BaseViewHolder holder, LineBean item) {
        holder.setText(R.id.lineName, item.getName());

    }

}
