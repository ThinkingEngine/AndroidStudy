package com.chengsheng.cala.htcm.network;

import com.chengsheng.cala.htcm.model.datamodel.articleModel.ArticleCollect;
import com.chengsheng.cala.htcm.model.datamodel.articleModel.NewsTitles;
import com.chengsheng.cala.htcm.model.datamodel.articleModel.RecommendedNews;


import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface ArticlesService {

    @GET("api/news/articles/recommended")
    Observable<RecommendedNews> getRecommendedNews(@Query("sort_fields") String sortFields);

    @GET("api/news/article-types/enable")
    Observable<NewsTitles> getNewsTitles();

    @GET("api/news/articles/online")
    Observable<RecommendedNews> getNewsTitlesFiliter(@Query("filters") String filter, @Query("sort_fields") String sortFields);

    @GET
    Observable<ArticleCollect> articleHasCollect(@Header("Authorization") String header, @Url String url);//检查文章是否被收藏

    @POST
    Observable<ResponseBody> articleCollect(@Header("Authorization") String header, @Url String url);//收藏文章

    @DELETE
    Observable<ResponseBody> articleCancelCollect(@Header("Authorization") String header, @Url String url);//取消文章收藏
}
