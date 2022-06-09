package com.polyactiveteam.polyactive.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.polyactiveteam.polyactive.R
import com.polyactiveteam.polyactive.databinding.FragmentLoginBinding
import com.polyactiveteam.polyactive.databinding.FragmentProfileBinding
import com.polyactiveteam.polyactive.utils.NetworkUtils

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var profileBinding: FragmentProfileBinding
    private lateinit var gso: GoogleSignInOptions
    private lateinit var gsc: GoogleSignInClient

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        profileBinding = FragmentProfileBinding.inflate(inflater)
        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        gsc = GoogleSignIn.getClient(requireActivity(), gso)
        val account: GoogleSignInAccount? = GoogleSignIn.getLastSignedInAccount(requireContext())
        if (account != null) {
            findNavController().navigate(R.id.from_login_to_feed)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.googleSignInButton.setOnClickListener {
            signInWithGoogle()
        }
    }

    private fun signInWithGoogle() {
        if (!NetworkUtils.isInternetAvailable(requireContext())) {
            Toast.makeText(context, "Network fail", Toast.LENGTH_SHORT).show()
        }
        val signIntent: Intent = gsc.signInIntent
        startActivityForResult(signIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account: GoogleSignInAccount? = task.getResult(ApiException::class.java)
                if (account != null) {
                    Toast.makeText(context, "Sign in success", Toast.LENGTH_SHORT).show()
                    val bundle = Bundle()
                    val googleFirstName: String? = account.givenName
                    val googleLastName: String? = account.familyName
                    val googleProfilePicURL: Uri? = account.photoUrl
                    if (googleFirstName != null) {
                        bundle.putString("googleFirstName", googleFirstName.toString())
                    }
                    if (googleLastName != null) {
                        bundle.putString("googleLastName", googleLastName.toString())
                    }
                    if (googleProfilePicURL != null) {
                        bundle.putString("googleProfilePicURL", googleProfilePicURL.toString())
                    }
                    findNavController().navigate(R.id.from_login_to_feed, bundle)
                }
            } catch (e: ApiException) {
                Toast.makeText(
                    context,
                    "Sign in failed" + e.statusCode.toString(),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    companion object {
        private const val RC_SIGN_IN = 1
    }
}