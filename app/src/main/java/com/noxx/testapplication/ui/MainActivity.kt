package com.noxx.testapplication.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.noxx.testapplication.R
import com.noxx.testapplication.eventbus.MessageDialogButtonEvent
import com.noxx.testapplication.ui.bsh.MessageDialogFragment
import com.noxx.testapplication.ui.bsh.MessageDialogTags
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EventBus.getDefault().register(this)
        setContentView(R.layout.activity_main)

        btnLogin.setOnClickListener {
            when {
                etLogin.text.isEmpty() || etPassword.text.isEmpty() -> {
                    showMessageDialogOk("Необходимо ввести логин и пароль")
                }
                etLogin.text.toString() == "autotest" && etPassword.text.toString() == "autotest" -> {
                    showMessageDialogOk("Успешный вход в приложение")
                }
                else -> {
                    showMessageDialogOk("Логин или пароль введен неверно")
                }
            }
        }

    }

    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        super.onDestroy()
    }

    /**
     * Реагирует на событие клика на кнопку в диалоге
     *
     * @param event
     * @Subscribe(threadMode = ThreadMode.MAIN)
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageDialogButtonClickedEvent(event: MessageDialogButtonEvent?) {
        if (event != null) {
            if (event.owner == getOwnerTag()) {
                // lol nothing to do
            }
        }
    }

    /**
     * Показать bottomsheet c текстовым сообщением
     */
    fun showMessageDialogOk(message: String, tag: String = MessageDialogTags.TAG_DIALOG_OK) {
        MessageDialogFragment.newInstance(
            getOwnerTag(),
            tag,
            message,
            getString(R.string.ok)
        ).show(supportFragmentManager, message)
    }

    private fun getOwnerTag(): String {
        return "MainActivity"
    }

}
