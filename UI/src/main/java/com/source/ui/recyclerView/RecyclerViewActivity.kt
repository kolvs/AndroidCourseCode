package com.source.ui.recyclerView

import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import com.source.ui.R;
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
        val layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
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
        val defaultItemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
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
            override fun onItemClick(viewHolder: androidx.recyclerview.widget.RecyclerView.ViewHolder?) {
                toast("onItemClick")
            }

            override fun onLongClick(viewHolder: androidx.recyclerview.widget.RecyclerView.ViewHolder?) {
                toast("onLongClick")
            }
        })

        // 添加分割线
        val divider = androidx.recyclerview.widget.DividerItemDecoration(
            this,
            androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
        )
        divider.setDrawable(ContextCompat.getDrawable(this, R.drawable.custom_divider)!!)
        recyclerView.addItemDecoration(divider)
        // 滑动监听
        recyclerView.addOnScrollListener(object : androidx.recyclerview.widget.RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: androidx.recyclerview.widget.RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                mSuspensionHeight = suspension_bar.height//获取悬浮条的高度
            }

            override fun onScrolled(recyclerView: androidx.recyclerview.widget.RecyclerView, dx: Int, dy: Int) {
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
        recyclerView.setViewCacheExtension(object : androidx.recyclerview.widget.RecyclerView.ViewCacheExtension(){
            override fun getViewForPositionAndType(p0: androidx.recyclerview.widget.RecyclerView.Recycler, p1: Int, p2: Int): View? {
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
