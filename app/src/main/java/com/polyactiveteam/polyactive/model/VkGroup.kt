package com.polyactiveteam.polyactive.model

import com.polyactiveteam.polyactive.R

enum class VkGroup(private val id: Int, val stringResId: Int) : Group {
    ADAPTERS(87987944, R.string.adapters_name),
    PROF(98426, R.string.prof_name),
    STUD_BRIGADES(12305069, R.string.students_brigades_name),
    GROUP_ALL(-1, R.string.tab_item_groups_all);

    override fun getId(): Int {
        return this.id
    }

    companion object {
        fun groupByID(id: Int): VkGroup = when (id) {
            ADAPTERS.stringResId -> ADAPTERS
            PROF.stringResId -> PROF
            STUD_BRIGADES.stringResId -> STUD_BRIGADES
            else -> GROUP_ALL
        }
    }
}