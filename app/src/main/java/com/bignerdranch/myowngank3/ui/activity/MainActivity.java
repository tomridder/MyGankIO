package com.bignerdranch.myowngank3.ui.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.bignerdranch.myowngank3.R;
import com.bignerdranch.myowngank3.ui.fragment.GirlFragment;
import com.bignerdranch.myowngank3.ui.fragment.OtherFragment;
import com.bignerdranch.myowngank3.ui.fragment.HotFragment;

public class MainActivity extends AppCompatActivity
{

    private BottomNavigationView bottomNavigationView;
    private GirlFragment girlFragment;
    private HotFragment hotFragment;
    private OtherFragment historyFragment;
    private Fragment[] fragments;
    private int lastfragment;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initPermisssion();
        initFragment();


    }

    private void initPermisssion()
    {
        if(ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }
        if(ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.READ_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch(requestCode)
        {
            case 1:

                break;
        }
    }

    private void initFragment()
    {
        girlFragment=new GirlFragment();
        hotFragment=new HotFragment();
        historyFragment=new OtherFragment();
        fragments=new Fragment[]{hotFragment,girlFragment,historyFragment};
        lastfragment=0;
        getSupportFragmentManager().beginTransaction().replace(R.id.main_view,hotFragment).show(hotFragment).commit();
        bottomNavigationView=(BottomNavigationView)findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
            {
                switch (menuItem.getItemId())
                {
                    case R.id.hot:
                    {
                        if(lastfragment!=0)
                        {
                            switchFragment(lastfragment,0);
                            lastfragment=0;
                        }
                        return true;
                    }
                    case R.id.girl:
                    {
                        if(lastfragment!=1)
                        {
                            switchFragment(lastfragment,1);
                            lastfragment=1;
                        }
                        return true;
                    }
                    case R.id.history:
                    {
                        if(lastfragment!=2)
                        {
                            switchFragment(lastfragment,2);
                            lastfragment=2;

                        }

                        return true;
                    }
                }
                return false;
            }
        });

    }

    private void switchFragment(int lastfragment,int index)
    {
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.hide(fragments[lastfragment]);
        if(fragments[index].isAdded()==false)
        {
            transaction.add(R.id.main_view,fragments[index]);
        }
        transaction.show(fragments[index]).commitAllowingStateLoss();
    }
}
