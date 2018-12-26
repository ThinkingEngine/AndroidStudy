package com.chengsheng.cala.htcm.constant;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Author: 任和
 * CreateDate: 2018/12/26 2:10 PM
 * Description: 支付方式
 */
@IntDef({
        PayType.ALIPAY,
        PayType.WECHAT
})
@Retention(RetentionPolicy.SOURCE)
public @interface PayType {
    int ALIPAY = 0;
    int WECHAT = 1;
}
