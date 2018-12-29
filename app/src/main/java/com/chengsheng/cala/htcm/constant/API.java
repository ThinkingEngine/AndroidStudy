package com.chengsheng.cala.htcm.constant;

import static com.chengsheng.cala.htcm.constant.BaseAPI.ACCOUNT_BASE_URL;
import static com.chengsheng.cala.htcm.constant.BaseAPI.SERVICE_BASE_URL;

/**
 * Author: 任和
 * CreateDate: 2018/12/18 1:41 PM
 * Description: 所有API接口地址
 */
public class API {

    /* 用户协议 */
    public static final String USER_AGREEMENT = SERVICE_BASE_URL + "api/soft-info/agreement";

    /* 获取用户信息 */
    public static final String USER_INFO_URL = ACCOUNT_BASE_URL + "user/account-infos";

    /* 发送和账号相关的短信验证码 */
    public static final String SEND_ACCOUNT_CAPTCHA = ACCOUNT_BASE_URL + "sms-verification-codes";

    /* 验证短信验证码 */
    public static final String VERIFICATION_ACCOUNT_CAPTCHA = ACCOUNT_BASE_URL + "sms-verification-codes";

    /* 修改账号手机号 */
    public static final String BIND_NEW_MOBILE = ACCOUNT_BASE_URL + "user/account-phone-numbers";

    /* 修改账号手机号 */
    public static final String RESET_LOGIN_PASSWORD = ACCOUNT_BASE_URL + "user/account-passwords";

    /* 修改用户信息 */
    public static final String UPDATE_USER_INFO = ACCOUNT_BASE_URL + "user/account-infos";

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
    public static final String MEMBER_CARD_LIST = SERVICE_BASE_URL + "api/health-card/health-cards?sort_fields=bind_at:desc";

    /* 会员卡详情 */
    public static final String MEMBER_CARD_DETAIL = SERVICE_BASE_URL + "api/health-card/health-cards/{id}";

    /* 解绑会员卡 */
    public static final String DELETE_MEMBER_CARD = SERVICE_BASE_URL + "api/health-card/health-cards/{id}";

    /* 绑定会员卡发送短信验证码 */
    public static final String SEND_CAPTCHA = SERVICE_BASE_URL + "api/health-card/health-cards/sms-validation-code";

    /* 绑定会员卡 */
    public static final String BIND_MEMBER_CARD = SERVICE_BASE_URL + "api/health-card/health-cards";

    /* 修改会员卡密码 */
    public static final String CHANGE_CARD_PASSWORD = SERVICE_BASE_URL + "api/health-card/health-cards/{id}/password-old";

    /* 找回卡密码-发送短信 */
    public static final String SEND_CARD_CAPTCHA = SERVICE_BASE_URL + "api/health-card/health-cards/{id}/password/sms-validation-code";

    /* 找回卡密码-验证短信 */
    public static final String CHECK_CARD_CAPTCHA = SERVICE_BASE_URL + "api/health-card/health-cards/{id}/password/sms-validation";

    /* 找回卡密码 */
    public static final String FIND_CARD_PASSWORD = SERVICE_BASE_URL + "api/health-card/health-cards/{id}/password-sms";

    /* 充值-支付宝签名 */
    public static final String CARD_DEPOSIT_ALIPAY_SIGN = SERVICE_BASE_URL + "api/health-card/deposit-orders/{id}/ali-sign";

    /* 充值-创建充值订单 */
    public static final String CREATE_CARD_DEPOSIT_ORDER = SERVICE_BASE_URL + "api/health-card/health-cards/{id}/deposit";

    /* 交易记录 */
    public static final String TRADE_RECORD = SERVICE_BASE_URL + "api/health-card/health-cards/{id}/trades?sort_fields=change_date:desc";

    /* 套餐列表启用的 */
    public static final String EXAM_PACKAGE = SERVICE_BASE_URL + "api/physical-exam-item/exam-packages/enable";

    /*  套餐详情 */
    public static final String EXAM_PACKAGES = SERVICE_BASE_URL + "api/physical-exam-item/exam-packages/{comboID}";

    /* 获取家人列表 */
    public static final String FAMILY_MEMBER = SERVICE_BASE_URL + "api/family/account-family-members";

    /* 获取订单列表 */
    public static final String EXAM_ORDER = SERVICE_BASE_URL + "api/physical-exam-order/orders";

    /* 解除家人绑定 */
    public static final String DEL_FAM = SERVICE_BASE_URL + "api/family/account-family-members/";

    /* 获取家人详细信息 */
    public static final String GET_FAM_INFO = SERVICE_BASE_URL + "api/family/account-family-members/";

    /* 设置默认就诊人 */
    public static final String SET_DEFULT = SERVICE_BASE_URL + "api/family/account-family-members/" + "{families_id}" + "/default-customer";

    /* 发送短信验证码-修改家人信息 */
    public static final String SEND_MOD_CODE = SERVICE_BASE_URL + "api/family/account-family-members/" + "{families_id}" + "/sms-validation-code";

    /* 验证短信验证码-修改家人信息 */
    public static final String VALI_MOD_CODE = SERVICE_BASE_URL + "api/family/account-family-members/" + "{families_id}" + "/sms-validation";

    /* 修改家庭成员信息 */
    public static final String PUT_FAM = SERVICE_BASE_URL + "api/family/account-family-members/{families_id}";

    /* 发送短信验证码-修改家人手机号 */
    public static final String VAIL_CODE = SERVICE_BASE_URL + "api/family/account-family-members/{families_id}/mobile/sms-validation-code";

    /* 验证短信验证码-修家人改手机号 */
    public static final String VAIL_CELL = SERVICE_BASE_URL + "api/family/account-family-members/{families_id}/mobile/sms-validation";

    /* 文件上传服务（头像上传） */
    public static final String HEADER_UP = SERVICE_BASE_URL + "api/file-storage/files";

    /* 添加家庭成员 */
    public static final String ADD_MEM = SERVICE_BASE_URL + "api/family/account-family-members";

    /* 体检订单 订单 订单详情 */
    public static final String ORDERS = SERVICE_BASE_URL + "api/physical-exam-order/orders/{order_id}";

    /* 智能助理 */
    public static final String AI = SERVICE_BASE_URL + "api/physical-exam-order/AI";

    /* 我的体检详情 */
    public static final String EXAM_DETAIL = SERVICE_BASE_URL + "api/physical-exam-order/customer-exams/{orderId}";

    /* 智能导检 */
    public static final String AI_CHECK = SERVICE_BASE_URL + "api/physical-exam-order/AI/{orderId}";

    /* 消息列表 */
    public static final String SMS_LIST = SERVICE_BASE_URL + "api/message/messages";

    /* 体检报告列表 */
    public static final String ISSUED_REPORT = SERVICE_BASE_URL + "api/physical-exam-order/issued-reports";

    /* 预约订单 */
    public static final String PUT_ORDER = SERVICE_BASE_URL + "api/physical-exam-order/orders";

    /* 报告详情 */
    public static final String EXAM_RESULT = SERVICE_BASE_URL + "api/physical-exam-order/exam-result/{orderID}";

}
