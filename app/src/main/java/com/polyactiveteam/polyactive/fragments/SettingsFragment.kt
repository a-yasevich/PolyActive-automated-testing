package com.polyactiveteam.polyactive.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.polyactiveteam.polyactive.MainActivity
import com.polyactiveteam.polyactive.R
import com.polyactiveteam.polyactive.databinding.FragmentSettingsBinding
import com.polyactiveteam.polyactive.services.SettingsManager

class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding
    private lateinit var preferences: SharedPreferences

    companion object {
        const val KEY_LANGUAGE = "language"
        const val KEY_THEME = "theme"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (requireActivity() as AppCompatActivity).supportActionBar?.show()
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).supportActionBar?.title = getString(R.string.menu_title_settings)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val spinnerLanguage = binding.spinnerLanguage
        val adapterLanguage = activity?.let {
            ArrayAdapter(
                it,
                android.R.layout.simple_spinner_dropdown_item,
                resources.getStringArray(R.array.languages)
            )
        }
        spinnerLanguage.adapter = adapterLanguage
        preferences =
            view.context.getSharedPreferences(SettingsFragment.toString(), Context.MODE_PRIVATE)
        spinnerLanguage.setSelection(loadInt(KEY_LANGUAGE))

        spinnerLanguage.post {
            spinnerLanguage.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    SettingsManager.updateLanguage(if (p2 == 0) "ru" else "en", view.context)
                    if (p2 == 0) {
                        saveInt(KEY_LANGUAGE, 0)
                    } else {
                        saveInt(KEY_LANGUAGE, 1)
                    }
                    activity?.recreate()
                    Toast.makeText(
                        context,
                        resources.getString(R.string.chooseLanguage),
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }
            }
        }

        val spinnerTheme = binding.spinnerTheme
        val adapterTheme = activity?.let {
            ArrayAdapter(
                it,
                android.R.layout.simple_spinner_dropdown_item,
                resources.getStringArray(R.array.theme)
            )
        }
        spinnerTheme.adapter = adapterTheme
        spinnerTheme.setSelection(loadInt(KEY_THEME))

        spinnerTheme.post {
            spinnerTheme.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    SettingsManager.updateTheme(p2 != 0, view.context)
                    if (p2 == 0) {
                        saveInt(KEY_THEME, 0)
                    } else {
                        saveInt(KEY_THEME, 1)
                    }
                    activity?.recreate()
                    Toast.makeText(
                        context,
                        resources.getString(R.string.chooseTheme) + " "
                                + resources.getStringArray(R.array.theme)[p2],
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }
            }
        }
        super.onViewCreated(view, savedInstanceState)
    }

    fun saveInt(key: String?, value: Int) {
        val editor: SharedPreferences.Editor = preferences.edit()
        editor.putInt(key, value)
        editor.apply()
    }

    private fun loadInt(key: String?): Int {
        return if (preferences.contains(key)) {
            preferences.getInt(key, 0)
        } else {
            0
        }
    }
}