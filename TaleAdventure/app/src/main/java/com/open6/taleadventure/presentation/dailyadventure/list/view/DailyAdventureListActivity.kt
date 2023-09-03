package com.open6.taleadventure.presentation.dailyadventure.list.view

import android.os.Bundle
import android.view.LayoutInflater
import com.open6.taleadventure.databinding.ActivityDailyAdventureListBinding
import com.open6.taleadventure.presentation.base.activity.BaseViewBindingActivity
import com.open6.taleadventure.presentation.dailyadventure.list.adapter.DailyAdventureListAdapter

class DailyAdventureListActivity : BaseViewBindingActivity<ActivityDailyAdventureListBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setViews()
        setClickEvents()
    }

    private fun setClickEvents() {
        setBackIVClickEvent()
    }

    private fun setBackIVClickEvent() {
        binding.ivDailyAdventureListBack.setOnClickListener {
            if (!isFinishing) finish()
        }
    }

    private fun setViews() {
        setDailyAdventureListAdapter()
    }

    private fun setDailyAdventureListAdapter() {
        binding.rvDailyAdventureList.adapter = DailyAdventureListAdapter()
    }

    override fun setBinding(inflater: LayoutInflater): ActivityDailyAdventureListBinding =
        ActivityDailyAdventureListBinding.inflate(layoutInflater)
}