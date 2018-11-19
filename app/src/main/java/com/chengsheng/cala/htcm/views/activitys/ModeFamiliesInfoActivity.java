package com.chengsheng.cala.htcm.views.activitys;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.views.fragments.ModeFamiliesFragment;

public class ModeFamiliesInfoActivity extends AppCompatActivity implements ModeFamiliesFragment.OnFragmentInteractionListener{

    private ModeFamiliesFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode_families_info);

        String modeParam = getIntent().getStringExtra("MODE");
        fragment = ModeFamiliesFragment.newInstance(modeParam,"");

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        ft.add(R.id.mode_fragment_container,fragment);
        ft.commit();


    }


    @Override
    public void onFragmentInteraction(Boolean isBack) {
        if(isBack){
            finish();
        }
    }
}
