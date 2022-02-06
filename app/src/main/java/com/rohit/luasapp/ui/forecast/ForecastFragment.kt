package com.rohit.luasapp.ui.forecast

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.rohit.luasapp.databinding.MainFragmentBinding
import com.rohit.luasapp.model.StopInfo
import com.rohit.luasapp.util.Response
import dagger.hilt.android.AndroidEntryPoint

/**
 * This is the first Fragment that is loaded by the MainActivity.  It implements the @AndroidEntryPoint
 * annotation in order to receive the dependencies.
 */
@AndroidEntryPoint
class ForecastFragment : Fragment() {

    companion object {
        /**
         * Creates an entry point to generate a new instance of the ForecastFragment.
         */
        fun newInstance() = ForecastFragment()
    }

    private val viewModel: ForecastViewModel by viewModels()
    private lateinit var binding: MainFragmentBinding
    private val tramsAdapter: TramAdapter = TramAdapter()

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

    /**
     * Function responsible to set up the observers to watch the LiveData updates.
     */
    private fun setupObservers() {
        /**
         * Observes the LiveData<Response<StopInfo>> from the ForecastViewModel. Any time this object updates,
         * the code within the lambda will be executed with the most current data.
         */
        viewModel.currentForecast.observe(viewLifecycleOwner, Observer { it ->
            // Loading status is set to false once the new data is received
            binding.isLoading = false

            // Check the status of the last response received
            when (it.status) {
                // Update the loading binding variable to true if the data is being loaded
                Response.Status.LOADING -> binding.isLoading = true

                Response.Status.SUCCESS -> {
                    // Update the forecast binding variable with the last data received from the server
                    binding.forecast = it.data

                    // If the data received contains trams for the line, update the adapter with the new data
                    if (!it.data?.lines.isNullOrEmpty()) tramsAdapter.setItems((it.data as StopInfo).lines[0].trams)
                }

                // In case of an error, display a Toast informing the user about the error
                Response.Status.ERROR -> Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
            }
        })
    }

    /**
     * Function responsible to set up the binding variables or views to the Fragment.
     */
    private fun setupBindings() {
        binding.adapter = tramsAdapter
        binding.refreshButton.setOnClickListener { viewModel.refreshForecast() }
    }

}