package com.polyactiveteam.polyactive.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.polyactiveteam.polyactive.R
import com.polyactiveteam.polyactive.databinding.FragmentProfileBinding
import java.util.*

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    companion object { //singleton
        val user = User("Steve", "Rogers")
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater)
        with(binding) {
            userName.text = user.toString()
            profButton.setOnClickListener {
                setColor(it, Groups.PROF)
            }
            adaptersButton.setOnClickListener {
                setColor(it, Groups.ADAPTERS)
            }
            brigadesButton.setOnClickListener {
                setColor(it, Groups.BRIGADES)
            }
        }
        return binding.root
    }

    private fun setColor(it: View, group: Groups) {
        when (user.processGroup(group)) {
            Answer.REMOVE -> {
                it.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.titan_white))
            }
            else -> it.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.forest_green
                )
            )
        }
    }

    class User(var name: String, var lastName: String) {
        private val groups: EnumSet<Groups> = EnumSet.noneOf(Groups::class.java)

        override fun toString(): String {
            return "$name $lastName"
        }

        fun processGroup(group: Groups): Answer {
            if (group in groups) {
                groups.remove(group)
                return Answer.REMOVE
            }
            groups.add(group)
            return Answer.ADD
        }

    }

    enum class Groups {
        ADAPTERS, BRIGADES, PROF
    }

    enum class Answer {
        ADD, REMOVE
    }

}