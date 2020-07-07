package com.learn.kotlin.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        usernameTextView.text = intent.getStringExtra(Constants.USERNAME)
        scoreTextView.text = "Your score is ${intent.getIntExtra(Constants.CORRECT_ANSWERS, 0)} out of"+
                " ${intent.getIntExtra(Constants.TOTAL_QUESTIONS, 0)}"

        resultFinishButton.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}