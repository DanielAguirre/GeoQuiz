package com.example.daniel.geoquiz

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Log.v
import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.Animation
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_cheat.*
import kotlinx.android.synthetic.main.activity_quiz.*
import org.w3c.dom.Text

class CheatActivity : AppCompatActivity() {

    companion object{
        var EXTRA_ANSWER_IS_TRUE = "com.daniel.android.geoquiz.answer_is_true"
        var mAnswerIsTrue:Boolean = false
        var EXTRA_ANSWER_SHOWN = "com.daniel.android.geoquiz.answer_shown"
        private val TAG = "CheatActivity"

        fun newIntent(packageContext: Context, answerIsTrue:Boolean ): Intent {
            val i = Intent(packageContext, CheatActivity::class.java )
            i.putExtra(CheatActivity. EXTRA_ANSWER_IS_TRUE,answerIsTrue)
            return i
        }

        fun wasAnswerShown(result:Intent) = result.getBooleanExtra(EXTRA_ANSWER_SHOWN, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG,"onCreate")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cheat)
        version_text.setText(Build.VERSION.SDK_INT)
        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false)
        showAnswerButton?.setOnClickListener {
            answer_text_view.setText(if(mAnswerIsTrue) R.string.true_button else R.string.false_button)
            setAnswerShownResult(true)
        }
    }

    fun setAnswerShownResult(isAnswerShown: Boolean) {
        var data = Intent()
        data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown)
        setResult(Activity.RESULT_OK)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            var cx = showAnswerButton.width / 2
            var cy = showAnswerButton.height / 2
            var radius = showAnswerButton.width
            var anim = ViewAnimationUtils.createCircularReveal(showAnswerButton, cx, cy, radius.toFloat(), 0F)
            anim.addListener(object: AnimatorListenerAdapter() {
                fun onAnimactionEnd(animation:Animator) {
                    super.onAnimationEnd(animation)
                    answer_text_view.visibility = View.VISIBLE
                    showAnswerButton.visibility = View.INVISIBLE
                }
            })
            anim.start()
        } else {
            answer_text_view.visibility = View.VISIBLE
            showAnswerButton.visibility = View.INVISIBLE

        }


    }

}
