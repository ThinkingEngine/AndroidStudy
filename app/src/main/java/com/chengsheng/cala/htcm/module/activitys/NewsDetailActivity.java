package com.chengsheng.cala.htcm.module.activitys;

import android.os.Bundle;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.chengsheng.cala.htcm.base.BaseActivity;
import com.chengsheng.cala.htcm.constant.GlobalConstant;
import com.chengsheng.cala.htcm.HTCMApp;
import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.protocol.articleModel.ArticleCollect;
import com.chengsheng.cala.htcm.protocol.articleModel.RecommendedItem;
import com.chengsheng.cala.htcm.network.ArticlesService;
import com.chengsheng.cala.htcm.network.MyRetrofit;
import com.chengsheng.cala.htcm.widget.ShareDialog;


import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;

public class NewsDetailActivity extends BaseActivity {

    private TextView title;
    private ImageView back;
    private ImageView collectNews;
    private ImageView shareNews;
    private WebView newsContent;

    private Retrofit retrofit;
    private HTCMApp app;


    @Override
    public int getLayoutId() {
        return R.layout.activity_news_detail;
    }

    @Override
    public void initView() {
        app = HTCMApp.create(getApplicationContext());

        Bundle bundle = getIntent().getExtras();
        final RecommendedItem recommendedItem = (RecommendedItem) bundle.getSerializable("NEWS_DETAIL");

        initViews();

        if (retrofit == null) {
            retrofit = MyRetrofit.createInstance().createURL(GlobalConstant.API_BASE_URL);
        }
        ArticlesService service = retrofit.create(ArticlesService.class);
        service.articleHasCollect(app.getTokenType() + " " + app.getAccessToken(), GlobalConstant.ARTICLE_COLLECT + recommendedItem.getId())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<ArticleCollect>() {
                    @Override
                    public void onNext(ArticleCollect responseBody) {
                        if (responseBody.isIs_collected()) {
                            collectNews.setSelected(true);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                        EventBus.getDefault().post("","");

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        setViews(recommendedItem);

        //发布事件 通知主页更新新闻浏览数据
        EventBus.getDefault().post("",GlobalConstant.UPDATE_NEWS_BOWSE);

        collectNews.setOnClickListener(v -> {
            if (collectNews.isSelected()) {
                collectCancelArticle(recommendedItem);
            } else {
                collectArticle(recommendedItem);
            }
        });
    }

    @Override
    public void getData() {

    }

    private void initViews() {
        title = findViewById(R.id.title_header_news_details).findViewById(R.id.menu_bar_title);
        back = findViewById(R.id.title_header_news_details).findViewById(R.id.back_login);
        collectNews = findViewById(R.id.title_header_news_details).findViewById(R.id.collect_icon);
        shareNews = findViewById(R.id.title_header_news_details).findViewById(R.id.share);

        newsContent = findViewById(R.id.news_content);

        title.setText("资讯详情");

        //新闻分享方法
        shareNews.setOnClickListener(v -> new ShareDialog()
                .build(this)
                .showDialog()
                .setOnShareListener(new ShareDialog.OnShareClickListener() {
                    @Override
                    public void shareToWeChat() {

                    }

                    @Override
                    public void shareToMoment() {

                    }

                    @Override
                    public void shareToQQ() {

                    }

                    @Override
                    public void shareToQZone() {

                    }

                    @Override
                    public void copyLink() {

                    }
                }));

        back.setOnClickListener(v -> finish());
    }

    private void setViews(RecommendedItem recommendedItem) {
        newsContent.loadUrl(recommendedItem.getUrl());
    }

    private void collectArticle(RecommendedItem recommendedItem) {
        if (retrofit == null) {
            retrofit = MyRetrofit.createInstance().createURL(GlobalConstant.API_BASE_URL);
        }

        showLoading();
        ArticlesService service = retrofit.create(ArticlesService.class);
        service.articleCollect(app.getTokenType() + " " + app.getAccessToken(), GlobalConstant.ARTICLE_COLLECT + recommendedItem.getId())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<ResponseBody>() {
                    @Override
                    public void onNext(ResponseBody responseBody) {
                        collectNews.setSelected(true);
                        hideLoading();
                        showShortToast("收藏成功!");
                    }

                    @Override
                    public void onError(Throwable e) {
                        hideLoading();
                        showShortToast("收藏失败 请重新再试");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void collectCancelArticle(RecommendedItem recommendedItem) {
        if (retrofit == null) {
            retrofit = MyRetrofit.createInstance().createURL(GlobalConstant.API_BASE_URL);
        }

        showLoading();
        ArticlesService service = retrofit.create(ArticlesService.class);
        service.articleCancelCollect(app.getTokenType() + " " + app.getAccessToken(), GlobalConstant.ARTICLE_COLLECT + recommendedItem.getId())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<ResponseBody>() {
                    @Override
                    public void onNext(ResponseBody responseBody) {
                        collectNews.setSelected(false);
                        hideLoading();
                        showShortToast("已取消收藏!");
                    }

                    @Override
                    public void onError(Throwable e) {
                        hideLoading();
                        showShortToast("取消收藏失败 请重新再试");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

//    @Subscriber(tag = "",mode = )
//    private
}
