package com.chengsheng.cala.htcm.module.home

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.view.KeyEvent
import com.chengsheng.cala.htcm.R
import com.chengsheng.cala.htcm.base.BaseActivity
import com.chengsheng.cala.htcm.constant.BaseAPI
import com.chengsheng.cala.htcm.module.find.FindFragment
import com.chengsheng.cala.htcm.module.fragments.HomeFragment
import com.chengsheng.cala.htcm.module.user.MineFragment
import kotlinx.android.synthetic.main.activity_new_main.*

/**
 * Author: 任和
 * CreateDate: 2018/12/19 8:23 AM
 * Description:
 */
class MainActivity : BaseActivity() {

    private var homeFragment: Fragment? = null
    //    private var healthFragment: Fragment? = null
    private var findFragment: Fragment? = null
    private var mineFragment: Fragment? = null
    private var currentFragment: Fragment? = null

    private var beginTransaction: FragmentTransaction? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_new_main
    }

    override fun initView() {
        homeFragment = HomeFragment.newInstance()
//        healthFragment = HealthFragment.newInstance()
        findFragment = FindFragment.newInstance()
        mineFragment = MineFragment.newInstance("", "")

        //默认选择首页
        rbHome.isChecked = true
        replaceFragment(homeFragment!!)

        radioGroupMain?.setOnCheckedChangeListener { _, id ->
            when (id) {
                R.id.rbHome -> {
                    replaceFragment(homeFragment!!)
                }
//                R.id.rbHealth -> {
//                    replaceFragment(healthFragment!!)
//                }
                R.id.rbFind -> {
                    replaceFragment(findFragment!!)
                }
                R.id.rbMine -> {
                    replaceFragment(mineFragment!!)
                }
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        beginTransaction = supportFragmentManager.beginTransaction()

        if (currentFragment == null) {
            beginTransaction?.add(R.id.layout_main_content, fragment)?.commitAllowingStateLoss()
        } else if (currentFragment != fragment) {
            beginTransaction?.hide(currentFragment!!)
            if (fragment.isAdded) {
                beginTransaction?.show(fragment)?.commitAllowingStateLoss()
            } else {
                beginTransaction?.add(R.id.layout_main_content, fragment)?.commitAllowingStateLoss()
            }
        }
        currentFragment = fragment
    }

    override fun getData() {

    }

    private var exitTime = 0L

    /**
     * 连按两次后退键退出程序
     */
    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_DOWN) {
            if (System.currentTimeMillis() - exitTime > 2000) {
                showShortToast("再点一次退出程序")
                exitTime = System.currentTimeMillis()
            } else {
                finish()
                return super.onKeyDown(keyCode, event)
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}