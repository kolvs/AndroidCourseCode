package com.assess15.ui.materialDesign;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;
import com.assess15.ui.R;
import com.assess15.ui.materialDesign.bean.MovieDetail;
import com.assess15.ui.materialDesign.net.ApiService;
import com.assess15.ui.materialDesign.net.GlideImageLoader;
import com.assess15.ui.materialDesign.utils.AppBarStateChangeEvent;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 电影详情Activity
 */
public class MovieDetailActivity extends AppCompatActivity {

    private TextView tv;
    private ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        String url = getIntent().getStringExtra("URL");
        final String name = getIntent().getStringExtra("NAME");
        String id = getIntent().getStringExtra("ID");

        final Toolbar toolbar = findViewById(R.id.tb_amd_toolbar);
        AppBarLayout appBarLayout = findViewById(R.id.appBar);
        //使用CollapsingToolbarLayout必须把title设置到CollapsingToolbarLayout上，设置到Toolbar上则不会显示
        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsingToolbarLayout);
        iv = findViewById(R.id.iv_movie_icon);
//        iv.setImageResource(R.mipmap.time);
        tv = findViewById(R.id.tv_content);

        collapsingToolbarLayout.setTitle(name);
        //通过CollapsingToolbarLayout修改字体颜色
        collapsingToolbarLayout.setExpandedTitleColor(Color.RED);//设置还没收缩时状态下字体颜色
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);//设置收缩后Toolbar上字体的颜色

        appBarLayout.addOnOffsetChangedListener(new AppBarStateChangeEvent() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, AppBarStateChangeEvent.State state, int verticalOffset) {
                if (toolbar == null) return;
                if (state == AppBarStateChangeEvent.State.COLLAPSED) {
                    toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                } else {
                    toolbar.setBackgroundColor(Color.TRANSPARENT);
                }
            }
        });

        initData(id);
    }

    private void initData(String id) {
        new ApiService().getTopMovieDetails(new Observer<MovieDetail>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(MovieDetail movieDetail) {
                tv.setText(movieDetail.getSummary());
                new GlideImageLoader().displayImage(MovieDetailActivity.this, movieDetail.getImages().getLarge(), iv);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        }, id);
    }
}
