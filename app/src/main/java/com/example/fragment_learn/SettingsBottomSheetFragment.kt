package com.example.fragment_learn

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.button.MaterialButton

class SettingsBottomSheetFragment : BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val title = view.findViewById<TextView>(R.id.title)
        val switch = view.findViewById<SwitchCompat>(R.id.switch_option)
        val closeButton = view.findViewById<MaterialButton>(R.id.btn_close)

        // 入场动画
        title.alpha = 0f
        switch.alpha = 0f
        closeButton.alpha = 0f

        title.animate().alpha(1f).setDuration(300).start()
        switch.animate().alpha(1f).setDuration(300).setStartDelay(100).start()
        closeButton.animate().alpha(1f).setDuration(300).setStartDelay(200).start()

        // 点击关闭
        closeButton.setOnClickListener {
            dismiss()
        }

        // 开关状态变化
        switch.setOnCheckedChangeListener { _, isChecked ->
            // 处理开关逻辑
        }
    }
}