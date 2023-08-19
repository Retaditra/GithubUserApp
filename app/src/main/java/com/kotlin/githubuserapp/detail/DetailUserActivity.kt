package com.kotlin.githubuserapp.detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.tabs.TabLayoutMediator
import com.kotlin.core.data.Resource
import com.kotlin.core.domain.model.User
import com.kotlin.githubuserapp.R
import com.kotlin.githubuserapp.databinding.ActivityDetailUserBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding
    private val viewModel: DetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Detail User"

        val username = intent.getStringExtra(EXTRA_USERNAME).toString()

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        sectionsPagerAdapter.userData = username

        binding.viewPager.adapter = sectionsPagerAdapter
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        viewModel.userDetail(username).observe(this) {
            when (it) {
                is Resource.Loading -> showLoading(true)
                is Resource.Success -> {
                    val user = it.data
                    showLoading(false)
                    if (user != null) {
                        user.displayUserDetail()
                    } else {
                        Toast.makeText(this, getString(R.string.error), Toast.LENGTH_SHORT).show()
                    }
                }
                is Resource.Error -> {
                    showLoading(false)
                    val message = it.message.toString()
                    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun User.displayUserDetail() {
        binding.apply {
            tvUserDt.text = username
            tvID.text = id.toString()
            tvFG.text = following.toString()
            tvFS.text = followers.toString()

            Glide.with(this@DetailUserActivity)
                .load(avatarUrl)
                .apply(RequestOptions.circleCropTransform())
                .into(binding.ivAvatarDt)

            var statusFavorite = isFavorite
            fabFavorite.isChecked = statusFavorite
            fabFavorite.setOnCheckedChangeListener { _, isChecked ->
                statusFavorite = isChecked
                viewModel.setFavorite(this@displayUserDetail, statusFavorite)
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        const val EXTRA_USERNAME = "extra_username"

        @StringRes
        private val TAB_TITLES = intArrayOf(R.string.followers, R.string.following)
    }
}

