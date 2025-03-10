package com.example.fragment_learn

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class LauncherActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)

        if (isUserLoggedIn()) {
            // 如果已登录且 Token 未过期
            startActivity(Intent(this, HomeActivity::class.java))
        } else {
            // 否则跳转到登录页面
            startActivity(Intent(this, LoginActivity::class.java))
        }
        finish()
    }

    private fun isUserLoggedIn(): Boolean {
        val sharedPrefs = getSharedPreferences("my_app_prefs", MODE_PRIVATE)

        // 从本地读取 Token
        val token = sharedPrefs.getString("user_token", null)
        // 从本地读取 Token 过期时间（示例中为毫秒时间戳）
        val tokenExpiryTime = sharedPrefs.getLong("token_expiry_time", 0L)

        // 1. 如果本地没有存储 Token，说明从未登录过
        if (token.isNullOrEmpty()) {
            return false
        }

        // 2. 判断 Token 是否已过期
        val currentTime = System.currentTimeMillis()
        if (currentTime > tokenExpiryTime) {
            // Token 已过期
            return false
        }
        return true
    }

}