package com.assess15.code.recyclerView

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.assess15.code.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_recycler_view.*
import org.jetbrains.anko.toast

/**
 * 带有吸顶的RecyclerView
 * 吸顶来自自己定义的View，不是从Adapter中获取的
 */
class RecyclerViewActivity : AppCompatActivity() {

    private var mSuspensionHeight: Int = 0
    private var mCurrentPosition: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)

        initView()
    }

    private fun initView() {
        val layoutManager = LinearLayoutManager(this)
        val feedAdapter = FeedAdapter()
        // 设置适配器
        recyclerView.adapter = feedAdapter
        // 设置布局管理器
        recyclerView.layoutManager = layoutManager
        //
        recyclerView.setHasFixedSize(true)//？
        // 设置默认动画，如果不设置，也是使用这个默认动画
//        recyclerView.itemAnimator = DefaultItemAnimator()
        // 自定义动画
        val defaultItemAnimator = DefaultItemAnimator()
        defaultItemAnimator.addDuration = 1000
        defaultItemAnimator.removeDuration = 1000
        recyclerView.itemAnimator = defaultItemAnimator

        // 在Adapter中ViewHolder.itemView.setOnClickListener()/ViewHolder.itemView.setOnLongClickListener()实现
        feedAdapter.setOnItemClickListener(object : FeedAdapter.OnItemClickListener {
            override fun onItemClick(view: View?, position: Int) {
                toast("onItemClick$position")
            }

        })
        feedAdapter.setOnItemLongClickListener(object : FeedAdapter.OnItemLongClickListener {
            override fun onItemLongClick(view: View?, position: Int): Boolean {
                toast("onItemLongClick$position")
                return false
            }

        })

        // 基于RecyclerView.OnItemTouchListener点击事件
        recyclerView.addOnItemTouchListener(object : RecyclerItemClickListener(this) {
            override fun onItemClick(view: View?, position: Int) {
                toast("点击了....$position")
            }
        })

        // 基于RecyclerView.OnItemTouchListener点击事件
        recyclerView.addOnItemTouchListener(object : OnRecyclerItemClickListener(recyclerView) {
            override fun onItemClick(viewHolder: RecyclerView.ViewHolder?) {
                toast("onItemClick")
            }

            override fun onLongClick(viewHolder: RecyclerView.ViewHolder?) {
                toast("onLongClick")
            }
        })

        // 添加分割线
        val divider = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        divider.setDrawable(ContextCompat.getDrawable(this, R.drawable.custom_divider)!!)
        recyclerView.addItemDecoration(divider)
        // 滑动监听
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                mSuspensionHeight = suspension_bar.height//获取悬浮条的高度
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                // 对悬浮条的位置进行调整
                // 找到下一个itemView
                val view = layoutManager.findViewByPosition(mCurrentPosition + 1)
                if (view != null) {
                    if (view.top <= mSuspensionHeight) {
                        // 需要对悬浮条进行移动
                        suspension_bar.y = ((-(mSuspensionHeight - view.top)).toFloat())
                    } else {
                        // 保持在原来的位置
                        suspension_bar.y = 0.toFloat()
                    }
                }
                if (mCurrentPosition != layoutManager.findFirstVisibleItemPosition()) {
                    mCurrentPosition = layoutManager.findFirstVisibleItemPosition()
                    updateBar()
                }
            }
        })

        // 自定义View缓存
        recyclerView.setViewCacheExtension(object :RecyclerView.ViewCacheExtension(){
            override fun getViewForPositionAndType(p0: RecyclerView.Recycler, p1: Int, p2: Int): View? {
                return null
            }
        })

        updateBar()
    }

    private fun updateBar() {
        Picasso.with(this)
            .load(getAvatarResId(mCurrentPosition))
            .centerInside()
            .fit()
            .into(iv_avatar)
        tv_nickname.text = "NetEase $mCurrentPosition"
    }

    private fun getAvatarResId(position: Int): Int {
        return when (position % 4) { // 注意写法，直接 return 0， kt中会crash，Java中不会
            0 -> {
                R.drawable.avatar1
            }
            1 -> {
                R.drawable.avatar2
            }
            2 -> {
                R.drawable.avatar3
            }
            3 -> {
                R.drawable.avatar4
            }
            else -> 0
        }
    }

}
