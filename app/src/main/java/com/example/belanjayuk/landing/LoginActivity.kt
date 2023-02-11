package com.example.belanjayuk.landing

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.belanjayuk.databinding.ActivityLoginBinding
import com.example.belanjayuk.localmanager.PrefManager
import com.example.belanjayuk.main.MainActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var prefManager: PrefManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prefManager = PrefManager(this)
        loginSession()
    }

    private fun loginSession() = with(binding) {
        btnSignIn.setOnClickListener {
            val username = etUsername.text.toString()
            val password = etPassword.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                prefManager.setLogin(true)
                prefManager.setUsername(username)

                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this@LoginActivity, "Maaf, Username dan Password tidak sesuai", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}