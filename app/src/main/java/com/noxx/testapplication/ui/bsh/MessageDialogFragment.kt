package com.noxx.testapplication.ui.bsh

import android.os.Bundle
import android.support.annotation.DrawableRes
import android.support.v4.content.ContextCompat
import android.view.View
import com.noxx.testapplication.R
import com.noxx.testapplication.eventbus.MessageDialogButtonEvent
import com.noxx.testapplication.utils.DisplayUtils
import kotlinx.android.synthetic.main.bottom_message.view.*
import org.greenrobot.eventbus.EventBus


class MessageDialogFragment : BaseSheetDialogFragment() {

    override fun getLayoutId(): Int {
        return R.layout.bottom_message
    }

    override fun initUi(view: View) {
        arguments?.let { args ->
            view.message.text = args.getString(ARG_MESSAGE, "")
            val imageRes = args.getInt(ARG_IMAGE_RES, -1)
            val owner: String = args.getString(ARG_OWNER, "")
            val tag: String = args.getString(ARG_TAG, "")
            val positiveText: String? = args.getString(ARG_POSITIVE_TEXT)
            val negativeText: String? = args.getString(ARG_NEGATIVE_TEXT)
            val neutralText: String? = args.getString(ARG_NEUTRAL_TEXT)
            val titleValue: String? = args.getString(ARG_TITLE_TEXT)
            val settingsKey: String? = args.getString(ARG_SETTINGS_KEY)

            if (imageRes != -1) {
                view.image.visibility = View.VISIBLE
                view.image.setImageDrawable(ContextCompat.getDrawable(view.image.context, imageRes))
            } else {
                view.image.visibility = View.GONE
            }

            if (positiveText != null) {
                view.positive.visibility = View.VISIBLE
                view.positive.text = positiveText
                view.positive.setOnClickListener {
                    EventBus.getDefault().post(
                        MessageDialogButtonEvent(
                            owner, tag,
                            BUTTON_POSITIVE, args
                        )
                    )
                    dismissAllowingStateLoss()
                }
            }
            if (negativeText != null) {
                view.negative.visibility = View.VISIBLE
                view.negative.text = negativeText
                view.negative.setOnClickListener {
                    EventBus.getDefault().post(
                        MessageDialogButtonEvent(
                            owner, tag,
                            BUTTON_NEGATIVE, args
                        )
                    )
                    dismissAllowingStateLoss()
                }
            }
            if (neutralText != null) {
                view.neutral.visibility = View.VISIBLE
                view.neutral.text = neutralText
                view.neutral.setOnClickListener {
                    EventBus.getDefault().post(
                        MessageDialogButtonEvent(
                            owner, tag,
                            BUTTON_NEUTRAL, args
                        )
                    )
                    dismissAllowingStateLoss()
                }
            }
            if (positiveText == null && negativeText == null && neutralText == null) {
                view.message.setPadding(0, 0, 0, DisplayUtils.dp2px(view.message.context, 24f))
                view.message.requestLayout()
            }
            if (settingsKey != null) {
                view.dontShow.visibility = View.VISIBLE
                view.dontShow.setOnCheckedChangeListener { _, _ ->

                }
            }
            if (titleValue != null) {
                view.title.text = titleValue
                view.title.visibility = View.VISIBLE
            }
        }

        if (arguments == null) {
            dismissAllowingStateLoss()
        }
    }

    companion object {


        const val ARG_MESSAGE = "ARG_MESSAGE"
        const val ARG_POSITIVE_TEXT = "ARG_POSITIVE_TEXT"
        const val ARG_NEGATIVE_TEXT = "ARG_NEGATIVE_TEXT"
        const val ARG_NEUTRAL_TEXT = "ARG_NEUTRAL_TEXT"
        const val ARG_TITLE_TEXT = "ARG_TITLE_TEXT"
        const val ARG_SETTINGS_KEY = "ARG_SETTINGS_KEY"
        const val ARG_IMAGE_RES = "ARG_IMAGE_RES"
        //


        fun getArgsBundle(
            owner: String,
            tag: String,
            message: String,
            positiveText: String,
            negativeText: String? = null,
            neutralText: String? = null,
            settingsKey: String? = null,
            title: String? = null
        ): Bundle {
            return Bundle().apply {
                putString(ARG_OWNER, owner)
                putString(ARG_TAG, tag)
                putString(ARG_MESSAGE, message)
                putString(ARG_POSITIVE_TEXT, positiveText)
                if (negativeText != null) putString(ARG_NEGATIVE_TEXT, negativeText)
                if (neutralText != null) putString(ARG_NEUTRAL_TEXT, neutralText)
                if (settingsKey != null) putString(ARG_SETTINGS_KEY, settingsKey)
                if (title != null) putString(ARG_TITLE_TEXT, title)
            }
        }

        fun newInstance(args: Bundle): MessageDialogFragment {
            val dialog = MessageDialogFragment()
            dialog.arguments = args
            return dialog
        }

        fun newInstance(
            owner: String,
            tag: String,
            message: String
        ): MessageDialogFragment {
            val args = Bundle()
            args.apply {
                putString(ARG_OWNER, owner)
                putString(ARG_TAG, tag)
                putString(ARG_MESSAGE, message)
            }
            val dialog = MessageDialogFragment()
            dialog.arguments = args
            return dialog
        }

        fun newInstance(
            owner: String,
            tag: String,
            message: String,
            @DrawableRes imageRes: Int,
            positiveText: String
        ): MessageDialogFragment {
            val args = Bundle()
            args.apply {
                putString(ARG_OWNER, owner)
                putString(ARG_TAG, tag)
                putString(ARG_MESSAGE, message)
                putInt(ARG_IMAGE_RES, imageRes)
                putString(ARG_POSITIVE_TEXT, positiveText)
            }
            val dialog = MessageDialogFragment()
            dialog.arguments = args
            return dialog
        }

        fun newInstance(
            owner: String,
            tag: String,
            message: String,
            positiveText: String,
            negativeText: String? = null,
            neutralText: String? = null,
            settingsKey: String? = null,
            title: String? = null
        ): MessageDialogFragment {
            val args = Bundle()
            args.apply {
                putString(ARG_OWNER, owner)
                putString(ARG_TAG, tag)
                putString(ARG_MESSAGE, message)
                putString(ARG_POSITIVE_TEXT, positiveText)
                if (negativeText != null) putString(ARG_NEGATIVE_TEXT, negativeText)
                if (neutralText != null) putString(ARG_NEUTRAL_TEXT, neutralText)
                if (settingsKey != null) putString(ARG_SETTINGS_KEY, settingsKey)
                if (title != null) putString(ARG_TITLE_TEXT, title)
            }
            val dialog = MessageDialogFragment()
            dialog.arguments = args
            return dialog
        }
    }

}
