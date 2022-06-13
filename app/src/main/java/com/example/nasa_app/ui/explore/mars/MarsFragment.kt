package com.example.nasa_app.ui.explore.mars

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.nasa_app.R
import com.example.nasa_app.databinding.FragmentMarsBinding
import com.example.nasa_app.models.MarsPhotoModel
import com.example.nasa_app.network.api.AppState


class MarsFragment : Fragment() {
    private var _binding: FragmentMarsBinding? = null
    private val binding get() = _binding!!

    private val adapter = MarsPhotosAdapter()

    private val viewModel: MarsPhotosViewModel by lazy {
        ViewModelProvider(this)[MarsPhotosViewModel::class.java]
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMarsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cameras = resources.getStringArray(R.array.cameras)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.camera_dropdown_item, cameras)
        binding.tvCamera.setAdapter(arrayAdapter)
        var camera = ""

        binding.marsRecyclerview.adapter = adapter
        binding.marsRecyclerview.layoutManager = GridLayoutManager(context, 2)

        binding.marsFragmentLoadingLayout.visibility = View.GONE
        binding.emptyView.emptyMessage.visibility = View.GONE
        binding.emptyView.emptyImage.visibility = View.GONE

        // Click listener on dropdown list item selection
        binding.tvCamera.setOnItemClickListener { parent: AdapterView<*>?, view: View?, position: Int, id: Long ->
            when (position) {
                0 -> camera = "FHAZ"
                1 -> camera = "RHAZ"
                2 -> camera = "MAST"
                3 -> camera = "CHEMCAM"
                4 -> camera = "MAHLI"
                5 -> camera = "MARDI"
                6 -> camera = "NAVCAM"
                7 -> camera = "PANCAM"
                8 -> camera = "MINITES"
            }

            Toast.makeText(requireContext(), "$camera was selected", Toast.LENGTH_SHORT).show()

            binding.emptyView.emptyImage.visibility = View.GONE
            binding.emptyView.emptyMessage.visibility = View.GONE
            binding.marsRecyclerview.visibility = View.GONE
            binding.marsFragmentLoadingLayout.visibility = View.VISIBLE

            viewModel.getMarsPhotos(camera).observe(viewLifecycleOwner) {
                if (it != null) renderData(it)
            }
        }
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                binding.marsFragmentLoadingLayout.visibility = View.GONE
                binding.marsRecyclerview.visibility = View.VISIBLE

                adapter.setMarsPhotoList(appState.success as List<MarsPhotoModel>)

                if (adapter.itemCount == 0) {
                    binding.marsRecyclerview.visibility = View.GONE
                    binding.emptyView.emptyImage.visibility = View.VISIBLE
                    binding.emptyView.emptyMessage.visibility = View.VISIBLE
                } else {
                    binding.emptyView.emptyImage.visibility = View.GONE
                    binding.emptyView.emptyMessage.visibility = View.GONE
                    binding.marsRecyclerview.visibility = View.VISIBLE
                }
            }
            is AppState.Loading -> {
                binding.emptyView.emptyImage.visibility = View.GONE
                binding.emptyView.emptyMessage.visibility = View.GONE
                binding.marsRecyclerview.visibility = View.GONE
                binding.marsFragmentLoadingLayout.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                binding.marsFragmentLoadingLayout.visibility = View.GONE
                binding.emptyView.emptyImage.visibility = View.VISIBLE
                binding.emptyView.emptyMessage.visibility = View.VISIBLE
            }
        }
    }
}

