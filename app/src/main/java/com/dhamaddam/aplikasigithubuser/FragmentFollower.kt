package com.dhamaddam.aplikasigithubuser

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dhamaddam.aplikasigithubuser.databinding.FragmentFollowerBinding

class FragmentFollower : Fragment() {

    lateinit var rVFollower: RecyclerView
    lateinit var progressBarFollower : ProgressBar
    lateinit var listFollowerRC:RecyclerViewAdapterFragment
    lateinit var tv_no_follower : TextView
    private lateinit var followersViewModel: FollowersViewModel
    private var list: ArrayList<GithubResponseItem> = arrayListOf()
    private var username: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            username = it.getString(USERNAME)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentFollowerBinding.inflate(layoutInflater)
        progressBarFollower = binding.progressBar2
        rVFollower = binding.recyclerViewFollower
        tv_no_follower = binding.textView
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        showLoadingFollowers(true)
        followersViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(FollowersViewModel::class.java)
        showRecyclerView()
        username.let { it?.let { it1 -> followersViewModel.setFollower(it1, requireContext()) } }

        followersViewModel.getFollowers().observe(viewLifecycleOwner, Observer { followerItems ->
            if (followerItems != null) {
                showFollowerItems(followerItems)
                showLoadingFollowers(false)
            }
        })

    }
    private fun showFollowerItems(followerItems: ArrayList<GithubResponseItem>) {

        listFollowerRC.setFollowerData(followerItems)
        when (followerItems.size) {
            0 -> tv_no_follower.visibility = View.VISIBLE
            else -> tv_no_follower.visibility = View.GONE
        }
    }
    private fun showRecyclerView() {
        rVFollower.layoutManager = LinearLayoutManager(activity)
        listFollowerRC = RecyclerViewAdapterFragment(list)
        rVFollower.adapter = listFollowerRC
        listFollowerRC.notifyDataSetChanged()
        rVFollower.setHasFixedSize(true)
    }

    private fun showLoadingFollowers(state: Boolean) {
        if (state) progressBarFollower.visibility = View.VISIBLE
        else progressBarFollower.visibility = View.GONE
    }

    companion object {
        const val USERNAME = "username"
        const val ARG_SECTION_NUMBER = "section_number"
    }
}