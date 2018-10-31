package com.michaelagustian.boxoffice.data.source.remote

import com.michaelagustian.boxoffice.BuildConfig
import com.michaelagustian.boxoffice.data.model.Movie
import com.michaelagustian.boxoffice.data.model.ResultList
import io.reactivex.Flowable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

/**
 * Created by Michael Agustian on 31/10/18.
 */

interface ApiService {

    @GET("3/discover/movie/")
    fun getMovieDiscover(
            @Query("page") page: Int
    ): Flowable<ResultList<Movie>>

    @GET("3/movie/{movie_id}")
    fun getMovieDetail(
            @Path("movie_id") movieId: Int
    ): Flowable<Movie>

    companion object Factory {
        fun create(): ApiService {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            val client = OkHttpClient.Builder()
                    .readTimeout(30, TimeUnit.SECONDS)
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .addInterceptor(loggingInterceptor)
                    .addNetworkInterceptor { chain ->
                        val originalRequest = chain.request()
                        val originalUrl = originalRequest.url()
                        val url = originalUrl.newBuilder()
                                .addQueryParameter("api_key", BuildConfig.TMDB_API_KEY)
                                .addQueryParameter("language", "en-US")
                                .build()
                        val requestBuilder = originalRequest.newBuilder().url(url)
                        val request = requestBuilder.build()

                        chain.proceed(request)
                    }
                    .build()

            val retrofit = Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()

            return retrofit.create(ApiService::class.java)
        }
    }
}