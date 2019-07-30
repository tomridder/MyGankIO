package com.bignerdranch.myowngank3.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.myowngank3.R;
import com.bignerdranch.myowngank3.adapter.MeiZhiAdapter;
import com.bignerdranch.myowngank3.bean.GanHuo;
import com.bignerdranch.myowngank3.retrofit.GankRetrofit;
import com.bignerdranch.myowngank3.retrofit.GankService;
import com.bignerdranch.myowngank3.ui.activity.MeiZhiActivity;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class GirlFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, RecyclerArrayAdapter.OnLoadMoreListener
{
    private EasyRecyclerView recyclerView;
    private List<GanHuo.Result> ganHuoList;
    private MeiZhiAdapter meiZhiAdapter;
    private int page = 1;
    Toolbar toolbar;
    private Handler handler=new Handler();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
           View view=inflater.inflate(R.layout.fragment_girl,container,false);
           initView(view);
           return view;
    }

    private void initView(View view)
    {
        toolbar=(Toolbar)view.findViewById(R.id.toolbar);
        
//        toolbar.setTitle("妹子图片");
//        toolbar.setTitleTextColor(getContext().getResources().getColor(R.color.white));
        ganHuoList=new ArrayList<>();
        recyclerView=(EasyRecyclerView)view.findViewById(R.id.recycler_view1);
        StaggeredGridLayoutManager staggeredGridLayoutManager=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        meiZhiAdapter=new MeiZhiAdapter(getContext());
        recyclerView.setAdapterWithProgress(meiZhiAdapter);
        meiZhiAdapter.setMore(R.layout.load_more_layout,this);
        meiZhiAdapter.setNoMore(R.layout.no_more_layout);
        meiZhiAdapter.setError(R.layout.error_layout);
        meiZhiAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent=new Intent(getContext(), MeiZhiActivity.class);
                intent.putExtra("desc",meiZhiAdapter.getItem(position).getDesc());
                intent.putExtra("url",meiZhiAdapter.getItem(position).getUrl());
                startActivity(intent);
            }
        });

        recyclerView.setRefreshListener(this);
        onRefresh();
    }
     private void getData(String type,int count,int page)
     {
         GankRetrofit.getRetrofit()
                 .create(GankService.class)
                 .getGanHuo(type,count,page)
                 .subscribeOn(Schedulers.io())
                 .observeOn(AndroidSchedulers.mainThread())
                 .subscribe(new Observer<GanHuo>() {
                     @Override
                     public void onSubscribe(Disposable d) {
                         Log.d("6666","onSubscribe");
                     }

                     @Override
                     public void onNext(GanHuo ganHuo) {
                         ganHuoList=ganHuo.getResults();
                         meiZhiAdapter.addAll(ganHuoList);
                     }

                     @Override
                     public void onError(Throwable e) {
                         Log.d("6666","onError"+e);
                         Snackbar.make(getView(),"没有网络 抱歉~~",Snackbar.LENGTH_LONG).show();
                     }

                     @Override
                     public void onComplete() {
                         Log.d("6666","onComplete");
                     }
                 });
     }
    @Override
    public void onRefresh() {
          handler.postDelayed(new Runnable()
          {
              @Override
              public void run()
              {
                  meiZhiAdapter.clear();
                  getData("福利",20,1);
                  page=2;
              }

          },1000);
    }

    @Override
    public void onLoadMore() {
              handler.postDelayed(new Runnable() {
                  @Override
                  public void run() {
                      getData("福利",20,page);
                      page++;
                  }
              },1000);
    }
}
