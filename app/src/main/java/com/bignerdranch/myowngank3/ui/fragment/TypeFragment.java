package com.bignerdranch.myowngank3.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.myowngank3.R;
import com.bignerdranch.myowngank3.adapter.GanHuoAdapter;
import com.bignerdranch.myowngank3.bean.GanHuo;
import com.bignerdranch.myowngank3.retrofit.GankRetrofit;
import com.bignerdranch.myowngank3.retrofit.GankService;
import com.bignerdranch.myowngank3.ui.activity.GanHuoActivity;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class TypeFragment extends Fragment implements RecyclerArrayAdapter.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener
{
    private EasyRecyclerView recyclerView;
    private List<GanHuo.Result> ganhuoList;
    private GanHuoAdapter ganHuoAdapter;
    private int page=1;
    private Handler handler=new Handler();
    private String title;
    public static TypeFragment getInstance(String title)
    {
        TypeFragment typeFragment=new TypeFragment();
        Bundle bundle=new Bundle();
        bundle.putString("title",title);
        typeFragment.setArguments(bundle);
        return typeFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle=getArguments();
        title=bundle.getString("title");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_layout,container,false);
        initView(view);
        return view;
    }

    private void initView(View view)
    {
        ganhuoList=new ArrayList<>();
        recyclerView=(EasyRecyclerView)view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ganHuoAdapter=new GanHuoAdapter(getContext());
        recyclerView.setAdapter(ganHuoAdapter);
        ganHuoAdapter.setMore(R.layout.load_more_layout,this);
        ganHuoAdapter.setNoMore(R.layout.no_more_layout);
        ganHuoAdapter.setError(R.layout.error_layout);
        ganHuoAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent=new Intent(getContext(), GanHuoActivity.class);
                intent.putExtra("desc",ganHuoAdapter.getItem(position).getDesc());
                intent.putExtra("url",ganHuoAdapter.getItem(position).getUrl());
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
                        Log.d("666","onSubscribe");
                    }

                    @Override
                    public void onNext(GanHuo ganHuo) {
                           ganhuoList=ganHuo.getResults();
                           ganHuoAdapter.addAll(ganhuoList);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("666","onError"+e);

                    }

                    @Override
                    public void onComplete() {
                        Log.d("666","onCompleted");

                    }
                });
    }

    @Override
    public void onRefresh() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ganHuoAdapter.clear();
                if (title.equals("Android"))
                {
                    getData("Android",20,1);
                }else if (title.equals("iOS"))
                {
                    getData("iOS",20,1);
                }
                else if (title.equals("休息视频"))
                {
                    getData("休息视频",20,1);
                }else if (title.equals("拓展资源"))
                {
                    getData("拓展资源",20,1);
                }else if (title.equals("前端"))
                {
                    getData("前端",20,1);
                } else if (title.equals("all"))
                {
                    getData("all",20,1);
                }
            page = 2;
            }
        },1000);
    }

    @Override
    public void onLoadMore() {
              handler.postDelayed(new Runnable() {
                  @Override
                  public void run() {
                      if (title.equals("Android")){
                          getData("Android",20,page);
                      }else if (title.equals("iOS")){
                          getData("iOS",20,page);
                      } else if (title.equals("休息视频"))
                      {
                          getData("休息视频",20,page);
                      }else if(title.equals("拓展资源"))
                      {
                          getData("拓展资源",20,page);
                      }else if(title.equals("前端"))
                      {
                          getData("前端",20,page);
                      }else if(title.equals("all"))
                      {
                          getData("all",20,page);
                      }
                      page++;
                  }
              },1000);
    }
}
