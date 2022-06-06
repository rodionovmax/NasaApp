package com.example.nasa_app.ui.explore.mars

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.example.nasa_app.R
import com.example.nasa_app.databinding.FragmentMarsBinding

class MarsFragment : Fragment() {
    private var _binding: FragmentMarsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMarsBinding.inflate(inflater, container, false)

        val cameras = resources.getStringArray(R.array.cameras)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.camera_dropdown_item, cameras)
        binding.tvCamera.setAdapter(arrayAdapter)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




    }
}
