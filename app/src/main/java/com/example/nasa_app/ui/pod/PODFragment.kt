package com.example.nasa_app.ui.pod

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.example.nasa_app.R
import com.example.nasa_app.network.api.PODState
import com.example.nasa_app.databinding.FragmentMainBinding
import com.example.nasa_app.models.PictureModel
import com.example.nasa_app.ui.settings.SettingsFragment
import com.example.nasa_app.util.showToast
import com.example.nasa_app.ui.favorites.FavoritesViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.chip.Chip

class PODFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    private val viewModel: PODViewModel by lazy {
        ViewModelProvider(this)[PODViewModel::class.java]
    }

    private val favoritesViewModel: FavoritesViewModel by lazy {
        ViewModelProvider(this)[FavoritesViewModel::class.java]
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBottomSheetBehavior(view.findViewById(R.id.bottom_sheet_container))
        binding.inputLayout.setEndIconOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data =
                    Uri.parse("https://en.wikipedia.org/wiki/${binding.inputEditText.text.toString()}")
            })
        }
        // Temporary removed
        //setBottomAppBar(view)

        binding.chipGroup.setOnCheckedChangeListener { chipGroup, position ->
            // chip position increments when a fragment recreate
            // so to make sure that parameter for viewModel.getData
            // stays in 1..3 range datePosition and offset are used
            val offset: Int = (position - 1) / 3
            val datePosition = position - (offset * 3)

            val chip: Chip? = binding.chipGroup.findViewById(position)
            showToast(requireContext(), "Selected picture from: ${chip?.text}")
            viewModel.setData(datePosition)
        }
        viewModel.getData().observe(viewLifecycleOwner) { renderData(it) }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_bottom_bar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.app_bar_fav -> {
                showToast(requireContext(), "Added to favorites")

                val pictureOfTheDay = favoritesViewModel.getPictureOfTheDay()
                favoritesViewModel.addPictureToFavorites(pictureOfTheDay)
            }
            R.id.app_bar_settings -> activity?.supportFragmentManager?.beginTransaction()
                ?.add(R.id.host_container, SettingsFragment())?.addToBackStack(null)?.commit()
            android.R.id.home -> {
                activity?.let {
                    BottomNavigationDrawerFragment().show(it.supportFragmentManager, "tag")
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun renderData(data: PODState) {
        when (data) {
            is PODState.Success -> {
                val serverResponseData = data.serverResponseData
                val date = serverResponseData.date
                val url = serverResponseData.url
                val title = serverResponseData.title
                val description = serverResponseData.explanation
                val copyright = serverResponseData.copyright
                if (url.isNullOrEmpty() && title.isNullOrEmpty() &&
                    description.isNullOrEmpty()
                ) {
                    //showError("Сообщение, что ссылка пустая")
                    showToast(requireContext(), "Server data is missing")
                    getPodFromDB()
                } else {
                    //showSuccess()
                    binding.imageView.load(url) {
                        lifecycle(this@PODFragment)
                        error(R.drawable.ic_load_error_vector)
                        placeholder(R.drawable.ic_no_photo_vector)
                    }
                    binding.PODTitle.text = title
                    binding.PODDescription.text = description

                    // Save to Picture of the day
                    favoritesViewModel.savePictureAsPOD(
                        PictureModel(
                            id = 0,
                            date = date.toString(),
                            url = url.toString(),
                            title = title.toString(),
                            explanation = description.toString(),
                            copyright = copyright.toString()
                        )
                    )
                }

            }
            is PODState.Loading -> {
                //showLoading()
            }
            is PODState.Error -> {
                //showError(data.error.message)
                data.error.message?.let { showToast(requireContext(), it) }
                getPodFromDB()
            }
        }
    }

    // set data to Picture of The Day from the database
    private fun getPodFromDB() {
        val podData = favoritesViewModel.getPictureOfTheDay()
        binding.imageView.load(podData.url) {
            lifecycle(this@PODFragment)
            error(R.drawable.ic_load_error_vector)
            placeholder(R.drawable.ic_no_photo_vector)

        }
        binding.PODTitle.text = podData.title
        binding.PODDescription.text = podData.explanation
    }

    // Temporary removed but want to save to use later
    /*private fun setBottomAppBar(view: View) {
        val context = activity as MainActivity
        context.setSupportActionBar(view.findViewById(R.id.bottom_app_bar))
        setHasOptionsMenu(true)
        fab.setOnClickListener {
            if (isMain) {
                isMain = false
                binding.bottomAppBar.navigationIcon = null
                binding.bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
                binding.fab.setImageDrawable(
                    ContextCompat.getDrawable(
                        context,
                        R.drawable.ic_back_fab
                    )
                )
                binding.bottomAppBar.replaceMenu(R.menu.menu_bottom_bar_other_screen)
            } else {
                isMain = true
                binding.bottomAppBar.navigationIcon =
                    ContextCompat.getDrawable(context, R.drawable.ic_hamburger_menu_bottom_bar)
                binding.bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
                binding.fab.setImageDrawable(
                    ContextCompat.getDrawable(
                        context,
                        R.drawable.ic_plus_fab
                    )
                )
                binding.bottomAppBar.replaceMenu(R.menu.menu_bottom_bar)
            }
        }
    }*/

    private fun setBottomSheetBehavior(bottomSheet: ConstraintLayout) {
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    private fun Fragment.toast(string: String?) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).apply {
            setGravity(Gravity.BOTTOM, 0, 250)
            show()
        }
    }

    companion object {
        fun newInstance() = PODFragment()
        private var isMain = true
    }
}
