package com.chengsheng.cala.htcm.base

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chengsheng.cala.htcm.R
import com.chengsheng.cala.htcm.network.NetWorkUtils
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener
import io.reactivex.annotations.Nullable

/**
 * Author: 任和
 * CreateDate: 2018/12/20 11:50 AM
 * Description: 对RecyclerView刷新的封装
 */
abstract class BaseRefreshActivity<T> : BaseActivity(), OnLoadmoreListener {

    //page: if (currentPage * DEFAULT_LIMIT > 0) (currentPage * DEFAULT_LIMIT) else 0

    private val AUTO_REFRESH_DELAY_TIME: Int = 180
    protected val DEFAULT_LIMIT: Int = 10
    protected val FIRST_PAGE: Int = 0
    protected var currentPage: Int = FIRST_PAGE

    var smartRefreshLayout: SmartRefreshLayout? = null
    var recyclerView: RecyclerView? = null
    private var classicsHeader: ClassicsHeader? = null

    protected var adapter: BaseQuickAdapter<T>? = null
    protected var layoutManager: RecyclerView.LayoutManager? = LinearLayoutManager(this)

    abstract fun getData(page: Int)
    abstract fun getCurrentAdapter(): BaseQuickAdapter<T>?

    override fun initView() {
        smartRefreshLayout = findViewById(R.id.smartRefreshLayout)
        recyclerView = findViewById(R.id.recyclerView)
        classicsHeader = findViewById(R.id.classicsHeader)

        initRecyclerView()
        initRefresh()
    }

    override fun getData() {
        getData(FIRST_PAGE)
    }

    private fun initRecyclerView() {
        recyclerView?.layoutManager = layoutManager
        recyclerView?.adapter = initAdapter()

        recyclerView?.let { adapter?.onAttachedToRecyclerView(it) }
    }

    private fun initAdapter(): BaseQuickAdapter<T>? {
        adapter = getCurrentAdapter()
        smartRefreshLayout?.setOnLoadmoreListener(this)
        return adapter
    }

    private fun initRefresh() {
        classicsHeader?.setEnableLastTime(false)

        if (NetWorkUtils.isNetConnected(this)) {
            smartRefreshLayout?.autoRefresh(AUTO_REFRESH_DELAY_TIME)
        } else {
            //网络未连接
            adapter?.emptyView = getEmptyView(R.mipmap.empty_no_net)
        }

        smartRefreshLayout?.setOnRefreshListener {
            smartRefreshLayout?.isLoadmoreFinished = false
            currentPage = FIRST_PAGE
            getData(FIRST_PAGE)
        }
    }

    override fun onLoadmore(refreshlayout: RefreshLayout?) {
        currentPage++
        getData(currentPage)
    }

    /**
     * 填充列表数据(空状态页面背景自定义颜色)
     * @param data  列表数据
     */
    protected fun fillData(@Nullable data: List<T>?) {
        setData(data, 0)
    }

    /**
     * 填充列表数据(空状态页面背景使用默认颜色)
     * @param data  列表数据
     * @param
     */
    protected fun fillData(@Nullable data: List<T>?, emptyImage: Int) {
        setData(data, emptyImage)
    }

    private fun setData(@Nullable data: List<T>?, emptyImage: Int) {
        smartRefreshLayout?.finishRefresh()
        smartRefreshLayout?.finishLoadmore()

        if (data == null || data.isEmpty()) {
            if (currentPage == FIRST_PAGE) {
                adapter?.data?.clear()
                adapter?.notifyDataSetChanged()
                if (!NetWorkUtils.isNetConnected(this)) {
                    adapter?.emptyView = getEmptyView(R.mipmap.empty_no_net)
                } else {
                    adapter?.emptyView = getEmptyView(emptyImage)
                }
            } else {
                smartRefreshLayout?.isLoadmoreFinished = true
            }

            return
        }

        if (currentPage == FIRST_PAGE) {
            adapter?.setNewData(data)
        } else {
            adapter?.addData(data)
        }

        if (data.size < DEFAULT_LIMIT) {
            smartRefreshLayout?.isLoadmoreFinished = true
        }
    }

    /**
     * 加载空页面
     * @param emptyImage 空状态页面图片资源
     */
    private fun getEmptyView(emptyImage: Int): View {
        val emptyView = LayoutInflater.from(this).inflate(R.layout.layout_list_empty, recyclerView, false)
        val ivEmptyDrawable = emptyView.findViewById<ImageView>(R.id.ivEmptyDrawable)
        ivEmptyDrawable.setImageResource(if (emptyImage == 0) R.mipmap.empty_normal else emptyImage)
        return emptyView
    }
}