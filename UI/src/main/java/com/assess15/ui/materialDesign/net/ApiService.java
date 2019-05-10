package com.assess15.ui.materialDesign.net;

import com.assess15.ui.materialDesign.bean.Movie;
import com.assess15.ui.materialDesign.bean.MovieDetail;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ApiService {

    public void getTopMovie(Observer<Movie> observer, int start, int count) {
        HttpMethods.getInstance().getService()
                .getTopMovie(start, count)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                //观察者的运行的线程
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void getTopMovieDetails(Observer<MovieDetail> observer, String id) {
        HttpMethods.getInstance().getService().getMovieDetail(id).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
