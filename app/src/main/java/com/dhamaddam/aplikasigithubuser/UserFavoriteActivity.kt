package com.dhamaddam.aplikasigithubuser

import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.dhamaddam.aplikasigithubuser.databinding.ActivityUserFavoriteBinding

class UserFavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserFavoriteBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUserFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (supportActionBar != null) {
            (supportActionBar as ActionBar).title = "Favorite User"
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}