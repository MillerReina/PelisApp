package com.example.peli

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("movie/popular")
    fun getPopularMovies(
        @Query("api_key") apiKey: String = "ff0bca50f58a700a9ca096ba4a3d4e45",
        @Query("page") page: Int,
        @Query("language") lg: String ="es-ES"
    ): Call<GetMoviesResponse>

    @GET("movie/top_rated")
    fun getTopRatedMovies(
            @Query("api_key") apiKey: String = "ff0bca50f58a700a9ca096ba4a3d4e45",
            @Query("page") page: Int,
            @Query("language") lg: String ="es-ES"
    ): Call<GetMoviesResponse>

    @GET("movie/upcoming")
    fun getUpcomingMovies(
            @Query("api_key") apiKey: String = "ff0bca50f58a700a9ca096ba4a3d4e45",
            @Query("page") page: Int,
            @Query("language") lg: String ="es-ES"
    ): Call<GetMoviesResponse>
}