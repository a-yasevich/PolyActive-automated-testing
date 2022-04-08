package com.polyactiveteam.polyactive.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.polyactiveteam.polyactive.databinding.ProfileFragmentBinding

class ProfileFragment : Fragment() {
    private lateinit var binding: ProfileFragmentBinding
    private lateinit var user: User


    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ProfileFragmentBinding.inflate(inflater)
        user = User("Steve", "Rogers")
        with(binding) {
            mainName.text = user.toString()
            changeButton.setOnClickListener {
                user.name = setName.text.toString()
                user.lastName = setLastName.text.toString()
                mainName.text = user.toString()
            }
        }
        return binding.root
    }


    private class User(var name: String, var lastName: String) {
        override fun toString(): String {
            return "$name $lastName"
        }
    }
}