package com.example.fragment_learn

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlin.math.abs

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_test)

        val viewPager = findViewById<ViewPager2>(R.id.viewPager)
        val adapter = ViewPagerAdapter(this)
        viewPager.adapter = adapter

        // 初始化TabLayout
        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            // 创建自定义视图
            val tabView = LayoutInflater.from(this)
                .inflate(R.layout.custom_tab, null)

            val tabText = tabView.findViewById<TextView>(R.id.tab_text)
            val tabIcon = tabView.findViewById<ImageView>(R.id.tab_icon)

            // 设置文本和图标
            when(position) {
                0 -> {
                    tabText.text = "首页"
                    tabIcon.setImageResource(R.drawable.ic_home)
                }
                1 -> {
                    tabText.text = "设置"
                    tabIcon.setImageResource(R.drawable.ic_settings)
                }
                2 -> {
                    tabText.text = "个人"
                    tabIcon.setImageResource(R.drawable.ic_me)
                }
            }
            tab.customView = tabView // 将自定义视图应用到Tab
        }.attach()

        // 添加Tab选择监听
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.customView?.let { view ->
                    val textView = view.findViewById<TextView>(R.id.tab_text)
                    val iconView = view.findViewById<ImageView>(R.id.tab_icon)
                    textView.setTextColor(ContextCompat.getColor(this@HomeActivity, R.color.selected_color))
                    iconView.setColorFilter(ContextCompat.getColor(this@HomeActivity, R.color.selected_color))
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                tab?.customView?.let { view ->
                    val textView = view.findViewById<TextView>(R.id.tab_text)
                    val iconView = view.findViewById<ImageView>(R.id.tab_icon)
                    textView.setTextColor(ContextCompat.getColor(this@HomeActivity, R.color.unselected_color))
                    iconView.setColorFilter(ContextCompat.getColor(this@HomeActivity, R.color.unselected_color))
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        // 设置默认选中状态（可选）
        tabLayout.getTabAt(0)?.select() // 默认选中第一个Tab

        // 添加页面切换动画
        viewPager.setPageTransformer { page, position ->
            page.alpha = 1 - abs(position)
            page.translationX = -position * page.width
        }
    }
}