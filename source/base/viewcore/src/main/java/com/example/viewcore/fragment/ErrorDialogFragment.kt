package com.example.viewcore.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.example.viewcore.R

private const val TITLE_ID_ARG = "TITLE_ID_ARG"
private const val MESSAGE_ID_ARG = "MESSAGE_ID_ARG"
private const val BUTTON_NAME_ID_ARG = "BUTTON_NAME_ID_ARG"
private const val EXIT_NAME_ID_ARG = "EXIT_NAME_ID_ARG"
private const val ERROR_DIALOG_TAG = "ERROR_DIALOG_TAG"

class ErrorDialogFragment : DialogFragment() {

    private val titleId by lazy { requireArguments().getInt(TITLE_ID_ARG) }
    private val messageId by lazy { requireArguments().getInt(MESSAGE_ID_ARG) }
    private val buttonNameId by lazy { requireArguments().getInt(BUTTON_NAME_ID_ARG) }
    private val exitNameId by lazy { requireArguments().getInt(EXIT_NAME_ID_ARG) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.error_dialog_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind()
        requireDialog().setCancelable(false)
    }

    private fun bind() {
        val mainView = requireView()
        val title = mainView.findViewById<TextView>(R.id.errorDialogTitle)
        val message = mainView.findViewById<TextView>(R.id.errorDialogMessage)
        val positiveButton = mainView.findViewById<TextView>(R.id.errorDialogPositive)
        val negativeButton = mainView.findViewById<TextView>(R.id.errorDialogNegative)
        title.setText(titleId)
        message.setText(messageId)
        positiveButton.setText(buttonNameId)
        negativeButton.setText(exitNameId)
        positiveButton.setOnClickListener {
            val navHostFragment = requireParentFragment()
            val baseFragment = navHostFragment.childFragmentManager.fragments.first { fragment ->
                fragment is BaseFragment
            } as BaseFragment
            baseFragment.onErrorDialogPositiveClick()
            dismiss()
        }
        negativeButton.setOnClickListener {
            requireActivity().finish()
        }
    }

    interface PositiveClickListener {
        fun onErrorDialogPositiveClick()
    }

    companion object {
        fun showDialog(
            @StringRes titleId: Int,
            @StringRes messageId: Int,
            @StringRes buttonNameId: Int,
            @StringRes exitNameId: Int,
            fragmentManager: FragmentManager
        ) {
            ErrorDialogFragment().apply {
                arguments = Bundle().apply {
                    putInt(TITLE_ID_ARG, titleId)
                    putInt(MESSAGE_ID_ARG, messageId)
                    putInt(BUTTON_NAME_ID_ARG, buttonNameId)
                    putInt(EXIT_NAME_ID_ARG, exitNameId)
                }
                show(fragmentManager, ERROR_DIALOG_TAG)
            }
        }
    }
}