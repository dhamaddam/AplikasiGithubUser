package com.dhamaddam.aplikasigithubuser

import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.bumptech.glide.Glide
import com.dhamaddam.aplikasigithubuser.databinding.ActivityGithubUserDetailsBinding

class GithubUserDetailsActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_USER = "extra_user"
    }
    private lateinit var binding: ActivityGithubUserDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityGithubUserDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (supportActionBar != null) {
            (supportActionBar as ActionBar).title = "Detail Pengguna"
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val user = intent.getParcelableExtra<UserGithub>(EXTRA_USER) as UserGithub

        binding.tvNameReceived.text = "Name : " + user.Name
        binding.tvRepository.text = "Repository : " +user.repository
        binding.tvFollowing.text = "Following : " +user.following
        binding.tvFollower.text = "Followers : " + user.followers
        binding.tvCompany.text = "Company : " +user.company
        binding.tvLocation.text = "Location : " + user.location

        Glide.with(this).load(user.Avatar).into(binding.ivAvatarReceived)
    }


}