package com.msu.morrison.geoquiz4

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.msu.morrison.geoquiz4.databinding.ActivityCheatBinding
import com.msu.morrison.geoquiz4.databinding.ActivityMainBinding

const val EXTRA_ANSWER_SHOWN = "com.msu.morrison.geoquiz4.answer_shown"

private const val EXTRA_ANSWER_IS_TRUE =
    "com.msu.morrison.geoquiz4.answer_is_true"

class CheatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCheatBinding
    private lateinit var viewModel: QuizViewModel

    private var answerIsTrue = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(QuizViewModel::class.java)

        answerIsTrue = intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false)

        if (viewModel.isCheater) {
            showAnswer()
        }


        binding.showAnswerButton.setOnClickListener{
           // val answerText = when {
           //     answerIsTrue -> R.string.true_button
            //    else -> R.string.false_button
          //  }
          //  binding.answerTextView.setText(answerText)
           // setAnswerShownResult(true)
            showAnswer()
            viewModel.isCheater = true
        }
    }

    private fun showAnswer() {
        val answerText = when {
            answerIsTrue -> R.string.true_button
            else -> R.string.false_button
        }
        binding.answerTextView.setText(answerText)
        setAnswerShownResult(true)
    }

    private fun setAnswerShownResult(isAnswerShown: Boolean) {
        val data = Intent().apply {
            putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown)
        }
        setResult(Activity.RESULT_OK, data)
    }

    companion object {
        fun newIntent(packageContext: Context, answerIsTrue: Boolean): Intent {
            return Intent(packageContext, CheatActivity::class.java).apply {
                putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue)
            }
        }
    }
}