package com.dhamaddam.aplikasigithubuser

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Window

import androidx.appcompat.app.AppCompatActivity

class SplashScreenActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.splashscreen)
        Handler().postDelayed({
            startActivity(Intent(applicationContext, MainActivity::class.java))
            finish()
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }, DELAY.toLong())
    }

    companion object {
        const val DELAY = 3000
    }
}
