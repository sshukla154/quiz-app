package com.learn.kotlin.quizapp

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_quiz_questions.*

class QuizQuestionsActivity : AppCompatActivity(), View.OnClickListener {

    private var currentPosition : Int = 1
    private var questionsList: ArrayList<Question>? = null
    private var mSelectedOption : Int = 0
    private var correctAnswersCount : Int = 0
    private var username : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_questions)

        username = intent.getStringExtra(Constants.USERNAME)


        questionsList = Constants.getQuestions()
        Log.i("Questions size: ", "${questionsList!!.size}")

        setQuestion()

        optionOneTextView.setOnClickListener(this)
        optionTwoTextView.setOnClickListener(this)
        optionThreeTextView.setOnClickListener(this)
        optionFourTextView.setOnClickListener(this)

        submitBtn.setOnClickListener(this)

    }

    fun setQuestion(){

        var question = questionsList!![currentPosition - 1]

        defaultOptionView()

        if(currentPosition == questionsList!!.size){
            submitBtn.text = "FINISH"
        } else {
            submitBtn.text = "SUBMIT"
        }

        progressBar.progress = currentPosition
        progressBarTextView.text = "$currentPosition"+"/" + progressBar.max

        questionTextView.text = question!!.question
        flagImageView.setImageResource(question.image)
        optionOneTextView.text = question.optionOne
        optionTwoTextView.text = question.optionTwo
        optionThreeTextView.text = question.optionThree
        optionFourTextView.text = question.optionFour
    }

    private fun defaultOptionView() {
        val options = ArrayList<TextView>()
        options.add(0, optionOneTextView)
        options.add(1, optionTwoTextView)
        options.add(2, optionThreeTextView)
        options.add(3, optionFourTextView)

        for(option in options){
            option.setTextColor(Color.parseColor("#7A8089"))
            //Typeface
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(this, R.drawable.default_option_border_bg)
        }
    }

    override fun onClick(v: View?) {

        when(v?.id){
            R.id.optionOneTextView -> {
                selectedOptionView(optionOneTextView, 1)
            }

            R.id.optionTwoTextView -> {
                selectedOptionView(optionTwoTextView, 2)
            }

            R.id.optionThreeTextView -> {
                selectedOptionView(optionThreeTextView, 3)
            }

            R.id.optionFourTextView -> {
                selectedOptionView(optionFourTextView, 4)
            }

            R.id.submitBtn -> {
                if(mSelectedOption == 0){
                    currentPosition++

                    when{
                        currentPosition <= questionsList!!.size -> {
                            setQuestion()
                        } else -> {
                            val intent = Intent(this, ResultActivity::class.java)
                            intent.putExtra(Constants.USERNAME, username)
                            intent.putExtra(Constants.CORRECT_ANSWERS, correctAnswersCount)
                            intent.putExtra(Constants.TOTAL_QUESTIONS, questionsList!!.size)
                            startActivity(intent)
                        }
                    }
                } else {
                    val question = questionsList?.get(currentPosition - 1)

                    //To display the in-correct answer with red background
                    if(question?.correctOption  != mSelectedOption){
                        answerView(mSelectedOption, R.drawable.wrong_option_border_bg)
                    } else {
                        correctAnswersCount++
                    }
                    //To display the correct answer with green background
                    answerView(question!!.correctOption, R.drawable.correct_option_border_bg)

                    // For last question we should get submit option
                    if(currentPosition == questionsList!!.size){
                        submitBtn.text = "FINISH"
                    } else {
                        submitBtn.text = "NEXT QUESTION"
                    }
                    mSelectedOption = 0
                }
            }
        }

    }

    private fun answerView(answer: Int, drawable: Int){
        when(answer){
            1 -> {
                optionOneTextView.background = ContextCompat.getDrawable(this, drawable)
            }
            2 -> {
                optionTwoTextView.background = ContextCompat.getDrawable(this, drawable)
            }
            3 -> {
                optionThreeTextView.background = ContextCompat.getDrawable(this, drawable)
            }
            4 -> {
                optionFourTextView.background = ContextCompat.getDrawable(this, drawable)
            }
        }

    }

    private fun selectedOptionView(tv: TextView, selectedOptionNumber: Int) {
       defaultOptionView()
       mSelectedOption = selectedOptionNumber
       tv.setTextColor(Color.parseColor("#363A43"))
       tv.setTypeface(tv.typeface, Typeface.BOLD)
       tv.background = ContextCompat.getDrawable(this, R.drawable.selected_option_border_bg)
    }
}