package com.example.viewcore.fragment

import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.example.viewcore.R
import com.example.viewcore.model.ToolbarData

abstract class BaseFragment : Fragment(), ErrorDialogFragment.PositiveClickListener {

    abstract val toolbarData: ToolbarData

    protected fun showErrorDialog(
        @StringRes titleId: Int = R.string.error_dialog_title,
        @StringRes messageId: Int = R.string.error_dialog_message,
        @StringRes buttonNameId: Int = R.string.error_dialog_button_name,
        @StringRes exitNameId: Int = R.string.error_dialog_exit_name
    ) {
        ErrorDialogFragment.showDialog(titleId, messageId, buttonNameId, exitNameId, parentFragmentManager)
    }

    abstract override fun onErrorDialogPositiveClick()
}