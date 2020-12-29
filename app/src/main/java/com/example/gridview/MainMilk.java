package com.example.gridview;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.gridview.Adapter.AdapterBoSuuTapTabLayout;
import com.google.android.material.tabs.TabLayout;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

public class MainMilk extends AppCompatActivity {
    public static String DATABASE_NAME = "AppFoody.db";
    public static SQLiteDatabase database;
    private ViewPager mVp;
    ImageView btback;
    TextView tenloaisp;
    SliderView imageSlider;
    SlideAdb slideAdb;
    // Recycler View
    RecyclerView recyclerView;
    RecylerAdapter Adapter5;
    ArrayList<Recyler> recylerArrayList;
    List<byte[]> img_flip ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_milk);
        database= database = Database.initDatabase(this,DATABASE_NAME);
        tenloaisp = findViewById(R.id.textView);
        initView();

        btback = (ImageView) findViewById(R.id.ig_back);
        btback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        // Recycler quảng cáo 2


        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recylerArrayList = new ArrayList<>();
        Cursor boSuuTap = database.rawQuery("Select * from UuDai",null);

        while (boSuuTap.moveToNext())
        {
            Recyler recyler = new Recyler((byte[]) boSuuTap.getBlob(2),boSuuTap.getInt(0),boSuuTap.getString(1));
            recylerArrayList.add(recyler);
        }
        Adapter5 = new RecylerAdapter(recylerArrayList,this);
        recyclerView.setAdapter(Adapter5);
//        //slider
//
//        imageSlider = findViewById(R.id.slider);
//        Cursor cursorposter = database.rawQuery("Select * from PosterNgang",null);
//        img_flip = new ArrayList<>();
//        while (cursorposter.moveToNext())
//        {
//            byte[] hinhAnh = cursorposter.getBlob(2);
//            img_flip.add(hinhAnh);
//
//        }
//        slideAdb = new SlideAdb(img_flip);
//        imageSlider.setSliderAdapter(slideAdb);
//        imageSlider.setIndicatorAnimation(IndicatorAnimationType.WORM);
//        imageSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
//        imageSlider.startAutoCycle();
    }

    private void initView() {
        mVp = (ViewPager) findViewById(R.id.Vp_trasua);
        Intent intent =getIntent();
        int dk = intent.getIntExtra("LoaiSP",1);
        tenloaisp.setText(intent.getStringExtra("NameLSP"));
        List<String> dieukienlist = new ArrayList<>();
        dieukienlist.add("WHERE Shop.idLoaiSP ="+dk+" AND SanPham.idShop = Shop.idShop AND ChiTietShop.idShop = Shop.idShop AND sao >= 4 GROUP by Shop.idShop");
        dieukienlist.add("WHERE Shop.idLoaiSP ="+dk+" AND SanPham.idShop = Shop.idShop AND ChiTietShop.idShop = Shop.idShop GROUP by Shop.idShop");
        dieukienlist.add("WHERE Shop.idLoaiSP ="+dk+" AND SanPham.idShop = Shop.idShop AND ChiTietShop.idShop = Shop.idShop AND ThinhHanh = 1 GROUP by Shop.idShop");
        dieukienlist.add("WHERE Shop.idLoaiSP ="+dk+" AND SanPham.idShop = Shop.idShop AND ChiTietShop.idShop = Shop.idShop GROUP by Shop.idShop");
        if(dk==2){
            mVp.setAdapter(new AdapterKhamPhaTabLayout(getSupportFragmentManager(),dieukienlist));
            TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_trasua);
            tabLayout.setupWithViewPager(mVp);
            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    mVp.setCurrentItem(tab.getPosition());
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });
        }else{
            mVp.setAdapter(new AdapterBoSuuTapTabLayout(getSupportFragmentManager(),dieukienlist));
            TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_trasua);
            tabLayout.setupWithViewPager(mVp);
            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    mVp.setCurrentItem(tab.getPosition());
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });
        }

    }
}