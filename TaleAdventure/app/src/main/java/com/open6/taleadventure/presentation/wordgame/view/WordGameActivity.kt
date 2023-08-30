package com.open6.taleadventure.presentation.wordgame.view

import android.os.Bundle
import androidx.activity.viewModels
import com.open6.taleadventure.R
import com.open6.taleadventure.databinding.ActivityWordGameBinding
import com.open6.taleadventure.presentation.base.BaseDataBindingActivity
import com.open6.taleadventure.presentation.wordgame.viewmodel.WordGameViewModel

class WordGameActivity :
    BaseDataBindingActivity<ActivityWordGameBinding>(R.layout.activity_word_game) {
    private val viewModel by viewModels<WordGameViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun bindViewModelWithBinding() {
        binding.vm = viewModel
    }
}