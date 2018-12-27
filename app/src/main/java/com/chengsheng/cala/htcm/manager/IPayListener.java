package com.chengsheng.cala.htcm.manager;

/**
 * Author: 任和
 * CreateDate: 2018/12/27 8:54 AM
 * Description:
 */
public interface IPayListener {
    void onSuccess();

    void onCancel();

    void onFail();
}
