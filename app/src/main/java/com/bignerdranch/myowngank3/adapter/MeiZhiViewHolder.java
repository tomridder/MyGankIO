package com.bignerdranch.myowngank3.adapter;

import android.view.ViewGroup;
import android.widget.ImageView;

import com.bignerdranch.myowngank3.R;
import com.bignerdranch.myowngank3.bean.GanHuo;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

public class MeiZhiViewHolder extends BaseViewHolder<GanHuo.Result>
{
    private ImageView imageView;

    public MeiZhiViewHolder(ViewGroup parent) {
        super(parent, R.layout.meizhi_item);
        imageView = $(R.id.image);
    }

    @Override
    public void setData(GanHuo.Result data) {
        super.setData(data);
        Glide.with(getContext())
                .load(data.getUrl())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imageView);
    }
}
