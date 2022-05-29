package com.example.nasa_app.ui.chips

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.nasa_app.databinding.FragmentChipsBinding
import com.example.nasa_app.util.showToast
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.fragment_chips.*

class ChipsFragment : Fragment() {

    private var _binding: FragmentChipsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChipsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        chip_group.setOnCheckedChangeListener { chipGroup, position ->
            chipGroup.findViewById<Chip>(position)?.let {
                showToast(requireContext(), "Выбран ${it.text}")
            }
        }

        chip_close.setOnCloseIconClickListener {
            showToast(requireContext(), "Close is Clicked")
        }
    }
}
