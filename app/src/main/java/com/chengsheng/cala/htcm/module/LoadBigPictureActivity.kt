package com.chengsheng.cala.htcm.module

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.chengsheng.cala.htcm.R
import com.chengsheng.cala.htcm.base.BaseActivity
import com.chengsheng.cala.htcm.widget.ShareDialog
import com.luck.picture.lib.tools.ScreenUtils
import kotlinx.android.synthetic.main.activity_load_big_picture.*

/**
 * Author: 任和
 * CreateDate: 2018/12/20 4:47 PM
 * Description: 加载大图公共页面
 */
class LoadBigPictureActivity : BaseActivity() {

    companion object {
        fun start(context: Context, title: String, imgUrl: String) {
            val bundle = Bundle()
            bundle.putString("title", title)
            bundle.putString("imgUrl", imgUrl)
            val intent = Intent(context, LoadBigPictureActivity::class.java)
            intent.putExtras(bundle)
            context.startActivity(intent)
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_load_big_picture
    }

    override fun initView() {
        val title: String = intent.getStringExtra("title")
        val imgUrl: String = intent.getStringExtra("imgUrl")

        titleBar?.setTitle(title)!!
                .setFinishClickListener {
                    finish()
                }.setRightClickListener {
                    ShareDialog()
                            .build(this)
                            .showDialog()
                            .setOnShareListener(object : ShareDialog.OnShareClickListener {
                                override fun shareToWeChat() {
                                    showShortToast("shareToWeChat")
                                }

                                override fun shareToMoment() {

                                }

                                override fun shareToQQ() {

                                }

                                override fun shareToQZone() {

                                }

                                override fun copyLink() {

                                }
                            })
                }


        Glide.with(this)
                .asBitmap()
                .load(imgUrl)
                .into(object : SimpleTarget<Bitmap>() {
                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                        //图片实际宽高
                        val height = resource.height
                        val width = resource.width
                        //显示宽高
                        val showWidth = ScreenUtils.getScreenWidth(this@LoadBigPictureActivity)
                        val ratio = showWidth.toFloat() / width.toFloat()
                        val showHeight = height * ratio
                        val layoutParams = ivBigPicture.layoutParams
                        layoutParams.width = showWidth
                        layoutParams.height = showHeight.toInt()
                        ivBigPicture.layoutParams = layoutParams
                        ivBigPicture.setImageBitmap(resource)
                    }
                })
    }

    override fun getData() {

    }
}