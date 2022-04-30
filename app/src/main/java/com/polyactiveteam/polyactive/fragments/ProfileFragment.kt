package com.polyactiveteam.polyactive.fragments

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.polyactiveteam.polyactive.R
import com.polyactiveteam.polyactive.databinding.ProfileFragmentBinding
import java.net.URL
import java.util.*


class ProfileFragment : Fragment() {
    private lateinit var binding: ProfileFragmentBinding

    companion object { //singleton
        lateinit var user: User
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val googleFirstName: String? = arguments?.getString("googleFirstName")
        val googleLastName: String? = arguments?.getString("googleLastName")
        val googleProfilePicURL: String? = arguments?.getString("googleProfilePicURL")
        user = User(googleFirstName, googleLastName)
        binding = ProfileFragmentBinding.inflate(inflater)
        with(binding) {
            mainName.text = user.toString()
            firstName.text = googleFirstName
            lastName.text = googleLastName
            if (googleProfilePicURL != null) {
                Thread {
                    val url = URL(googleProfilePicURL)
                    mainImage.findViewById<ImageView>(R.id.avatar_image).setImageBitmap(
                        BitmapFactory.decodeStream(url.openConnection().getInputStream())
                    )
                }.start()
            }
            changeButton.setOnClickListener {
                user.name = firstName.text.toString()
                user.lastName = lastName.text.toString()
                mainName.text = user.toString()
            }
            prof.setOnClickListener {
                setColor(it, Groups.PROF)
            }
            adapters.setOnClickListener {
                setColor(it, Groups.ADAPTERS)
            }
            brigades.setOnClickListener {
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

    class User(var name: String?, var lastName: String?) {

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