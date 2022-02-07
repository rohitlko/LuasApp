package com.rohit.luasapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.rohit.luasapp.databinding.MainFragmentBinding
import com.rohit.luasapp.data.StopInfo
import com.rohit.luasapp.api.ApiResponse
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LuasFragment : Fragment() {

    companion object {
        fun newInstance() = LuasFragment()
    }

    private val viewModel: LuasViewModel by viewModels()
    private lateinit var binding: MainFragmentBinding
    private val tramsAdapter: LuasAdapter = LuasAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.main
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.initialize()
        viewModel.refreshForecast()

        setupBindings()
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.currentForecast.observe(viewLifecycleOwner, Observer { it ->
            when (it.status) {

                ApiResponse.Status.SUCCESS -> {
                    binding.forecast = it.data
                    if (!it.data?.lines.isNullOrEmpty()) tramsAdapter.setItems((it.data as StopInfo).lines[0].trams)
                }
                ApiResponse.Status.ERROR -> Toast.makeText(context, it.message, Toast.LENGTH_LONG)
                    .show()
            }
        })
    }

    private fun setupBindings() {
        binding.adapter = tramsAdapter
    }

}