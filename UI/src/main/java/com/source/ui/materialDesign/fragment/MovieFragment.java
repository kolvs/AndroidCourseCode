package com.source.ui.materialDesign.fragment;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.source.ui.R;
import com.source.ui.materialDesign.adapter.NormalAdapter;
import com.source.ui.materialDesign.bean.Movie;
import com.source.ui.materialDesign.net.ApiService;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MovieFragment extends Fragment {
    RecyclerView rvFrList;
    SwipeRefreshLayout srlFrRefresh;
    private boolean firstShow = true;
    private NormalAdapter normalAdapter;

    private ArrayList<Movie.SubjectsBean> mMovieList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rv, null, false);
        rvFrList = view.findViewById(R.id.rv_fr_list);
        srlFrRefresh = view.findViewById(R.id.srl_fr_refresh);
        return view;
    }

    @Override
    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState) {
        initData();
    }

    private void initData() {
        srlFrRefresh.setRefreshing(true);
        rvFrList.setLayoutManager(new LinearLayoutManager(getActivity()));

        if (firstShow) {
            new ApiService().getTopMovie(new Observer<Movie>() {
                @Override
                public void onSubscribe(Disposable d) {

                }
                @Override
                public void onNext(Movie movie) {
                    Log.i("ToolTAG", "onNext: " + movie.getSubjects().size());
                    mMovieList.addAll(movie.getSubjects());
                    normalAdapter = new NormalAdapter(mMovieList, getActivity());
                    rvFrList.setAdapter(normalAdapter);
                }

                @Override
                public void onError(Throwable e) {
                    Log.i("ToolTAG", "onError: " + e.getMessage());
                    srlFrRefresh.setRefreshing(false);
                }

                @Override
                public void onComplete() {
                    srlFrRefresh.setRefreshing(false);
                    Log.i("ToolTAG", "onCompleted: ");
                }
            },0,10);
//
//            HttpMethods.getInstance().getTopMovie(new Observer<Movie>() {
//                @Override
//                public void onSubscribe(Disposable d) {
//
//                }
//
//                @Override
//                public void onNext(Movie movie) {
//                    Log.i("ToolTAG", "onNext: " + movie.getSubjects().size());
//                    mMovieList.addAll(movie.getSubjects());
//                    normalAdapter = new NormalAdapter(mMovieList, getActivity());
//                    rvFrList.setAdapter(normalAdapter);
//                }
//
//                @Override
//                public void onError(Throwable e) {
//                    Log.i("ToolTAG", "onError: " + e.getMessage());
//                    srlFrRefresh.setRefreshing(false);
//                }
//
//                @Override
//                public void onComplete() {
//                    srlFrRefresh.setRefreshing(false);
//                    Log.i("ToolTAG", "onCompleted: ");
//                }
//            }, 0, 20);
        }

    }

}
