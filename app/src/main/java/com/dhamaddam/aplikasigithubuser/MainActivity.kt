package com.dhamaddam.aplikasigithubuser

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var rvgithuber: RecyclerView

    private var list: ArrayList<UserGithub> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvgithuber = findViewById(R.id.rv_githuber)
        rvgithuber.setHasFixedSize(true)

        if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            rvgithuber.layoutManager = GridLayoutManager(this, 2)

        } else {
            rvgithuber.layoutManager = LinearLayoutManager(this)
        }

        list.addAll(listUser)
        showRecyclerList()
    }

    private fun showSelectedHero(user: UserGithub) {

        val startGithubUserDetails = Intent(this@MainActivity, GithubUserDetailsActivity::class.java)
        startGithubUserDetails.putExtra(GithubUserDetailsActivity.EXTRA_USER, user)
        startActivity(startGithubUserDetails)

//        Toast.makeText(this, "Kamu memilih " + user.Username, Toast.LENGTH_SHORT).show()
    }

    private val listUser: ArrayList<UserGithub>
        get() {
            val dataName = resources.getStringArray(R.array.username)
            val dataDescription = resources.getStringArray(R.array.name)
            val dataPhoto = resources.obtainTypedArray(R.array.avatar)
            val dataCompany = resources.getStringArray(R.array.company)
            val dataLocation = resources.getStringArray(R.array.location)
            val dataRepository = resources.getStringArray(R.array.repository)
            val dataFollowers = resources.getStringArray(R.array.followers)
            val dataFollowing = resources.getStringArray(R.array.following)


            val listU = ArrayList<UserGithub>()
            for (i in dataName.indices) {
                val user = UserGithub(
                    dataName[i],
                    dataDescription[i],
                    dataPhoto.getResourceId(i, -1),
                    dataCompany[i],
                    dataLocation[i],
                    dataRepository[i],
                    dataFollowers[i],
                    dataFollowing[i]
                )
                listU.add(user)
            }
            return listU
        }

    private fun showRecyclerList() {
        rvgithuber.layoutManager = LinearLayoutManager(this)
        val listHeroAdapter = ListUserAdapter(list)
        rvgithuber.adapter = listHeroAdapter

        listHeroAdapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: UserGithub) {
                showSelectedHero(data)
            }
        })

    }
}