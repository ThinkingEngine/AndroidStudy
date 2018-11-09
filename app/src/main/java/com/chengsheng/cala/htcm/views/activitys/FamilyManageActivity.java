package com.chengsheng.cala.htcm.views.activitys;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.views.fragments.AddFamilyFragment;
import com.chengsheng.cala.htcm.views.fragments.FamilyListFragment;

public class FamilyManageActivity extends AppCompatActivity implements FamilyListFragment.OnFamilyListInteractionListener,
        AddFamilyFragment.OnAddFamilyFragmentInteractionListener {

    private ImageView backHomeButton;
    private TextView titleHeader;
    private TextView addFamily;

    private FamilyListFragment familyListFragment;
    private AddFamilyFragment addFamilyFragment;

    private String stats = "manage";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_manage);

        initViews();

        familyListFragment = FamilyListFragment.newInstance("", "");
        addFamilyFragment = AddFamilyFragment.newInstance("", "");

        final FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        ft.add(R.id.family_manage_container, familyListFragment);
        ft.commit();
        stats = "manage";

        addFamily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = fm.beginTransaction();
                if (stats.equals("manage")) {
                    ft.replace(R.id.family_manage_container, addFamilyFragment);
                    ft.commit();
                    stats = "add";
                    titleHeader.setText("添加家人");
                    addFamily.setVisibility(View.INVISIBLE);
                } else {
                    Log.e("states", "当前状态错误");
                }

            }
        });

        backHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = fm.beginTransaction();
                if (stats.equals("add")) {
                    ft.add(R.id.family_manage_container, familyListFragment);
                    ft.commit();
                    stats = "manage";
                    titleHeader.setText("家人管理");
                    addFamily.setVisibility(View.VISIBLE);

                } else if (stats.equals("manage")) {
                    Intent intent = new Intent(FamilyManageActivity.this, HomePageActivity.class);
                    startActivity(intent);
                }
            }
        });

    }

    private void initViews() {
        backHomeButton = (ImageView) findViewById(R.id.title_header_family_manage).findViewById(R.id.back_login);
        titleHeader = (TextView) findViewById(R.id.title_header_family_manage).findViewById(R.id.menu_bar_title);
        addFamily = (TextView) findViewById(R.id.title_header_family_manage).findViewById(R.id.message_mark_text);

        titleHeader.setText("家人管理");
        addFamily.setText("添加家人");

        addFamily.setVisibility(View.VISIBLE);
    }

    @Override
    public void onAddFamilyFragmentInteraction(Bundle bundle) {

    }

    @Override
    public void onFamilyListInteraction(Bundle data) {

    }
}