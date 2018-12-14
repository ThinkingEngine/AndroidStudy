package com.chengsheng.cala.htcm.views.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chengsheng.cala.htcm.GlobalConstant;
import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.model.datamodel.articleModel.RecommendedItem;
import com.chengsheng.cala.htcm.model.datamodel.articleModel.RecommendedNews;
import com.chengsheng.cala.htcm.network.ArticlesService;
import com.chengsheng.cala.htcm.network.MyRetrofit;
import com.chengsheng.cala.htcm.views.customviews.FooterAdapter;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyao89.view.zloading.Z_TYPE;


import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;


public class NewsListFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private XRecyclerView newsList;

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private Retrofit retrofit;
    private ZLoadingDialog loadingDialog;

    public NewsListFragment() {
    }


    public static NewsListFragment newInstance(String param1, String param2) {
        NewsListFragment fragment = new NewsListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        loadingDialog = new ZLoadingDialog(getContext());
        loadingDialog.setLoadingBuilder(Z_TYPE.DOUBLE_CIRCLE);
        loadingDialog.setHintText("加载新闻");
        loadingDialog.setLoadingColor(getContext().getResources().getColor(R.color.colorPrimary));
        loadingDialog.setHintTextColor(getContext().getResources().getColor(R.color.colorPrimary));
        loadingDialog.setDialogBackgroundColor(getContext().getResources().getColor(R.color.colorText));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_news_list, container, false);

        newsList = rootView.findViewById(R.id.news_list);
        getNews(true);
        newsList.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                getNews(false);
            }

            @Override
            public void onLoadMore() {

            }
        });

        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void getNews(final boolean loading) {
        if (retrofit == null) {
            retrofit = MyRetrofit.createInstance().createURL(GlobalConstant.API_BASE_URL);
        }

        ArticlesService service = retrofit.create(ArticlesService.class);
        if (loading) {
            loadingDialog.show();
        }
        service.getNewsTitlesFiliter("articleType.id=" + mParam1, "online_at:desc")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<RecommendedNews>() {
                    @Override
                    public void onNext(RecommendedNews recommendedNews) {
                        if (loading) {
                            loadingDialog.cancel();
                        }else{
                            newsList.refreshComplete();
                        }
                        FooterAdapter adapter = new FooterAdapter(recommendedNews.getItems(), getContext());
                        newsList.setLayoutManager(new LinearLayoutManager(getContext()));
                        newsList.setAdapter(adapter);

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (loading) {
                            loadingDialog.cancel();
                        }else{
                            newsList.refreshComplete();
                        }
                        Toast.makeText(getContext(), "网络异常！", Toast.LENGTH_SHORT).show();
                        List<RecommendedItem> list = new ArrayList<>();
                        FooterAdapter adapter = new FooterAdapter(list, getContext());
                        adapter.addFooterView(LayoutInflater.from(getContext()).inflate(R.layout.no_content_layout, null));
                        newsList.setLayoutManager(new LinearLayoutManager(getContext()));
                        newsList.setAdapter(adapter);
                    }

                    @Override
                    public void onComplete() {
                        loadingDialog.cancel();
                    }
                });
    }

}
