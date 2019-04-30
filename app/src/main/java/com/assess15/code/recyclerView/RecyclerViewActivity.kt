package com.assess15.code.recyclerView

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.assess15.code.R
import kotlinx.android.synthetic.main.activity_recycler_view.*

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
        recyclerView.adapter = feedAdapter
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)

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
        updateBar()
    }

    private fun updateBar() {
//        Picasso.with(this)
//            .load(getAvatarResId(mCurrentPosition))
//            .centerInside()
//            .fit()
//            .into(mSuspensionIv)
        tv_nickname.text = "NetEase $mCurrentPosition"
    }

    private fun getAvatarResId(position: Int): Int {
        when (position % 4) {
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
        }
        return 0
    }

}
