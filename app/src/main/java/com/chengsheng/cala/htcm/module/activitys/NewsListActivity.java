package com.chengsheng.cala.htcm.module.activitys;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chengsheng.cala.htcm.base.BaseActivity;
import com.chengsheng.cala.htcm.constant.GlobalConstant;
import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.protocol.articleModel.NewsTitles;
import com.chengsheng.cala.htcm.network.ArticlesService;
import com.chengsheng.cala.htcm.network.MyRetrofit;
import com.chengsheng.cala.htcm.adapter.MainViewPagerAdapter;
import com.chengsheng.cala.htcm.module.fragments.NewsListFragment;
import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyao89.view.zloading.Z_TYPE;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class NewsListActivity extends BaseActivity implements NewsListFragment.OnFragmentInteractionListener {

    private TextView title;
    private ImageView back;
    private TabLayout newsSelectHeader;
    private ViewPager newsListChild;

    private String[] marks;
    private List<Fragment> fragments;

    private Retrofit retrofit;
    private ZLoadingDialog loadingDialog;


    @Override
    public int getLayoutId() {
        return R.layout.activity_news_list;
    }

    @Override
    public void initView() {
        loadingDialog = new ZLoadingDialog(this);
        loadingDialog.setLoadingBuilder(Z_TYPE.DOUBLE_CIRCLE);
        loadingDialog.setHintText("加载列表..");
        loadingDialog.setHintTextColor(getResources().getColor(R.color.colorPrimary));
        loadingDialog.setLoadingColor(getResources().getColor(R.color.colorText));
        loadingDialog.setDialogBackgroundColor(getResources().getColor(R.color.colorText));

        initViews();


        if (retrofit == null) {
            retrofit = MyRetrofit.createInstance().createURL(GlobalConstant.API_BASE_URL);
        }

//        loadingDialog.show();
        ArticlesService service = retrofit.create(ArticlesService.class);
        service.getNewsTitles()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<NewsTitles>() {
                    @Override
                    public void onNext(NewsTitles newsTitles) {
                        marks = new String[newsTitles.getMeta().getPagination().getCount()];
                        initDatas(newsTitles);
                        for (int i = 0; i < marks.length; i++) {
                            marks[i] = newsTitles.getItems().get(i).getName();
                            newsSelectHeader.getTabAt(i).setText(marks[i]);
//                            loadingDialog.cancel();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
//                        loadingDialog.cancel();
                    }

                    @Override
                    public void onComplete() {
//                        loadingDialog.cancel();
                    }
                });
    }

    @Override
    public void getData() {

    }

    @SuppressLint("CutPasteId")
    private void initViews() {
        title = findViewById(R.id.title_header_news_list).findViewById(R.id.menu_bar_title);
        back = findViewById(R.id.title_header_news_list).findViewById(R.id.back_login);

        newsSelectHeader = findViewById(R.id.news_select_header);
        newsListChild = findViewById(R.id.news_list_child);

        title.setText("资讯");

        back.setOnClickListener(v -> finish());

    }

    private void initDatas(NewsTitles newsTitles) {

        fragments = new ArrayList<>();

        for (int i = 0; i < newsTitles.getItems().size(); i++) {
            newsSelectHeader.addTab(newsSelectHeader.newTab().setText(newsTitles.getItems().get(i).getName()));
            fragments.add(NewsListFragment.newInstance(String.valueOf(newsTitles.getItems().get(i).getId()), ""));
        }

        MainViewPagerAdapter adapter = new MainViewPagerAdapter(getSupportFragmentManager(), fragments);
        newsListChild.setAdapter(adapter);

        newsSelectHeader.setupWithViewPager(newsListChild);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
