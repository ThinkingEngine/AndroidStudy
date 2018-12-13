package com.chengsheng.cala.htcm.views.activitys;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.chengsheng.cala.htcm.BaseActivity;
import com.chengsheng.cala.htcm.GlobalConstant;
import com.chengsheng.cala.htcm.HTCMApp;
import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.model.datamodel.articleModel.ArticleCollect;
import com.chengsheng.cala.htcm.model.datamodel.articleModel.RecommendedItem;
import com.chengsheng.cala.htcm.network.ArticlesService;
import com.chengsheng.cala.htcm.network.MyRetrofit;

import java.io.IOException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.HttpException;
import retrofit2.Retrofit;

public class NewsDetailActivity extends BaseActivity {

    private TextView title;
    private ImageView back;
    private ImageView collectNews;
    private ImageView shareNews;
    private TextView newsDetailHeader, newsDetailMarks, newsDetailDate;
    private TextView newsBrowseNum;
    private WebView newsContent;

    private Retrofit retrofit;
    private HTCMApp app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = HTCMApp.create(getApplicationContext());
        setContentView(R.layout.activity_news_detail);

        if (retrofit == null) {
            retrofit = MyRetrofit.createInstance().createURL(GlobalConstant.API_BASE_URL);
        }

        initViews();

        Bundle bundle = getIntent().getExtras();
        final RecommendedItem recommendedItem = (RecommendedItem) bundle.getSerializable("NEWS_DETAIL");

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
                        if (e instanceof HttpException) {
                            ResponseBody body = ((HttpException) e).response().errorBody();
                            try {
                                Log.e("TAG", "onError" + body.string());
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });

        setViews(recommendedItem);

        collectNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("TAG","点击标签1");
                if(collectNews.isSelected()){
                    collectCancelArticle(recommendedItem);
                    Log.e("TAG","点击标签2");
                }else{
                    collectArticle(recommendedItem);
                    Log.e("TAG","点击标签3");
                }
            }
        });

    }

    private void initViews() {
        title = findViewById(R.id.title_header_news_details).findViewById(R.id.menu_bar_title);
        back = findViewById(R.id.title_header_news_details).findViewById(R.id.back_login);
        collectNews = findViewById(R.id.title_header_news_details).findViewById(R.id.collect_icon);
        shareNews = findViewById(R.id.title_header_news_details).findViewById(R.id.share);

//        newsDetailHeader = findViewById(R.id.news_detail_header);
//        newsDetailMarks = findViewById(R.id.news_detail_marks);
//        newsDetailDate = findViewById(R.id.news_detail_date);
//        newsBrowseNum = findViewById(R.id.news_browse_num);
        newsContent = findViewById(R.id.news_content);

        title.setText("咨讯详情");
    }

    private void setViews(RecommendedItem recommendedItem) {
//        newsDetailHeader.setText(recommendedItem.getTitle());
//        newsDetailMarks.setText(recommendedItem.getArticle_type_name());
//        newsDetailDate.setText(recommendedItem.getOnline_at());
//        newsBrowseNum.setText(String.valueOf(recommendedItem.getBasic_read_num()));

        newsContent.loadUrl(recommendedItem.getUrl());
    }

    private void collectArticle(RecommendedItem recommendedItem) {
        if (retrofit == null) {
            retrofit = MyRetrofit.createInstance().createURL(GlobalConstant.API_BASE_URL);
        }

        ArticlesService service = retrofit.create(ArticlesService.class);
        service.articleCollect(app.getTokenType() + " " + app.getAccessToken(), GlobalConstant.ARTICLE_COLLECT + recommendedItem.getId())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<ResponseBody>() {
                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            collectNews.setSelected(true);
                            Log.e("TAG", "收藏操作成功" + responseBody.string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            ResponseBody body = ((HttpException) e).response().errorBody();
                            try {
                                Log.e("TAG", "收藏操作失败" + body.string());
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }
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

        ArticlesService service = retrofit.create(ArticlesService.class);
        service.articleCancelCollect(app.getTokenType() + " " + app.getAccessToken(), GlobalConstant.ARTICLE_COLLECT + recommendedItem.getId())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<ResponseBody>() {
                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            Log.e("TAG", "取消收藏操作成功" + responseBody.string());
                            collectNews.setSelected(false);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                        if (e instanceof HttpException) {
                            ResponseBody body = ((HttpException) e).response().errorBody();
                            try {
                                Log.e("TAG", "取消收藏操作失败" + body.string());
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
