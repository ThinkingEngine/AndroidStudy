package com.chengsheng.cala.htcm.network;


import com.chengsheng.cala.htcm.model.datamodel.ExamReportModel.CompareModel;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ExamReportInterface {

    @FormUrlEncoded
    @POST("api/physical-exam-order/exam-result/comparison")
    Observable<CompareModel> getCompareReport(@Header("Authorization") String header, @Field("first_order_id") String first, @Field("second_order_id") String second);
}
