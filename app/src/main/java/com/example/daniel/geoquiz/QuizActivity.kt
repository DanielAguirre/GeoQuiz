package com.example.daniel.geoquiz

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_quiz.*

class QuizActivity : AppCompatActivity() {
    private val mQuestionBank: Array<Question> = arrayOf(
            Question( R.string.question_oceans, true),
            Question( R.string.question_mideast, false),
            Question( R.string.question_africa, false),
            Question( R.string.question_americas, true),
            Question( R.string.question_asia, true)

    )
    private var mCurrentIndex = 0
    private var mIsCheater = false
    private val TAG = "QuizActivity"
    private val KEY_INDEX = "index"
    private val KEY_CHEAT = "cheat"
    private val REQUEST_CODE_CHEAT = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        mCurrentIndex = savedInstanceState?.let{  savedInstanceState.getInt(KEY_INDEX,0) } ?: 0

        val questrion = mQuestionBank[mCurrentIndex].mTextResId


        cheat_button?.setOnClickListener({
            val answerIstrue = mQuestionBank[mCurrentIndex].mAnwserTrue
            val i = CheatActivity.newIntent(this, answerIstrue)
            startActivityForResult(i, REQUEST_CODE_CHEAT)
        })

        true_button.setOnClickListener{ checkAnswer(true) }

        false_button?.setOnClickListener{ checkAnswer(false)}

        question_text_view?.setOnClickListener { updateQuestion() }

        next_button?.setOnClickListener{
            this.mIsCheater = false
            updateQuestion()
        }

        prev_button?.setOnClickListener{ run {
            mCurrentIndex = if(mCurrentIndex > 0) --mCurrentIndex else 0
            val question = mQuestionBank[mCurrentIndex].mTextResId
            question_text_view?.setText(question)
        }}

        savedInstanceState?.let {
            this.mCurrentIndex = savedInstanceState.getInt(KEY_INDEX,0)
            this.mIsCheater = savedInstanceState.getBoolean(KEY_CHEAT, false)
        }

        updateQuestion()
    }
    override fun onSaveInstanceState(savedInstanceState: Bundle?) {
        super.onSaveInstanceState(savedInstanceState)
        savedInstanceState?.putInt(KEY_INDEX, mCurrentIndex)
        savedInstanceState?.putBoolean(KEY_CHEAT, mIsCheater)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.d(TAG,"ResultCode $resultCode, ${Activity.RESULT_OK}, $REQUEST_CODE_CHEAT")
        if(resultCode != Activity.RESULT_OK) return
        if(resultCode == REQUEST_CODE_CHEAT) {
            if(data == null) {
                return
            }
            mIsCheater = CheatActivity.wasAnswerShown(data)
        }
    }


    fun updateQuestion() {
        if(mCurrentIndex < mQuestionBank.size-1){
            mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.size
            val question = mQuestionBank[mCurrentIndex].mTextResId
            question_text_view?.setText(question)
        } else {
            return
        }
    }

    fun checkAnswer(userPressedTrue:Boolean){
        val answerIsTrue = mQuestionBank[mCurrentIndex].mAnwserTrue
        var messageResId = 0

        if(mIsCheater == true){
            messageResId = R.string.judgment_toast
        } else{
            if(userPressedTrue == answerIsTrue)
                messageResId =  R.string.correct_toast
            else
                messageResId =  R.string.incorrect_toast
        }

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart*() called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause*() called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume*() called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop*() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy*() called")
    }

}
