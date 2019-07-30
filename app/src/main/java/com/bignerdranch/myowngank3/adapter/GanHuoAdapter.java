package com.bignerdranch.myowngank3.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.bignerdranch.myowngank3.bean.GanHuo;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

public class GanHuoAdapter extends RecyclerArrayAdapter<GanHuo.Result>
{
    public GanHuoAdapter(Context context)
    {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new GanHuoViewHolder(parent);
    }
}
