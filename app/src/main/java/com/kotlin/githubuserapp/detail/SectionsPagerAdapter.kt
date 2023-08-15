package com.kotlin.githubuserapp.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.kotlin.githubuserapp.followers.Followers
import com.kotlin.githubuserapp.following.Following

class SectionsPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    var userData: String = ""

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = Followers()
            1 -> fragment = Following()
        }
        fragment?.arguments = Bundle().apply {
            putString(DetailUserActivity.EXTRA_USERNAME, userData)
        }
        return fragment as Fragment
    }
}