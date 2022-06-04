package com.polyactiveteam.polyactive.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.polyactiveteam.polyactive.MainActivity
import com.polyactiveteam.polyactive.R
import com.polyactiveteam.polyactive.databinding.FragmentSettingsBinding
import com.polyactiveteam.polyactive.services.SettingsManager

class SettingsFragment : Fragment() {

    lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).supportActionBar?.title = getString(R.string.menu_title_settings)
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
            //activity?.recreate() //Seems there is no need for that
        }
        binding.themeIv2.setOnClickListener {
            SettingsManager.updateTheme(true, view.context)
            //activity?.recreate() //Seems there is no need for that
        }
        super.onViewCreated(view, savedInstanceState)
    }
}
