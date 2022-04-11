package com.example.viewcore.fragment

import androidx.fragment.app.Fragment
import com.example.viewcore.model.ToolbarData

abstract class BaseFragment : Fragment() {
    abstract val toolbarData: ToolbarData
}