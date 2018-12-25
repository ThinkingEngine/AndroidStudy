package com.chengsheng.cala.htcm.constant;

import static com.chengsheng.cala.htcm.constant.BaseAPI.ACCOUNT_BASE_URL;
import static com.chengsheng.cala.htcm.constant.BaseAPI.SERVICE_BASE_URL;

/**
 * Author: 任和
 * CreateDate: 2018/12/18 1:41 PM
 * Description: 所有API接口地址
 */
public class API {

    /* 用户信息 */
    public static final String USER_INFO_URL = ACCOUNT_BASE_URL + "user/account-infos";

    /* 获取客户体检订单详情 */
    public static final String USER_EXAM_DETAIL = SERVICE_BASE_URL + "api/physical-exam-order/customer-exams/";

    /* 自助登记 */
    public static final String USER_REGISTER = SERVICE_BASE_URL + "api/physical-exam-order/orders/registration/";

    /* 推荐服务 */
    public static final String RECOMMEND_SERVICE = SERVICE_BASE_URL + "api/support-service/categories/items/recommended";

    /*我的体检列表*/
    public static final String MY_EXAM_LIST = SERVICE_BASE_URL + "api/physical-exam-order/customer-exams";

    /* 科室列表 */
    public static final String ORGANIZATION = SERVICE_BASE_URL + "api/organization/depts/enabled";

    /* 科室详情 */
    public static final String ORGANIZATION_DETAIL = SERVICE_BASE_URL + "api/organization/depts";

    /* 医生列表 */
    public static final String DOCTORS = SERVICE_BASE_URL + "api/organization/doctors/enabled";

    /* 会员卡列表 */
    public static final String MEMBER_CARD_LIST = SERVICE_BASE_URL + "api/health-card/health-cards";

    /* 会员卡详情 */
    public static final String MEMBER_CARD_DETAIL = SERVICE_BASE_URL + "api/health-card/health-cards/3";

    /* 绑定会员卡 */
    public static final String BIND_MEMBER_CARD = SERVICE_BASE_URL + "api/health-card/health-cards";

}
