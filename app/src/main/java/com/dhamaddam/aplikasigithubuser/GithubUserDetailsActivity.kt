package com.dhamaddam.aplikasigithubuser

import android.content.ContentValues
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.dhamaddam.aplikasigithubuser.databinding.ActivityGithubUserDetailsBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GithubUserDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGithubUserDetailsBinding

    private lateinit var sectionsPagerAdapter: SectionsPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityGithubUserDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (supportActionBar != null) {
            (supportActionBar as ActionBar).title = "Detail Pengguna"
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        var username = intent.getStringExtra(EXTRA_USER).toString()

        DetailsUserGithub(username)

        sectionsPagerAdapter = SectionsPagerAdapter(this)
        sectionsPagerAdapter.username = username

        setupServices()

        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabFollowerFollowing

        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        supportActionBar?.elevation = 0f

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setupServices() {
        WifiService.instance.initializeWithApplicationContext(this)
    }

    private fun DetailsUserGithub(username:String)
    {
        showLoading(true)

        val client = ApiConfig.getApiService().getDetailsUserGithub("token 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",username)

        client.enqueue( object : Callback<DetailsItem> {
            override fun onResponse(
                call: Call<DetailsItem>,
                response: Response<DetailsItem>
            ) {
                showLoading(false)

                if (response.isSuccessful) {

                    val responseBody = response.body()

                    if (responseBody != null) {

                         setUserDetailsData(responseBody)

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

                    Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                }

            }

            override fun onFailure(call: Call<DetailsItem>, t: Throwable) {
                showLoading(false)
                Log.e(ContentValues.TAG, "onFailure: ${t.message}")
                val errorMessage = t.message
                Toast.makeText(applicationContext, errorMessage, Toast.LENGTH_SHORT).show()
            }


        })
    }


    private fun setUserDetailsData(Items: DetailsItem) {
        binding.apply {
            tvNameReceived.text = "Name : " + Items.name
            tvRepository.text = "Repository : " +Items.publicRepos
            tvFollowing.text = "Following : " +Items.following
            tvFollower.text = "Followers : " + Items.followers
            tvCompany.text = "Company : " +Items.company
            tvLocation.text = "Location : " + Items.location

        }
        Glide.with(this).load(Items.avatarUrl).into(binding.ivAvatarReceived)
    }






    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }


    companion object {
        const val EXTRA_USER = "extra_user"
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }
}