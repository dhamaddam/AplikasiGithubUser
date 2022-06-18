package com.dhamaddam.aplikasigithubuser

import retrofit2.Call
import retrofit2.http.*

interface ApiServices {

    @GET("/users")
    @Headers("Authorization: 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")
    fun getAllUser(): Call<ArrayList<GithubResponseItem>>

    @GET("/search/users?q=")
    @Headers("Authorization: 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")
    fun searchAllUser(@Query("q") q:String): Call<SearchUserGithubResponse>

    @GET ("/users/{username}/followers")
    fun getFollower(@Path("username") username: String?) : Call <ArrayList<GithubResponseItem>>

    @GET ("/users/{username}")
    fun getDetailsUserGithub(@Path("username") username: String) : Call <DetailsItem>

    @GET ("/users/{username}/following")
    fun getFollowing(@Path("username") username: String?) : Call<ArrayList<GithubResponseItem>>

}