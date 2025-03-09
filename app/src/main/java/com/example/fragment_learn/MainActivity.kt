package com.example.fragment_learn

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private val btnFragmentTest: Button by lazy {
        findViewById(R.id.btn_fragment_test)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnFragmentTest.setOnClickListener(object : OnClickListener{
            override fun onClick(p0: View?) {
                var intent = Intent(this@MainActivity,FragmentTestActivity::class.java)
                startActivity(intent)
            }
        })
    }
}