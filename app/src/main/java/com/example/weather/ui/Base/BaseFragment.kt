package com.example.weather.ui.Base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<VB : ViewBinding> : Fragment() {
    private var _binding: VB? = null
    val binding get() = _binding

    /**
     * A lambda expression used to inflate the ViewBinding associated with this Fragment.
     * This should be implemented in derived classes.
     */
    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB

    /**
     * A tag used for logging purposes. Should be defined in derived classes.
     */
    abstract val logTag: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindingInflater(inflater, container, false)
        return requireNotNull(_binding).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addCallbacks()
        setup()
    }

    /**
     * Abstract method that should be implemented in derived classes to add callbacks or listeners.
     */
    abstract fun addCallbacks()
    abstract fun setup()

    /**
     * Helper method for logging messages with the specified tag.
     *
     * @param value The message or value to be logged.
     */
    fun log(value: Any) = Log.v(logTag, value.toString())

    /**
     * Called when the Fragment's View is destroyed.
     * It clears the ViewBinding to avoid memory leaks.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}