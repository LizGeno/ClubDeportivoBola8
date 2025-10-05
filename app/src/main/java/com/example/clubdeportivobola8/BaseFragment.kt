package com.example.clubdeportivobola8

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.google.android.material.appbar.MaterialToolbar
import kotlin.let

// En BaseFragment.kt
abstract class BaseFragment<T : ViewBinding> : Fragment() {

    private var _binding: T? = null
    val binding get() = _binding!!

    protected abstract fun getToolbarFromBaseBinding(fragmentBinding: T): MaterialToolbar?

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflateBinding(inflater, container)
        setupNavigation()
        return binding.root
    }

    abstract fun inflateBinding(inflater: LayoutInflater, container: ViewGroup?): T

    private fun setupNavigation() {
        val toolbar = getToolbarFromBaseBinding(binding)
        toolbar?.let {
            if (shouldShowBackButton()) {
                it.setNavigationIcon(R.drawable.ic_arrow_back)
                it.setNavigationOnClickListener {
                    activity?.onBackPressedDispatcher?.onBackPressed()
                }
            } else {
                it.navigationIcon = null // Oculta el icono
            }
            // Permite que los fragmentos configuren el título, etc.
            configureToolbar(it)
        }
    }

    // Los fragmentos hijos pueden sobrescribir esto
    protected open fun shouldShowBackButton(): Boolean = true

    // Los fragmentos hijos pueden sobrescribir esto para personalizar el título, menú, etc.
    protected open fun configureToolbar(toolbar: MaterialToolbar) {
        toolbar.title = "" // Título por defecto o vacío
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

// En LoginFragment.kt

