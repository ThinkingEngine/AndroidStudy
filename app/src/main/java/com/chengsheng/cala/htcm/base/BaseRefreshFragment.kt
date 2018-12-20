package com.chengsheng.cala.htcm.base

import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chengsheng.cala.htcm.R

/**
 * Author: 任和
 * CreateDate: 2018/12/20 11:50 AM
 * Description: 对RecyclerView刷新的封装
 */
abstract class BaseRefreshFragment<T> : BaseFragment() {

    abstract fun getLayout(): Int

    abstract fun initViews(view: View?)

    abstract fun getData(page: Int)

    abstract fun getCurrentAdapter(): BaseQuickAdapter<T>?

    //page: if (currentPage * DEFAULT_LIMIT > 0) (currentPage * DEFAULT_LIMIT) else 0

    protected val FIRST_PAGE: Int = 0
    protected var currentPage: Int = FIRST_PAGE

    protected var swipeRefreshLayout: SwipeRefreshLayout? = null
    protected var recyclerView: RecyclerView? = null
    protected var adapter: BaseQuickAdapter<T>? = null

    override fun getLayoutId(): Int {
        return getLayout()
    }

    override fun initView(view: View?) {
        swipeRefreshLayout = view?.findViewById(R.id.swipeRefresh)
        recyclerView = view?.findViewById(R.id.recyclerView)

        initViews(view)
        initRecyclerView()
        initRefresh()
    }

    override fun getData() {
        getData(FIRST_PAGE)
    }

    private fun initRecyclerView() {
        recyclerView?.layoutManager = LinearLayoutManager(context)
        recyclerView?.adapter = initAdapter()

        recyclerView?.let {
            adapter?.onAttachedToRecyclerView(it)
        }
    }

    private fun initRefresh() {
        swipeRefreshLayout?.setOnRefreshListener {
            currentPage = FIRST_PAGE
            getData(FIRST_PAGE)
        }
    }

    private fun initAdapter(): BaseQuickAdapter<T>? {
        adapter = getCurrentAdapter()
        return adapter
    }

    /**
     * 填充列表数据
     * @param data  列表数据
     */
    protected fun fillData(data: List<T>?) {
        setData(data)
    }

    private fun setData(data: List<T>?) {
        swipeRefreshLayout?.isRefreshing = false
        if (data == null || data.isEmpty()) {
            if (currentPage == FIRST_PAGE) {
                adapter?.data?.clear()
                adapter?.notifyDataSetChanged()
            }
            return
        }
        if (currentPage == FIRST_PAGE) {
            adapter?.data?.clear()
            adapter?.setNewData(data)
        } else {
            adapter?.addData(data)
        }
    }
}