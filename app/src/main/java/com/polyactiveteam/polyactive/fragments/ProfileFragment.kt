package com.polyactiveteam.polyactive.fragments

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.polyactiveteam.polyactive.MainActivity
import com.polyactiveteam.polyactive.R
import com.polyactiveteam.polyactive.databinding.FragmentProfileBinding
import com.polyactiveteam.polyactive.model.Group
import com.polyactiveteam.polyactive.model.VkGroup
import com.polyactiveteam.polyactive.utils.NetworkUtils
import java.net.URL
import java.util.*

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var gso: GoogleSignInOptions
    private lateinit var gsc: GoogleSignInClient
    private lateinit var preferences: SharedPreferences

    private val user: User = User()

    companion object {
        const val USER_PREFS_FILE_NAME = "UserPrefs"
        const val GROUPS_KEY = "Groups"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as MainActivity).supportActionBar?.title = getString(R.string.menu_title_profile)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (requireActivity() as AppCompatActivity).supportActionBar?.show()
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        gsc = GoogleSignIn.getClient(requireActivity(), gso)

        val account: GoogleSignInAccount? = GoogleSignIn.getLastSignedInAccount(requireActivity())
        if (account != null) {
            val googleFirstName: String? = account.givenName
            val googleLastName: String? = account.familyName
            val googleProfilePicURL: Uri? = account.photoUrl

            if (googleFirstName != null) {
                user.firstName = googleFirstName
            }

            if (googleLastName != null) {
                user.lastName = googleLastName
            }
            if (!NetworkUtils.isInternetAvailable(requireContext())) {
                Toast.makeText(context, "Network fail", Toast.LENGTH_SHORT).show()
            } else {
                if (googleProfilePicURL != null) {
                    Thread {
                        val url = URL(googleProfilePicURL.toString())
                        user.googleProfilePicBitmap =
                            BitmapFactory.decodeStream(url.openConnection().getInputStream())
                        requireActivity().runOnUiThread {
                            binding.icProfile.findViewById<ImageView>(R.id.avatar_image)
                                .setImageBitmap(user.googleProfilePicBitmap)
                        }
                    }.start()
                }
            }
        }

        with(binding) {
            userName.text = user.toString()
            if (user.googleProfilePicBitmap != null) {
                icProfile.findViewById<ImageView>(R.id.avatar_image)
                    .setImageBitmap(user.googleProfilePicBitmap)
            }
            profButton.setOnClickListener {
                switchGroupButtonState(it, VkGroup.PROF)
            }
            adaptersButton.setOnClickListener {
                switchGroupButtonState(it, VkGroup.ADAPTERS)
            }
            brigadesButton.setOnClickListener {
                switchGroupButtonState(it, VkGroup.STUD_BRIGADES)
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonExit.setOnClickListener {
            signOut()
        }
        preferences = view.context.getSharedPreferences(USER_PREFS_FILE_NAME, Context.MODE_PRIVATE)
        preferences.getStringSet(GROUPS_KEY, emptySet())?.map { s: String -> VkGroup.valueOf(s) }
            ?.forEach {
                user.groups.add(it)
            }
        with(binding) {
            resolveGroupButtonState(profButton, VkGroup.PROF)
            resolveGroupButtonState(adaptersButton, VkGroup.ADAPTERS)
            resolveGroupButtonState(brigadesButton, VkGroup.STUD_BRIGADES)
        }
    }

    override fun onStop() {
        super.onStop()
        preferences.edit()
            .putStringSet(GROUPS_KEY, user.groups.map { group -> group.toString() }.toSet()).apply()
    }

    private fun signOut() {
        gsc.signOut()
        findNavController().navigate(R.id.from_profile_to_login)

    }

    private fun resolveGroupButtonState(button: View, group: Group) {
        if (group in user.groups) {
            button.setBackgroundResource(R.drawable.ic_group_button_active)
        }
    }

    private fun switchGroupButtonState(button: View, group: VkGroup) {
        val userGroups = user.groups
        if (group in userGroups) {
            userGroups.remove(group)
            button.setBackgroundResource(R.drawable.ic_group_button_deactive)
        } else {
            userGroups.add(group)
            button.setBackgroundResource(R.drawable.ic_group_button_active)
        }
    }

    private class User {
        var firstName: String = "First Name"
        var lastName: String = "Last Name"
        var googleProfilePicBitmap: Bitmap? = null

        val groups: EnumSet<VkGroup> = EnumSet.noneOf(VkGroup::class.java)

        override fun toString(): String {
            return "$firstName $lastName"
        }
    }

}