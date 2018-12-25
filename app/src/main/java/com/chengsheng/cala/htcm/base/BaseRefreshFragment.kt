package com.chengsheng.cala.htcm.base

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
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
abstract class BaseRefreshFragment<T> : BaseFragment(), OnLoadmoreListener {

    //page: if (currentPage * DEFAULT_LIMIT > 0) (currentPage * DEFAULT_LIMIT) else 0

    private val AUTO_REFRESH_DELAY_TIME: Int = 180
    protected val DEFAULT_LIMIT: Int = 15
    protected val FIRST_PAGE: Int = 1
    protected var currentPage: Int = FIRST_PAGE

    protected var smartRefreshLayout: SmartRefreshLayout? = null
    protected var recyclerView: RecyclerView? = null
    private var classicsHeader: ClassicsHeader? = null

    protected var adapter: BaseQuickAdapter<T>? = null

    abstract fun initViews(view: View)
    abstract fun getData(page: Int)
    abstract fun getCurrentAdapter(): BaseQuickAdapter<T>?

    override fun initView(view: View) {
        smartRefreshLayout = view.findViewById(R.id.smartRefreshLayout)
        recyclerView = view.findViewById(R.id.recyclerView)
        classicsHeader = view.findViewById(R.id.classicsHeader)

        initViews(view)
        initRecyclerView()
        initRefresh()
    }

    override fun getData() {

    }

    private fun initRecyclerView() {
        recyclerView?.layoutManager = LinearLayoutManager(context)
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

        if (NetWorkUtils.isNetConnected(context)) {
            smartRefreshLayout?.autoRefresh(AUTO_REFRESH_DELAY_TIME)
        } else {
            //网络未连接
            adapter?.emptyView = getEmptyView(R.mipmap.empty_no_net, "网络未连接")
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
     * 填充列表数据
     * @param data  列表数据
     */
    protected fun fillData(@Nullable data: List<T>?) {
        setData(data, 0, "")
    }

    /**
     * 填充列表数据
     * @param data  列表数据
     * @param emptyImage 空状态图片id
     */
    protected fun fillData(@Nullable data: List<T>?, emptyImage: Int) {
        setData(data, emptyImage, "")
    }

    /**
     * 填充列表数据
     * @param data  列表数据
     * @param emptyImage 空状态图片id
     * @param emptyStr 空状态提示文字
     */
    protected fun fillData(@Nullable data: List<T>?, emptyImage: Int, emptyStr: String = "") {
        setData(data, emptyImage, emptyStr)
    }

    private fun setData(@Nullable data: List<T>?, emptyImage: Int, emptyStr: String = "") {
        smartRefreshLayout?.finishRefresh()
        smartRefreshLayout?.finishLoadmore()

        if (data == null || data.isEmpty()) {
            if (currentPage == FIRST_PAGE) {
                adapter?.data?.clear()
                adapter?.notifyDataSetChanged()
                if (!NetWorkUtils.isNetConnected(context)) {
                    adapter?.emptyView = getEmptyView(R.mipmap.empty_no_net, "网络未连接")
                } else {
                    adapter?.emptyView = getEmptyView(emptyImage, emptyStr)
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
     * @param emptyImage 空状态图片资源
     * @param emptyStr 空状态提示文字
     */
    private fun getEmptyView(emptyImage: Int, emptyStr: String): View {
        val emptyView = LayoutInflater.from(context).inflate(R.layout.layout_list_empty, recyclerView, false)
        val ivEmptyDrawable = emptyView.findViewById<ImageView>(R.id.ivEmptyDrawable)
        val tvEmptyStr = emptyView.findViewById<TextView>(R.id.tvEmptyStr)
        ivEmptyDrawable.setImageResource(if (emptyImage == 0) R.mipmap.empty_normal else emptyImage)
        tvEmptyStr.text = if (emptyStr.isEmpty()) "没有数据" else emptyStr
        return emptyView
    }
}