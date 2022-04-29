package com.polyactiveteam.polyactive.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.polyactiveteam.polyactive.databinding.FragmentSettingsBinding
import com.polyactiveteam.polyactive.services.SettingsManager

class SettingsFragment : Fragment() {
    lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //Переместить строчку ниже в fragment_profile
        //чтобы при переходе с navigation_graph ActionBar был виден
        (requireActivity() as AppCompatActivity).supportActionBar?.show()
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
        // Inflate the layout for this fragment
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.languageIv1.setOnClickListener {
            SettingsManager.updateLanguage("ru", view.context)
            activity?.recreate()
        }
        binding.languageIv2.setOnClickListener {
            SettingsManager.updateLanguage("en", view.context)
            activity?.recreate()
        }
        binding.themeIv1.setOnClickListener {
            SettingsManager.updateTheme(false, view.context)
            activity?.recreate()
        }
        binding.themeIv2.setOnClickListener {
            SettingsManager.updateTheme(true, view.context)
            activity?.recreate()
        }
        super.onViewCreated(view, savedInstanceState)
    }
}
