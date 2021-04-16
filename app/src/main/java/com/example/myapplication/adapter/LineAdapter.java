package com.example.myapplication.adapter;

import android.content.Context;
import android.graphics.Color;
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

    private int position;

    public LineAdapter(@LayoutRes int layoutResId, @Nullable List<LineBean> data) {
        super(layoutResId, data);
    }


    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemPosition(@org.jetbrains.annotations.Nullable LineBean item) {

        return super.getItemPosition(item);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder holder, LineBean item) {
        holder.setTextColor(R.id.lineName,holder.getLayoutPosition() == position ? Color.parseColor("#B22222") : Color.parseColor("#363636"));
        holder.setText(R.id.lineName, item.getName());

    }

    public void setSelection(int pos) {
        this.position = pos;
        notifyDataSetChanged();
    }

}
