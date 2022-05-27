package com.example.nasa_app.ui.chips

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.nasa_app.R
import com.example.nasa_app.util.showToast
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.fragment_chips.*

class ChipsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_chips, container, false)
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