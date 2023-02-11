package com.example.belanjayuk.landing

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.belanjayuk.databinding.ActivitySplashScreenBinding
import com.example.belanjayuk.localmanager.PrefManager
import com.example.belanjayuk.main.MainActivity

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var bindng: ActivitySplashScreenBinding
    private lateinit var prefManager: PrefManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindng = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(bindng.root)

        prefManager = PrefManager(this)

        val splashTime: Long = 3000
        Handler().postDelayed({
            if (prefManager.isLogin() == true) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }, splashTime)
    }
}