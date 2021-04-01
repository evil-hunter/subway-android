package com.example.myapplication.adapter;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.myapplication.R;
import com.example.myapplication.entity.Station;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import io.supercharge.shimmerlayout.ShimmerLayout;


public class StationAdapter extends BaseQuickAdapter<Station.HitsBean,BaseViewHolder> implements LoadMoreModule {

    public StationAdapter() {
        super(R.layout.item_station);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, Station.HitsBean hitsBean) {

        final ShimmerLayout shimmerLayout;
        ImageView imageGalleryItem;

        shimmerLayout = baseViewHolder.getView(R.id.simmeritem);
        imageGalleryItem = baseViewHolder.getView(R.id.imageStationItem);

        shimmerLayout.setShimmerColor(0X55FFFFFF);
        shimmerLayout.setShimmerAngle(0);
        shimmerLayout.startShimmerAnimation();

        //Glide初始化图片
        Glide.with(baseViewHolder.itemView)
                //加载的URL
                .load(hitsBean.getWebformatURL())
                //占位图初始化
                .placeholder(R.drawable.ic_crop_original_black_24dp)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        //加载成功则停止动画
                        shimmerLayout.stopShimmerAnimation();
                        return false;
                    }
                })
                //装载图片
                .into(imageGalleryItem);

    }

}
