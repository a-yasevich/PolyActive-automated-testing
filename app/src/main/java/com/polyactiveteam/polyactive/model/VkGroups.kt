package com.polyactiveteam.polyactive.model

enum class VkGroups(private val id: Int) : Group {
    ADAPTERS(87987944), PROF(98426), STUD_BRIGADES(12305069);

    override fun getId(): Int {
        return this.id
    }
}