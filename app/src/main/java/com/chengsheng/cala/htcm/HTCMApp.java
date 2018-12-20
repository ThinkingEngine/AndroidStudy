package com.chengsheng.cala.htcm;

import android.content.Context;
import android.support.annotation.NonNull;

import com.chengsheng.cala.htcm.protocol.AppointmentDetail;
import com.chengsheng.cala.htcm.protocol.ExamReportItem;
import com.chengsheng.cala.htcm.protocol.FamiliesDetailInfo;
import com.chengsheng.cala.htcm.utils.FuncUtils;
import com.chengsheng.cala.htcm.utils.UserUtil;

import java.util.ArrayList;
import java.util.List;

public class HTCMApp {

    private List<ExamReportItem> exams = new ArrayList<>();

    private FamiliesDetailInfo familiesDetailInfo;
    private AppointmentDetail appointmentDetail;

    public static int EXACT_SCREEN_WIDTH;
    public static int EXACT_SCREEN_HEIGHT;

    private static volatile HTCMApp app;

    private HTCMApp(@NonNull Context context) {
        if (app != null) {
            throw new RuntimeException("HTCMapp 实例化失败!");
        }
    }

    public static HTCMApp create(@NonNull Context context) {
        if (null == app) {
            synchronized (HTCMApp.class) {
                if (null == app) {
                    app = new HTCMApp(context);
                }
            }
        }
        return app;
    }

    public String getTokenType() {
        return UserUtil.getTokenType();
    }

    public String getAccessToken() {
        return UserUtil.getAccessToken();
    }

    public void addExamsID(ExamReportItem exam) {
        exams.add(exam);
//        exams.add(exam);
    }

    public void delExamsID(ExamReportItem exam) {
        exams.remove(exam);
    }

    public void clearExams() {
        exams.clear();
    }

    public List<ExamReportItem> getExams() {
        return exams;
    }

    public void setOrderID(int orderid) {
        FuncUtils.putString("ORDER_ID", String.valueOf(orderid));
    }

    public int getOrderID() {
        return Integer.valueOf(FuncUtils.getString("ORDER_ID", "0"));
    }

    public void setFamiliesDetailInfo(FamiliesDetailInfo familiesDetailInfo) {
        this.familiesDetailInfo = familiesDetailInfo;
    }

    public FamiliesDetailInfo getFamiliesDetailInfo() {
        return familiesDetailInfo;
    }

    public void setAppointmentDetail(AppointmentDetail appointmentDetail) {
        this.appointmentDetail = appointmentDetail;
    }

    public AppointmentDetail getAppointmentDetail() {
        return appointmentDetail;
    }

    public void clearAppointmentDetail() {
        this.appointmentDetail = null;
    }

}
