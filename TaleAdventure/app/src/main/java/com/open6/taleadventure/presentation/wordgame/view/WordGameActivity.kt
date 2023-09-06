package com.open6.taleadventure.presentation.wordgame.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import coil.load
import com.open6.taleadventure.R
import com.open6.taleadventure.databinding.ActivityWordGameBinding
import com.open6.taleadventure.presentation.base.activity.BaseDataBindingActivity
import com.open6.taleadventure.presentation.end.view.EndActivity
import com.open6.taleadventure.presentation.wordgame.viewmodel.WordGameViewModel
import com.open6.taleadventure.util.PublicString.CHAPTER_NAME
import com.open6.taleadventure.util.PublicString.IS_FROM_CHAPTER
import com.open6.taleadventure.util.PublicString.TALE_NAME
import com.open6.taleadventure.util.extensions.makeGradeSnackbar
import com.open6.taleadventure.util.extensions.makeToastMessage

class WordGameActivity :
    BaseDataBindingActivity<ActivityWordGameBinding>(R.layout.activity_word_game) {
    private val viewModel by viewModels<WordGameViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getData()
        setClickEvents()
        setObservers()
    }

    private fun setObservers() {
        setGetChapterWordsObservers()
    }

    private fun setGetChapterWordsObservers() {
        setGetChapterWordsSuccessObservers()
        setGetChapterWordsFailureObservers()

    }

    private fun setGetChapterWordsSuccessObservers() {
        viewModel.gameWords.observe(this) { successData ->
            if (successData == null) {
                makeToastMessage(getString(R.string.server_error))
                return@observe
            }
            setQuiz(order = 1)
        }
    }

    private fun setQuiz(order: Int) {
        val position = order.minus(1)
        val quizData = viewModel.gameWords.value?.get(position) ?: return
        val (prefix, postfix) = quizData.sentence.split(quizData.name)
        binding.run {
            ivWordGameImage.load(quizData.imageUrl)
            tvWordGameIndicator.text =
                getString(R.string.game_indicator, order, viewModel.maxGameOrder)

            tvWordGamePrefix.text = prefix.trim()
            tvWordGamePostfix.text = postfix.trim()

            tvWordGameFirstChar.text = quizData.example[0].toString()
            tvWordGameSecondChar.text = quizData.example[1].toString()
            tvWordGameThirdChar.text = quizData.example[2].toString()
            tvWordGameFourthChar.text = quizData.example[3].toString()
        }
    }

    private fun setNextQuiz(order: Int) {
        if (order > viewModel.maxGameOrder) {
            Intent(this, EndActivity::class.java).also { intent -> startActivity(intent) }
            if (!isFinishing) finish()
        } else {
            setQuiz(order)
        }
    }

    private fun setGetChapterWordsFailureObservers() {
        viewModel.getChapterWordsErrorResponse.observe(this) { errorMessage ->
            makeToastMessage(errorMessage)
        }
    }

    private fun getData() {
        val isFromChapter = intent.getBooleanExtra(IS_FROM_CHAPTER, false)
        if (isFromChapter) {
            val chapterName = intent.getStringExtra(CHAPTER_NAME)
            if (chapterName != null) viewModel.getChapterWords(chapterName)
        } else {
            val taleName = intent.getStringExtra(TALE_NAME)
            if (taleName != null) viewModel.getChapterWords(taleName)
        }
    }

    private fun setClickEvents() {
        setBackIVClickEvent()
        setCharTVClickEvents()
    }

    private fun setCharTVClickEvents() {
        binding.run {
            tvWordGameFirstChar.setOnClickListener {
                setAnswerChar(tvWordGameFirstChar.text.first())
            }
            tvWordGameSecondChar.setOnClickListener {
                setAnswerChar(tvWordGameSecondChar.text.first())
            }
            tvWordGameThirdChar.setOnClickListener {
                setAnswerChar(tvWordGameThirdChar.text.first())
            }
            tvWordGameFourthChar.setOnClickListener {
                setAnswerChar(tvWordGameFourthChar.text.first())
            }
        }
    }

    private fun setAnswerChar(char: Char) {
        when (viewModel.answer.length) {
            0 -> {
                viewModel.answer = char.toString()
                binding.tvWordGameFirstBlankChar.run {
                    text = char.toString()
                    background = null
                }
            }

            1 -> {
                viewModel.answer = viewModel.answer + char
                binding.tvWordGameSecondBlankChar.run {
                    text = char.toString()
                    background = null
                }
                submitAnswer()
            }
        }
    }

    private fun submitAnswer() {
        if (viewModel.answer == viewModel.gameWords.value?.get(viewModel.currentGameOrder)?.name) {
            viewModel.currentGameOrder = viewModel.currentGameOrder.plus(1)
            binding.root.makeGradeSnackbar(
                isCorrect = true,
                anchorView = binding.ivWordGameImage,
                onDismiss = {
                    resetAnswer()
                    setNextQuiz((viewModel.currentGameOrder))
                })
        } else {
            binding.root.makeGradeSnackbar(
                isCorrect = false,
                anchorView = binding.ivWordGameImage,
                onDismiss = {
                    resetAnswer()
                })
        }
    }

    private fun resetAnswer() {
        binding.tvWordGameFirstBlankChar.run {
            text = ""
            setBackgroundResource(R.drawable.img_char_blank)
        }

        binding.tvWordGameFirstBlankChar.run {
            text = ""
            setBackgroundResource(R.drawable.img_char_blank)
        }
    }

    private fun setBackIVClickEvent() {
        binding.ivWordGameBack.setOnClickListener {
            if (!isFinishing) finish()
        }
    }

    override fun bindViewModelWithBinding() {
        binding.vm = viewModel
    }
}