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
import com.dhamaddam.aplikasigithubuser.databinding.FragmentFollowingBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentFollowing.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentFollowing : Fragment() {

    lateinit var rVFollowing: RecyclerView
    lateinit var progressBarFollowing : ProgressBar
    lateinit var listFollowingRC:RecyclerViewAdapterFragment
    lateinit var tv_no_following : TextView
    private lateinit var followingViewModel: FollowingViewModel
    private var list: ArrayList<GithubResponseItem> = arrayListOf()

    private var username: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            username = it.getString(FragmentFollower.USERNAME)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentFollowingBinding.inflate(layoutInflater)
        progressBarFollowing = binding.progressBar2
        rVFollowing = binding.recyclerViewFollowing
        tv_no_following = binding.textView
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        showLoadingFollowing(true)
        followingViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(FollowingViewModel::class.java)
        showRecyclerView()
        username.let { it?.let { it1 -> followingViewModel.setFollowing(it1, requireContext()) } }

        followingViewModel.getFollowing().observe(viewLifecycleOwner, Observer { followingItems ->
            if (followingItems != null) {
                showFollowingItems(followingItems)
                showLoadingFollowing(false)
            }
        })

    }

    private fun showRecyclerView() {
        rVFollowing.layoutManager = LinearLayoutManager(activity)
        listFollowingRC = RecyclerViewAdapterFragment(list)
        rVFollowing.adapter = listFollowingRC
        listFollowingRC.notifyDataSetChanged()
        rVFollowing.setHasFixedSize(true)
    }

    private fun showLoadingFollowing(state: Boolean) {
        if (state) progressBarFollowing.visibility = View.VISIBLE
        else progressBarFollowing.visibility = View.GONE
    }

    private fun showFollowingItems(followingItems: ArrayList<GithubResponseItem>) {

        listFollowingRC.setFollowerData(followingItems)
        when (followingItems.size) {
            0 -> tv_no_following.visibility = View.VISIBLE
            else -> tv_no_following.visibility = View.GONE
        }
    }

    companion object {
        const val USERNAME = "username"
        const val ARG_SECTION_NUMBER = "section_number"
    }
}