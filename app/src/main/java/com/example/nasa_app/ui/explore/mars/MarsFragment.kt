package com.example.nasa_app.ui.explore.mars

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
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

        val cameras = resources.getStringArray(R.array.cameras)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.camera_dropdown_item, cameras)
        binding.tvCamera.setAdapter(arrayAdapter)

        binding.marsRecyclerview.adapter = adapter
        binding.marsRecyclerview.layoutManager = GridLayoutManager(context, 2)

        val camera = "FHAZ"

        viewModel.getMarsPhotos(camera).observe(viewLifecycleOwner) {
            if (it != null) renderData(it)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                binding.marsFragmentLoadingLayout.visibility = View.GONE
                adapter.setMarsPhotoList(appState.success as List<MarsPhotoModel>)
            }
            is AppState.Loading -> {
                binding.marsFragmentLoadingLayout.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                binding.marsFragmentLoadingLayout.visibility = View.GONE
                Toast.makeText(requireContext(), "Oops something went wrong with loading pictures from Mars...", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

