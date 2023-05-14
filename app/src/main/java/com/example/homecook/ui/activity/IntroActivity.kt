package com.example.homecook.ui.activity

import android.os.Bundle
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.homecook.databinding.ActivityIntroBinding
import com.example.homecook.shared_pref.SharedPref
import com.example.homecook.ui.fragments.IntroFragment
import com.google.android.material.tabs.TabLayoutMediator

class IntroActivity : AppCompatActivity() {
    private val TAG = "IntroActivityTAG"
    private lateinit var _binding: ActivityIntroBinding
    private val binding get() = _binding
    private lateinit var sharedPref: SharedPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPref = SharedPref(this)
        _binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.getStartedBtn.setOnClickListener {
            sharedPref.storeIntroShown(true)
            val intent = android.content.Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        setUpViewPager()
//        Auto swipe view pager
        val handler = android.os.Handler(Looper.getMainLooper())
        val update = Runnable {
            if (binding.viewPager.currentItem == 2) {
                binding.viewPager.setCurrentItem(0, true)
            } else {
                binding.viewPager.setCurrentItem(binding.viewPager.currentItem + 1, true)
            }
        }
        val timer = java.util.Timer()
        timer.schedule(object : java.util.TimerTask() {
            override fun run() {
                handler.post(update)
            }
        }, 1000, 2000)
    }

    /*  ViewPager2 Adapter*/
    private fun setUpViewPager() {
        val adapter = IntroViewPagerAdapter(this)
        binding.viewPager.adapter = adapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
        }.attach()
    }

    /*ViewPager2 Adapter*/
    inner class IntroViewPagerAdapter(val context: android.content.Context) :
        androidx.viewpager2.adapter.FragmentStateAdapter(this) {
        override fun getItemCount(): Int {
            return 3
        }

        override fun createFragment(position: Int): androidx.fragment.app.Fragment {
            return when (position) {
                0 -> IntroFragment().apply {
                    arguments = Bundle().apply {
                        putInt("imageId", com.example.homecook.R.drawable.intro1)
                        putString("title", "Fast Delivery")
                    }
                }
                1 -> IntroFragment().apply {
                    arguments = Bundle().apply {
                        putInt("imageId", com.example.homecook.R.drawable.intro2)
                        putString("title", "Fresh Food")
                    }
                }
                2 -> IntroFragment().apply {
                    arguments = Bundle().apply {
                        putInt("imageId", com.example.homecook.R.drawable.intro3)
                        putString("title", "Enjoy Your Meal")
                    }
                }
                else -> IntroFragment()
            }
        }
    }

}