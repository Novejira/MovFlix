package com.nafi.movflix.data.source.network.service

import com.nafi.movflix.BuildConfig
import com.nafi.movflix.data.source.network.model.movie.MovieNowPlayingResponse
import com.nafi.movflix.data.source.network.model.movie.MoviePopularResponse
import com.nafi.movflix.data.source.network.model.movie.MovieTopRatedResponse
import com.nafi.movflix.data.source.network.model.movie.MovieUpComingResponse
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface MovFlixApiService {
    @Headers("accept: application/json")
    @GET("discover/movie")
    suspend fun getNowPlayingMovies(
        @Query("include_adult") includeAdult: Boolean = false,
        @Query("include_video") includeVideo: Boolean = false,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1,
        @Query("sort_by") sortBy: String = "popularity.desc",
        @Query("with_release_type") withReleaseType: String = "2|3",
        @Query("release_date.gte") minDate: String,
        @Query("release_date.lte") maxDate: String,
    ): MovieNowPlayingResponse

    @GET("discover/movie")
    suspend fun getPopularMovies(
        @Query("include_adult") includeAdult: Boolean = false,
        @Query("include_video") includeVideo: Boolean = false,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1,
        @Query("sort_by") sortBy: String = "popularity.desc",
    ): MoviePopularResponse

    @GET("discover/movie")
    suspend fun getTopRatedMovies(
        @Query("include_adult") includeAdult: Boolean = false,
        @Query("include_video") includeVideo: Boolean = false,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1,
        @Query("sort_by") sortBy: String = "vote_average.desc",
        @Query("without_genres") withoutGenres: String = "99,10755",
        @Query("vote_count.gte") voteCountGte: Int = 200,
    ): MovieTopRatedResponse

    @GET("discover/movie")
    suspend fun getUpComingMovies(
        @Query("include_adult") includeAdult: Boolean = false,
        @Query("include_video") includeVideo: Boolean = false,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1,
        @Query("sort_by") sortBy: String = "popularity.desc",
        @Query("with_release_type") withReleaseType: String = "2|3",
        @Query("release_date.gte") minDate: String,
        @Query("release_date.lte") maxDate: String,
    ): MovieUpComingResponse

    companion object {
        @JvmStatic
        operator fun invoke(): MovFlixApiService {
            val okHttpClient =
                OkHttpClient.Builder()
                    .addInterceptor(
                        Interceptor { chain ->
                            val newRequest =
                                chain.request().newBuilder()
                                    .addHeader(
                                        "Authorization",
                                        "Bearer ${BuildConfig.Bearer}",
                                    )
                                    .build()
                            chain.proceed(newRequest)
                        },
                    ).build()

            val retrofit =
                Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build()
            return retrofit.create(MovFlixApiService::class.java)
        }
    }
}
