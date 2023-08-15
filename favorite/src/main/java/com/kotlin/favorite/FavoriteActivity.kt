package com.kotlin.favorite

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.kotlin.core.domain.model.User
import com.kotlin.core.ui.UserAdapter
import com.kotlin.favorite.databinding.ActivityFavoriteBinding
import com.kotlin.githubuserapp.detail.DetailUserActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var adapter: UserAdapter
    private val viewModel: FavoriteViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "User Favorite"

        loadKoinModules(viewModelModule)

        adapter = UserAdapter(::userClicked)

        viewModel.getFavorite.observe(this) {
            showLoading(true)
            if (!it.isNullOrEmpty()) {
                showLoading(false)
            } else {
                showLoading(false)
                binding.tvNoFavorite.visibility = View.VISIBLE
            }
            adapter.submitList(it)
        }

        with(binding.rvFav) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            this.adapter = this@FavoriteActivity.adapter
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun userClicked(user: User) {
        val intent = Intent(this@FavoriteActivity, DetailUserActivity::class.java)
        intent.putExtra(DetailUserActivity.EXTRA_USERNAME, user.username)
        startActivity(intent)
    }
}

