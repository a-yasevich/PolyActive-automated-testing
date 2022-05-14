package com.polyactiveteam.polyactive.model

import com.polyactiveteam.polyactive.R

enum class Group(val id: Int) {
    ALL(R.string.tab_item_groups_all),
    ADAPTERS(R.string.tab_item_adapters),
    BRIGADES(R.string.tab_item_brigades),
    PROF(R.string.tab_item_prof);

    companion object {
        fun groupByID(id: Int) = when (id) {
            R.string.tab_item_adapters -> ADAPTERS
            R.string.tab_item_prof -> PROF
            R.string.tab_item_brigades -> BRIGADES
            else -> ALL
        }
    }
}