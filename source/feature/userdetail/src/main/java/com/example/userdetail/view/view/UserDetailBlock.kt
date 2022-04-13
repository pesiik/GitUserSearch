package com.example.userdetail.view.view

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.example.userdetail.R
import com.example.userdetail.view.model.UserDetailBlockModel


class UserDetailBlock @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        View.inflate(context, R.layout.user_detail_block, this)
    }

    fun populate(model: UserDetailBlockModel) {
        with(model) {
            val leftImageView = findViewById<ImageView>(R.id.leftImage)
            val mainInfoView = findViewById<TextView>(R.id.mainInfo)
            val subInfoView = findViewById<TextView>(R.id.subInfo)
            val divider = findViewById<View>(R.id.divider)
            leftImageView?.setImageDrawable(leftDrawable)
            mainInfoView?.text = mainInfo
            subInfoView?.text = subInfo
            divider?.isVisible = showDivider
            setupClipboard(this)
        }
    }

    private fun setupClipboard(model: UserDetailBlockModel) {
        with(model) {
            val rightImage = findViewById<ImageView>(R.id.rightImage)
            rightImage.isVisible = canCopy
            if (canCopy) {
                setOnLongClickListener {
                    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?
                    val clip = ClipData.newPlainText(mainInfo, mainInfo)
                    clipboard?.setPrimaryClip(clip)
                    val toastText = resources.getString(R.string.user_detail_copy_clipboard_text, subInfo)
                    Toast.makeText(context, toastText, Toast.LENGTH_LONG).show()
                    true
                }
            }
        }
    }
}