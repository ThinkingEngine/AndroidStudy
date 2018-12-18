package com.chengsheng.cala.htcm.utils;


import com.chengsheng.cala.htcm.protocol.ExamReportModel.CompareItem;
import com.chengsheng.cala.htcm.protocol.FamiliesListItem;

import java.util.List;
import java.util.Map;

public class CallBackDataAuth {

    private static AuthStateCallBack mAuthStateCallBack;

    private static ExamPersonInterface mExamPersonInterface;

    private static ExamDateInterface mExamDateInterface;

    private static UpdateAIAssisont mUpdateAIAssisont;

    private static UpdateStateInterface mUpdateStateInterface;

    private static CheckServiceInterface mCheckServiceInterface;

    private static UpdateConditionInterface mUpdateConditionInterface;

    private static ConditionInterface mConditionInterface;


    public static void setAuthStateCallBack(AuthStateCallBack authStateCallBack){
        mAuthStateCallBack = authStateCallBack;
    }

    public static void doAuthStateCallBack(boolean isAuth){
        mAuthStateCallBack.authResult(isAuth);
    }

    public static void setExamPersonInterface(ExamPersonInterface examPersonInterface){
        mExamPersonInterface = examPersonInterface;
    }

    public static void doExamPersonCallBack(List<FamiliesListItem> datas){
        mExamPersonInterface.examPersonData(datas);
    }

    public static void setExamDateInterface(ExamDateInterface examDateInterface){
        mExamDateInterface = examDateInterface;
    }

    public static void doExamDateInterface(int id){
        mExamDateInterface.getExamDate(id);
    }

    public static void setUpdateAIAssisont(UpdateAIAssisont updateAIAssisont){
        mUpdateAIAssisont = updateAIAssisont;
    }

    public static void doUpdateAIAssisont(boolean status){
        mUpdateAIAssisont.updateResult(status);
    }

    public static void setUpdateStateInterface(UpdateStateInterface updateStateInterface){
        mUpdateStateInterface = updateStateInterface;
    }

    public static void doUpdateStateInterface(boolean state){
        mUpdateStateInterface.updateServiceMessage(state);
    }

    public static void setCheckServiceInterface(CheckServiceInterface checkServiceInterface){
        mCheckServiceInterface = checkServiceInterface;
    }

    public static void doCheckServiceInterface(List<String> checks){
        mCheckServiceInterface.clickSMS(checks);
    }

    public static void setUpdateConditionInterface(UpdateConditionInterface updateConditionInterface){
        mUpdateConditionInterface = updateConditionInterface;
    }

    public static void doUpdateConditionInterface(List<Map<String,String>> datas,boolean update){
        mUpdateConditionInterface.selectCondition(datas,update);
    }

    public static void setConditionInterface(ConditionInterface conditionInterface){
        mConditionInterface = conditionInterface;
    }

    public static void doConditionInterface(List<CompareItem> condition){
        mConditionInterface.selectCondition(condition);
    }

}
