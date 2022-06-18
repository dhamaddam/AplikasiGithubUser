package com.dhamaddam.aplikasigithubuser

import android.content.ContentValues
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingViewModel: ViewModel()  {
    private var listFollowing = MutableLiveData<ArrayList<GithubResponseItem>>()

    fun setFollowing(username: String, context: Context) {

        val client = ApiConfig.getApiService().getFollowing(username)

            client.enqueue( object : Callback<ArrayList<GithubResponseItem>> {
                override fun onResponse(
                    call: Call<ArrayList<GithubResponseItem>>,
                    response: Response<ArrayList<GithubResponseItem>>
                ) {
                    if (response.isSuccessful) {

                        val responseBody = response.body()

                        if (responseBody != null) {

                           var listItems = response.body() as ArrayList<GithubResponseItem>
                            listFollowing.postValue(listItems)
                        }
                    } else {
                        Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                    }

                }

                override fun onFailure(call: Call<ArrayList<GithubResponseItem>>, t: Throwable) {
                    Log.e(ContentValues.TAG, "onFailure: ${t.message}")
                }


            })
        }

    fun getFollowing(): LiveData<ArrayList<GithubResponseItem>> = listFollowing

}