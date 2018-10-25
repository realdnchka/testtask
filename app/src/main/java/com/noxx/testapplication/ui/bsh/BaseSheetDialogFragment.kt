package com.noxx.testapplication.ui.bsh

import android.app.Dialog
import android.os.Bundle
import android.support.annotation.Nullable
import android.support.design.widget.BottomSheetBehavior
import android.support.design.widget.BottomSheetDialog
import android.support.design.widget.BottomSheetDialogFragment
import android.support.v4.app.FragmentManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.noxx.testapplication.R


abstract class BaseSheetDialogFragment : BottomSheetDialogFragment() {

    abstract fun getLayoutId(): Int

    abstract fun initUi(view: View)

    override fun getTheme(): Int = R.style.BottomSheetDialogTheme

    @Nullable
    override fun onCreateView(inflater: LayoutInflater,
                              @Nullable container: ViewGroup?,
                              @Nullable savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayoutId(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi(view)
        view.post {
            val dialog = dialog as BottomSheetDialog
            val bottomSheet = dialog.findViewById<View>(android.support.design.R.id.design_bottom_sheet) as FrameLayout
            val behavior = BottomSheetBehavior.from(bottomSheet)
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
            behavior.peekHeight = 0
            behavior.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onSlide(bottomSheet: View, slideOffset: Float) {

                }

                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    when (newState) {
                        BottomSheetBehavior.STATE_HIDDEN,
                        BottomSheetBehavior.STATE_COLLAPSED -> {
                            dialog.dismiss()
                        }
                    }
                }
            })
        }
    }

    override fun show(manager: FragmentManager?, tag: String?) {
        try {
            if (manager != null) {
                val ft = manager.beginTransaction()
                ft.add(this, tag)
                ft.commitAllowingStateLoss()
            }
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        }

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(requireContext(), theme)
        return dialog
    }

    companion object {
        const val ARG_OWNER = "ARG_OWNER"
        const val ARG_TAG = "ARG_TAG"

        const val BUTTON_POSITIVE = 0
        const val BUTTON_NEGATIVE = 1
        const val BUTTON_NEUTRAL = 2

    }

}