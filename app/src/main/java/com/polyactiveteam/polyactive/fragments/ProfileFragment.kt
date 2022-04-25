package com.polyactiveteam.polyactive.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
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
        binding = FragmentProfileBinding.inflate(inflater, container, false)
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonExit.setOnClickListener {
            findNavController().navigate(R.id.from_profile_to_login)
        }
    }

    private fun setColor(it: View, group: Groups) {
        when (user.processGroup(group)) {
            Answer.REMOVE -> {
                it.setBackgroundResource(R.drawable.ic_group_button_deactive)
            }
            else -> it.setBackgroundResource(R.drawable.ic_group_button_active)
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