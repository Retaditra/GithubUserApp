package com.kotlin.githubuserapp.following

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.kotlin.core.data.Resource
import com.kotlin.core.domain.model.User
import com.kotlin.core.ui.FollowAdapter
import com.kotlin.githubuserapp.databinding.FragmentFollowingBinding
import com.kotlin.githubuserapp.detail.DetailUserActivity
import com.kotlin.githubuserapp.detail.DetailUserActivity.Companion.EXTRA_USERNAME
import org.koin.androidx.viewmodel.ext.android.viewModel

class Following : Fragment() {

    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: FollowAdapter
    private val viewModel: FollowingViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = FollowAdapter(::userClicked)
        val username = arguments?.getString(EXTRA_USERNAME).toString()

        viewModel.getFollowing(username).observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> showLoading(true)
                is Resource.Success -> {
                    showLoading(false)
                    if (!it.data.isNullOrEmpty()) {
                        adapter.submitList(it.data)
                    } else {
                        adapter.submitList(emptyList())
                        binding.tvNoFollowing.visibility = View.VISIBLE
                    }
                }
                is Resource.Error -> {
                    showLoading(false)
                    val message = it.message.toString()
                    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        with(binding.rvFollowing) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            this.adapter = this@Following.adapter
        }
    }

    private fun userClicked(user: User) {
        val intent = Intent(requireContext(), DetailUserActivity::class.java)
        intent.putExtra(EXTRA_USERNAME, user.username)
        startActivity(intent)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBarFg.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}