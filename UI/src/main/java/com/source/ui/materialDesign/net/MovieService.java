package com.source.ui.materialDesign.net;

import com.source.ui.materialDesign.bean.Movie;
import com.source.ui.materialDesign.bean.MovieDetail;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieService {
    @GET("top250")
    Observable<Movie> getTopMovie(@Query("start") int start, @Query("count") int count);

    @GET("subject/{id}")
    Observable<MovieDetail> getMovieDetail(@Path("id") String id);
}
