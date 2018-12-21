package com.chengsheng.cala.htcm.network;


import com.chengsheng.cala.htcm.protocol.AppointmentDetail;
import com.chengsheng.cala.htcm.protocol.AssistantList;
import com.chengsheng.cala.htcm.protocol.ExamApponitments;
import com.chengsheng.cala.htcm.protocol.ExamItemsList;
import com.chengsheng.cala.htcm.protocol.ExamReportList;
import com.chengsheng.cala.htcm.protocol.FamiliesDetailInfo;
import com.chengsheng.cala.htcm.protocol.FamiliesList;
import com.chengsheng.cala.htcm.protocol.LoginData;
import com.chengsheng.cala.htcm.protocol.Message;
import com.chengsheng.cala.htcm.protocol.OrderID;
import com.chengsheng.cala.htcm.protocol.SMSVerificationResult;
import com.chengsheng.cala.htcm.protocol.URLResult;
import com.chengsheng.cala.htcm.protocol.ZhiFuBaoSign;
import com.chengsheng.cala.htcm.protocol.childmodela.ExamReportDetial;
import com.chengsheng.cala.htcm.protocol.childmodela.MessageList;
import com.chengsheng.cala.htcm.protocol.childmodela.NureadMessage;
import com.chengsheng.cala.htcm.protocol.childmodelb.BeforeExam;
import com.chengsheng.cala.htcm.protocol.childmodelb.ExamOrder;
import com.chengsheng.cala.htcm.protocol.childmodelb.ExamOrderDetail;
import com.chengsheng.cala.htcm.protocol.childmodelb.IntelligentCheck;
import com.chengsheng.cala.htcm.protocol.childmodelb.UserExamDetail;


import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface NetService {

    @FormUrlEncoded
    @POST("token")
    Observable<LoginData> login(@Field("client_id") String c_id,
                                @Field("grant_type") String grantType,
                                @Field("username") String userName,
                                @Field("password") String passWord,
                                @Field("client_secret") String clientSecret,
                                @Field("scope") String scope);


    @FormUrlEncoded
    @POST("sms-verification-codes")
    Observable<SMSVerificationResult> getSMSVerification(@Field("phone_number") String phoneNumber);

    @FormUrlEncoded
    @POST("account-registrations")
    Observable<ResponseBody> commitRegistrInfo(@Field("phone_number") String phoneNumber,
                                               @Field("password") String passWord,
                                               @Field("vcerification_code_id") String verificationCodeId,
                                               @Field("vcerification_code") String verificationCode,
                                               @Field("client_id") String clientId);

    @GET("api/physical-exam-item/exam-packages/enable")
    Observable<ExamApponitments> getComboInfo();//获取套餐信息。

    @GET("api/physical-exam-item/exam-packages/enable")
    Observable<ExamApponitments> getComboInfoFilters(@Query("filters")String filters,@Query("page")String page,@Query("sort_fields")String sortFields);//获取套餐信息。(条件)

    @GET
    Observable<AppointmentDetail> getCombonDetail(@Url String url, @Header("Authorization") String header);//获取套餐详情

    @GET
    Observable<ResponseBody> checkIsCollect(@Url String url, @Header("Authorization") String header);//检查

    @POST
    Observable<ResponseBody> collectCombo(@Url String url, @Header("Authorization") String header);//收藏套餐


    @DELETE
    Observable<ResponseBody> cancelCollect(@Url String url, @Header("Authorization") String header);//取消收藏

    @DELETE
    Observable<ResponseBody> deleteFamilies(@Url String url, @Header("Authorization") String header);//解绑家人

    @GET("api/family/account-family-members")
    Observable<FamiliesList> getFamiliesList(@Header("Authorization") String header);//获取家人列表

    @GET
    Observable<FamiliesDetailInfo> getFamiliesDetail(@Url String url, @Header("Authorization") String header);//获取家人详细信息

    @FormUrlEncoded
    @POST("api/family/account-family-members/sms-validation-code")
    Observable<Message> addFamiliesCodeRequest(@Header("Authorization") String header, @Field("mobile") String cellphone);

    @Multipart
    @POST("api/file-storage/files")
    Observable<URLResult> uploadFile(@Header("Authorization") String header, @PartMap Map<String, RequestBody> map, @Part MultipartBody.Part file);//上传文件


    @FormUrlEncoded
    @POST("api/family/account-family-members")
    Observable<ResponseBody> upLoadFamiliesInfo(@Header("Authorization") String header, @FieldMap Map<String, String> params);

    @Multipart
    @POST("api/family/account-family-members/{familiesId}/authentication")
    Observable<ResponseBody> authenticationFamilies(@Header("Authorization") String header, @Path("familiesId") String familiesID, @PartMap Map<String, RequestBody> map);//家人认证

    @GET("api/physical-exam-order/customer-exams")
    Observable<ExamItemsList> getExamList(@Header("Authorization") String header);//获取体检列表（无参数）

    @GET("api/physical-exam-order/customer-exams")
    Observable<ExamItemsList> getExamListFilter(@Header("Authorization") String header,@Query("customer_ids")String customerIds);

    @GET("api/physical-exam-order/customer-exams")
    Observable<ExamItemsList> getExamListMore(@Header("Authorization") String header, @Query("page") String page);//

    @GET("api/physical-exam-order/customer-exams")
    Observable<ExamItemsList> getExamListMode(@Header("Authorization") String header, @Query("mode") String mode,@Query("customer_ids")String customerIds);

    @GET("api/physical-exam-order/customer-exams")
    Observable<ExamItemsList> getExamListModeMode(@Header("Authorization") String header, @Query("mode") String mode, @Query("page") String page,@Query("customer_ids")String customerIds);

    @FormUrlEncoded
    @POST("api/physical-exam-order/orders")
    Observable<OrderID> putAppointment(@Header("Authorization") String header, @FieldMap Map<String, String> params);

    @GET
    Observable<ZhiFuBaoSign> getAliSign(@Url String url, @Header("Authorization") String header);

    @GET("api/physical-exam-order/issued-reports")
    Observable<ExamReportList> getIssuedReport(@Header("Authorization") String header, @Query("customer_id") String id);

    @GET
    Observable<ExamReportDetial> getExamReportDetial(@Url String url, @Header("Authorization") String header);

    @GET("api/physical-exam-order/AI")
    Observable<AssistantList> getAIAssistants(@Header("Authorization") String header,@Query("exclude_closed")String excludeClosed,@Query("page") String page);//智能助理列表.

    @GET("api/message/messages/unread")
    Observable<NureadMessage> getUnreadMessageNum(@Header("Authorization") String header);

    @GET("api/message/messages")
    Observable<MessageList> getMessageList(@Header("Authorization") String header, @Query("page") String page);

    @GET
    Observable<UserExamDetail> getUserExamDetail(@Header("Authorization") String header, @Url String url);//获取用户的体检详情

    @GET("api/physical-exam-order/customer-exams/{orderID}/notice")
    Observable<BeforeExam> getBeforeExamNotice(@Header("Authorization") String header, @Path("orderID") String orderID);

    @GET
    Observable<IntelligentCheck> getIntelligentCheckInfo(@Header("Authorization") String header, @Url String url);

    @GET("api/physical-exam-order/orders")
    Observable<ExamOrder> getExamOrder(@Header("Authorization") String header, @Query("customer_ids") String id, @Query("mode") String mode, @Query("page") String page);

    @GET
    Observable<ExamOrderDetail> getExamOrderDetail(@Header("Authorization") String header, @Url String url);

    @POST
    Observable<ResponseBody> cancelReservation(@Header("Authorization") String header, @Url String url);//取消预约

    @FormUrlEncoded
    @PUT
    Observable<ResponseBody> modeFamiliesInfo(@Header("Authorization") String header, @Url String url, @FieldMap Map<String, String> info);

    @FormUrlEncoded
    @POST("api/physical-exam-order/AI/{orderid}/close-recommended")
    Observable<ResponseBody> closeRecommended(@Header("Authorization") String header, @Field("exam_status") String checking, @Path("orderid") String orderID);

    @FormUrlEncoded
    @POST("api/message/messages")
    Observable<ResponseBody> markMessageReaded(@Header("Authorization") String header, @Field("message_ids[]") List<String> message_ids);

    @FormUrlEncoded
    @POST("api/family/account-family-members/{id}/mobile/sms-validation-code")
    Observable<Message> getCodeModeFamiliesPhone(@Header("Authorization")String header,@Path("id")String id,@Field("mobile") String mobile);

    @FormUrlEncoded
    @POST("api/family/account-family-members/{id}/mobile")
    Observable<ResponseBody> modeFamiliesTel(@Header("Authorization")String header,@Path("id") String id,@FieldMap Map<String,String> map);
}
