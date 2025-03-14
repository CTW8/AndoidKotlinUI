package com.example.fragment_learn

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment

class SettingsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val showBottomSheetButton = view.findViewById<Button>(R.id.btn_show_bottom_sheet)
        showBottomSheetButton.setOnClickListener {
            // 弹出半屏
            val bottomSheet = SettingsBottomSheetFragment()
            bottomSheet.show(parentFragmentManager, "SettingsBottomSheet")
        }
    }
}