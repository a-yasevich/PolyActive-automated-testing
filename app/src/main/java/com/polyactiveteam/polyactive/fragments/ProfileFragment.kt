package com.polyactiveteam.polyactive.fragments

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.polyactiveteam.polyactive.R
import com.polyactiveteam.polyactive.databinding.FragmentProfileBinding
import java.net.URL
import java.util.*

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    companion object { //singleton
        val user: User = User()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        val googleFirstName: String? = arguments?.getString("googleFirstName")
        val googleLastName: String? = arguments?.getString("googleLastName")
        val googleProfilePicURL: String? = arguments?.getString("googleProfilePicURL")
        if (googleFirstName != null ) {
            user.firstName = googleFirstName
        }
        if (googleLastName != null ) {
            user.lastName = googleLastName
        }
        if (googleProfilePicURL != null) {
            Thread {
                val url = URL(googleProfilePicURL)
                user.googleProfilePicBitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream())
                requireActivity().runOnUiThread {
                    binding.icProfile.findViewById<ImageView>(R.id.avatar_image).setImageBitmap(user.googleProfilePicBitmap)
                }
            }.start()
        }
        with(binding) {
            userName.text = user.toString()
            if (user.googleProfilePicBitmap != null) {
                icProfile.findViewById<ImageView>(R.id.avatar_image).setImageBitmap(user.googleProfilePicBitmap)
            }
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
            else -> it.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.forest_green
                )
            )
        }
    }

    class User {
        var firstName: String = "First Name"
        var lastName: String= "Last Name"
        var googleProfilePicBitmap: Bitmap? = null

        private val groups: EnumSet<Groups> = EnumSet.noneOf(Groups::class.java)

        override fun toString(): String {
            return "$firstName $lastName"
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