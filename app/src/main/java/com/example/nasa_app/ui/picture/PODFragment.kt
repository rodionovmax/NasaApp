package com.example.nasa_app.ui.picture

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.widget.TextViewCompat.setTextAppearance
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import coil.load
import com.example.nasa_app.App
import com.example.nasa_app.R
import com.example.nasa_app.databinding.MainFragmentBinding
import com.example.nasa_app.network.models.PODModel
import com.example.nasa_app.repository.LocalRepository
import com.example.nasa_app.repository.LocalRepositoryImpl
import com.example.nasa_app.ui.AppState
import com.example.nasa_app.ui.MainActivity
import com.example.nasa_app.ui.chips.ChipsFragment
import com.example.nasa_app.ui.favorites.FavoritesViewModel
import com.example.nasa_app.ui.settings.SettingsFragment
import com.example.nasa_app.util.showToast
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.android.synthetic.main.main_fragment.chip_group

class PODFragment : Fragment() {
    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    private val localRepository: LocalRepository = LocalRepositoryImpl(App.getInstance())

    private val viewModel: PODViewModel by lazy {
        ViewModelProviders.of(this).get(PODViewModel::class.java)
    }

    private val favoritesViewModel: FavoritesViewModel by lazy {
        ViewModelProviders.of(this).get(FavoritesViewModel::class.java)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBottomSheetBehavior(view.findViewById(R.id.bottom_sheet_container))
        binding.inputLayout.setEndIconOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("https://en.wikipedia.org/wiki/${input_edit_text.text.toString()}")
            })
        }
        setBottomAppBar(view)

        binding.chipGroup.setOnCheckedChangeListener { chipGroup, position ->
            val chip: Chip? = binding.chipGroup.findViewById(position)
            showToast(requireContext(), "Selected picture from: ${chip?.text}")
            viewModel.getData(position).observe(viewLifecycleOwner, Observer<PODData> { renderData(it) })
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_bottom_bar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.app_bar_fav -> {
                showToast(requireContext(), "Added to favorites")
                // TODO: has to be replaced by the method from favoritesViewModel
                val pictureOfTheDay : PODModel = localRepository.getPictureOfTheDay()

                Log.d("pictureOfTheDay", pictureOfTheDay.toString())

                favoritesViewModel.addPictureToFavorites(pictureOfTheDay)
            }
            R.id.app_bar_settings -> activity?.supportFragmentManager?.beginTransaction()
                ?.add(R.id.container, SettingsFragment())?.addToBackStack(null)?.commit()
            android.R.id.home -> {
                activity?.let {
                    BottomNavigationDrawerFragment().show(it.supportFragmentManager, "tag")
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun renderData(data: PODData) {
        when (data) {
            is PODData.Success -> {
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
                        PODModel(
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
            is PODData.Loading -> {
                //showLoading()
            }
            is PODData.Error -> {
                //showError(data.error.message)
                data.error.message?.let { showToast(requireContext(), it) }
            }
        }
    }

    private fun setBottomAppBar(view: View) {
        val context = activity as MainActivity
        context.setSupportActionBar(view.findViewById(R.id.bottom_app_bar))
        setHasOptionsMenu(true)
        fab.setOnClickListener {
            if (isMain) {
                isMain = false
                binding.bottomAppBar.navigationIcon = null
                binding.bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
                binding.fab.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_back_fab))
                binding.bottomAppBar.replaceMenu(R.menu.menu_bottom_bar_other_screen)
            } else {
                isMain = true
                binding.bottomAppBar.navigationIcon =
                    ContextCompat.getDrawable(context, R.drawable.ic_hamburger_menu_bottom_bar)
                binding.bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
                binding.fab.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_plus_fab))
                binding.bottomAppBar.replaceMenu(R.menu.menu_bottom_bar)
            }
        }
    }

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