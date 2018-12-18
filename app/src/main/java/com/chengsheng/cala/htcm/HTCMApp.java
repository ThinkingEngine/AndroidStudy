package com.chengsheng.cala.htcm;

import android.content.Context;
import android.support.annotation.NonNull;

import com.chengsheng.cala.htcm.constant.GlobalConstant;
import com.chengsheng.cala.htcm.protocol.AppointmentDetail;
import com.chengsheng.cala.htcm.protocol.ExamReportItem;
import com.chengsheng.cala.htcm.protocol.FamiliesDetailInfo;
import com.chengsheng.cala.htcm.utils.FuncUtils;

import java.util.ArrayList;
import java.util.List;

public class HTCMApp {

    private int userRegister = GlobalConstant.USER_STATE_UNREGISTER;
    private String userName = "";
    private String accessToken;
    private String expiresIn;
    private String tokenType;
    private List<ExamReportItem> exams = new ArrayList<>();

    private FamiliesDetailInfo familiesDetailInfo;
    private AppointmentDetail appointmentDetail;

    private int orderID;

    public static int EXACT_SCREEN_WIDTH;
    public static int EXACT_SCREEN_HEIGHT;


    private static final String clientId = "12aef523-379e-4f0b-894d-1834dc0a80c8";
    private static final String grantType = "password";
    private static final String clientSecret = "x9w2f1m4iUXD21GYU5SSzrLgj55VuFOFpJQC+SLcNgUA3RUKhBNdzlRtQFx4+rLb";
    private static final String scope = "accountInfo";

    private static volatile HTCMApp app;

    private HTCMApp(@NonNull Context context){
        if(app != null){
            throw new RuntimeException("HTCMapp 实例化失败!");
        }

        //app 初始化，先检查是否曾经有过登录动作。
        if(FuncUtils.getBoolean("REGISTER",false)){
           userRegister = GlobalConstant.USER_STATE_REGISTER;
        }
    }

    public static HTCMApp create(@NonNull Context context){
        if(null == app){
            synchronized (HTCMApp.class){
                if(null == app){
                    app = new HTCMApp(context);
                }
            }
        }
        return app;
    }


    public int getRegisterState(){
        return userRegister;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserRegister(int userRegister) {
        this.userRegister = userRegister;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public static String getClientId() {
        return clientId;
    }

    public static String getGrantType() {
        return grantType;
    }

    public static String getClientSecret() {
        return clientSecret;
    }

    public static String getScope() {
        return scope;
    }

    public String getAccessToken() {

        return FuncUtils.getString("ACCESSTOKEN","");
    }

    public void setAccessToken(String accessToken) {
        FuncUtils.putString("ACCESSTOKEN",accessToken);
    }

    public String getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(String expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getTokenType() {

        return FuncUtils.getString("TOKENTYPE","");
    }

    public void setTokenType(String tokenType) {
        FuncUtils.putString("TOKENTYPE",tokenType);

    }

    public void addExamsID(ExamReportItem exam){
        exams.add(exam);
    }

    public void delExamsID(ExamReportItem examID){
        exams.remove(examID);
    }

    public void clearExams(){
        exams.clear();
    }

    public List<ExamReportItem> getExams(){
        return exams;
    }

    public void setOrderID(int orderid){
        FuncUtils.putString("ORDER_ID",String.valueOf(orderid));
    }

    public int getOrderID(){
        return  Integer.valueOf(FuncUtils.getString("ORDER_ID","0"));
    }

    public void setFamiliesDetailInfo(FamiliesDetailInfo familiesDetailInfo){
        this.familiesDetailInfo = familiesDetailInfo;
    }

    public FamiliesDetailInfo getFamiliesDetailInfo(){
        return familiesDetailInfo;
    }

    public void setAppointmentDetail(AppointmentDetail appointmentDetail){
        this.appointmentDetail = appointmentDetail;
    }

    public AppointmentDetail getAppointmentDetail(){
        return appointmentDetail;
    }

    public void clearAppointmentDetail(){
        this.appointmentDetail = null;
    }

}
