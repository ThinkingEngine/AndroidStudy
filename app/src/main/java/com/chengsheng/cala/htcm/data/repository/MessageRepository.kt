package com.chengsheng.cala.htcm.data.repository

import com.chengsheng.cala.htcm.data.retrofit.RetrofitHelper
import com.chengsheng.cala.htcm.data.transformProto
import com.chengsheng.cala.htcm.protocol.childmodela.MessageList
import io.reactivex.Observable


/**
 * Author: 蔡浪
 * CreateDate: 2018-12-28 15:01
 * Description:
 */
class MessageRepository {
    companion object {
        @Volatile
        private var instance: MessageRepository? = null

        val default: MessageRepository?
            get() {
                if (instance == null) {
                    synchronized(MessageRepository::class.java) {
                        if (instance == null) {
                            instance = MessageRepository()
                        }
                    }
                }
                return instance
            }
    }

    fun getMessageList(page: String): Observable<MessageList>? {
        return transformProto(RetrofitHelper.getInstance().messageService.getSMSList(page))
    }
}