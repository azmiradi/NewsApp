package azmithabet.com.news.representation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import azmithabet.com.news.R
import azmithabet.com.news.representation.news.activity.MainActivity
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        MainScope().launch {
            delay(3000)
            val intent=Intent(this@SplashActivity,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}