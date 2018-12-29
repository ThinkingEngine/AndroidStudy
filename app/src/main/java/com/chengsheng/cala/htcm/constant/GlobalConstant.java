package com.chengsheng.cala.htcm.constant;

/**
 * Author: 蔡浪
 * CreateDate: 2018/12/18 1:41 PM
 * Description: 全局静态常量
 */
public class GlobalConstant {

    public static final String clientId = "12aef523-379e-4f0b-894d-1834dc0a80c8";
    public static final String grantType = "password";
    public static final String clientSecret = "x9w2f1m4iUXD21GYU5SSzrLgj55VuFOFpJQC+SLcNgUA3RUKhBNdzlRtQFx4+rLb";
    public static final String scope = "accountInfo";

    public static final int NETWORK_NONE = -1;
    public static final int NETWORK_MOBILE = 0;
    public static final int NETWORK_WIFI = 1;

    public static final String ACCESS_TOKEN = "accessToken";
    public static final String TOKEN_TYPE = "tokenType";
    public static final String MOBILE = "mobile";
    public static final String PASSWORD = "passWord";

    /**
     * 更新会员卡信息
     */
    public static final String UPDATE_MEMBER_CARD_SUCCESS = "updateMemberCardSuc";
    /**
     * 更新智能助理信息
     */
    public static final String UPDATE_AI_ASSISTANT_DATA = "updateAIAssistant";

    /**
     * 刷新主页新闻的浏览量
     */
    public static final String UPDATE_NEWS_BOWSE = "updateNewsBowse";
    /**
     * 通知 确认预约 用户选择的体检人的id
     */
    public static final String BOARD_EXAM_ID = "boardExamID";

    /**
     * 昵称修改成功
     */
    public static final String UPDATE_NICKNAME_SUCCESS = "updateNickName";
    /**
     * 昵称修改成功
     */
    public static final String BIND_NEW_MOBILE_SUCCESS = "bindNewMobileSuc";

    /**
     * 家庭成员变更通知
     */
    public static final String CHANGE_MEM = "changeMem";


    public static final String TEST_URL = "http://account.zz-tech.com.cn:85/";
    public static final String API_URL = "http://api.peis-mobile.zz-tech.com.cn:85/api/physical-exam-item/";
//    public static final String ACCOUNT_BASE_URL = "http://api.peis-mobile.zz-tech.com.cn:85/";//线上地址

    public static final String API_BASE_URL = "http://192.168.1.119:9001/";

    public static final String USER_EXAM_DETAIL = "api/physical-exam-order/customer-exams/";
    public static final String INTELLIGENT_CHECK = "api/physical-exam-order/AI/";
    public static final String ARTICLE_COLLECT = "api/news/collector/article/";
    public static final String EXAM_ORDER = "api/physical-exam-order/orders/";
    public static final String CANCEL_RESERVATION = "api/physical-exam-order/orders/cancel-Reservation/";
    public static final String MODE_FAMILIES = "api/family/account-family-members/";
    public static final String FAMILIES_INFO = "api/family/account-family-members/";
    public static final String EXAM_PACKAGES = "api/physical-exam-item/exam-packages/";
    public static final String COLLECT_EXAM_PACKAGES = "api/physical-exam-item/collector/exam-packages/";
    public static final String ALI_SIGN = "api/physical-exam-order/order-payments/ali-sign/";
    public static final String EXAM_RESULT = "api/physical-exam-order/exam-result/";

    public static final String TEST = "http://192.168.1.140:8000/";

    public static final String COMBO_FILTER = "name:like=";
    public static final String COMBO_TYPE_A = "display_order:asc";//综合
    public static final String COMBO_TYPE_B = "price:asc";//升序
    public static final String COMBO_TYPE_C = "price:desc";//降序
    public static final String COMBO_TYPE_D = "current_sales_num:desc";//销量

    public static final String EXAM_REPORT_ID = "e_r_id";
    public static final String EXAM_REPORT_NAME = "e_r_name";

    public static final String PAY_MARK = "pay_result";
    public static final int PAY_COMPLETE = 1;
    public static final int PAY_FAILURE = 0;
    public static final int PAY_UNKNOWN = -1;


    //体检状态信息
    public static final String RESERVATION = "reservation";//预约(二维码)
    public static final String CHECKING = "checking";//体检中(条形码)
    public static final String CHECKED = "checked";//完检(条形码)
    public static final String CANCEL = "cancel";//已取消(条形码)

    //我的订单 模式
    public static final String MODE_ALL = "all";
    public static final String MODE_RECEIVABLE = "receivable";
    public static final String MODE_RECEIVED = "received";
    public static final String MODE_COMMENT = "comment";
    public static final String MODE_CANCEL = "cancel";


    public static final int MAX_SELECT_PIC_NUM = 8;
    public static final String IMG_LIST = "img_list"; //第几张图片
    public static final String POSITION = "position"; //第几张图片
    public static final String PIC_PATH = "pic_path"; //图片路径
    public static final int REQUEST_CODE_MAIN = 10; //请求码
    public static final int RESULT_CODE_VIEW_IMG = 11; //查看大图页面的结果码

    //一般常量
    /**
     * 多个页面的recyclerView有共同的布局，但现实的组件不一样，用数字对其进行区分。
     */
    public static final int COMBO_DETAIL_MARK = 1;
    public static final int EXAM_DETAIL_MARK = 2;
    public static final int REGISTER_DETIAL = 3;


    public static final int EXACT_SCREEN_HEIGHT = 0;
    public static final int EXACT_SCREEN_WIDTH = 0;
}
