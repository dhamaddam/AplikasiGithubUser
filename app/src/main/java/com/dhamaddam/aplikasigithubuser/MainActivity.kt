package com.dhamaddam.aplikasigithubuser

import android.app.SearchManager
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.app.TaskStackBuilder
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.dhamaddam.aplikasigithubuser.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {


    private var list: ArrayList<GithubResponseItem> = arrayListOf()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvGithuber.setHasFixedSize(true)

        if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding.rvGithuber.layoutManager = GridLayoutManager(this, 2)

        } else {
            binding.rvGithuber.layoutManager = LinearLayoutManager(this)
        }

        getUserGithub()
        setupServices()

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun showSelectedUser(user: GithubResponseItem) {
        val startGithubUserDetails = Intent(this@MainActivity, GithubUserDetailsActivity::class.java)
        startGithubUserDetails.putExtra(GithubUserDetailsActivity.EXTRA_USER, user.username)
        startActivity(startGithubUserDetails)
    }

    private fun getUserGithub()
    {
        showLoading(true)

        val client = ApiConfig.getApiService().getAllUser("token ${BuildConfig.TOKEN}")

        client.enqueue( object : Callback <ArrayList<GithubResponseItem>> {
            override fun onResponse(
                call: Call<ArrayList<GithubResponseItem>>,
                response: Response<ArrayList<GithubResponseItem>>
            ) {
                showLoading(false)

                if (response.isSuccessful) {

                    val responseBody = response.body()

                    if (responseBody != null) {

                        list = response.body() as ArrayList<GithubResponseItem>
                        showRecyclerList()

                    }
                } else {
                    var statusCode = response.code()
                    val errorMessage = when (response.code()) {
                        401 -> "$statusCode : Bad Request"
                        403 -> "$statusCode : Forbidden requests get a higher rate limit"
                        404 -> "$statusCode : Not Found"
                        else -> "$statusCode : ${response.message()}"
                    }
                    Toast.makeText(applicationContext,"Error" + errorMessage, Toast.LENGTH_LONG ).show()
                    Log.e(TAG, "onFailure: ${response.message()}")
                }

            }

            override fun onFailure(call: Call<ArrayList<GithubResponseItem>>, t: Throwable) {
                showLoading(false)
                val errorMessage = t.message
                Toast.makeText(applicationContext, errorMessage, Toast.LENGTH_SHORT).show()
            }


        })
    }

    private fun setupServices() {
        WifiService.instance.initializeWithApplicationContext(this)
    }

    private fun SearchUserGithub(username:String)
    {
        showLoading(true)

        val client = ApiConfig.getApiService().searchAllUser("token ${BuildConfig.TOKEN}", username)

        client.enqueue( object : Callback <SearchUserGithubResponse> {
            override fun onResponse(
                call: Call<SearchUserGithubResponse>,
                response: Response<SearchUserGithubResponse>
            ) {
                showLoading(false)

                if (response.isSuccessful) {

                    val responseBody = response.body()

                    if (responseBody != null) {
                        list.clear()
                        list = response.body()?.items as ArrayList<GithubResponseItem>
                        showRecyclerList()

                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }

            }

            override fun onFailure(call: Call<SearchUserGithubResponse>, t: Throwable) {
                showLoading(false)

                val errorMessage = t.message

                Toast.makeText(applicationContext, errorMessage, Toast.LENGTH_SHORT).show()
            }


        })
    }



    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun showRecyclerList() {
        binding.rvGithuber.layoutManager = LinearLayoutManager(this)
        val listHeroAdapter = ListUserAdapter(list)
        binding.rvGithuber.adapter = listHeroAdapter

        listHeroAdapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: GithubResponseItem) {
                showSelectedUser(data)
            }
        })

    }

    override fun onCreateSupportNavigateUpTaskStack(builder: TaskStackBuilder) {
        super.onCreateSupportNavigateUpTaskStack(builder)
        getUserGithub()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        var searchView = menu.findItem(R.id.search).actionView as SearchView
        var menuSearch = menu.findItem(R.id.search)
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                Toast.makeText(this@MainActivity, query, Toast.LENGTH_SHORT).show()
                SearchUserGithub(query)
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return true
            }


        })

        val expandListener = object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
                getUserGithub()
                return true // Return true to collapse action view
            }

            override fun onMenuItemActionExpand(item: MenuItem): Boolean {
                // Do something when expanded
                return true // Return true to expand action view
            }
        }

        var FavoriteButton = menu.findItem(R.id.action_favorite)
        FavoriteButton.setOnMenuItemClickListener ( object : MenuItem.OnMenuItemClickListener  {
            override fun onMenuItemClick(item: MenuItem?): Boolean {

                val startGithubUserDetails = Intent(applicationContext, UserFavoriteActivity::class.java)
                startActivity(startGithubUserDetails)

                return true
            }

        } )

        menuSearch?.setOnActionExpandListener(expandListener)

        return true
    }
}