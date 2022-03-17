package com.example.marvelapiapp.view.characters

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.common.characters.CharacterInfo
import com.example.marvelapiapp.MainActivity
import com.example.marvelapiapp.R
import com.example.marvelapiapp.constant.CharactersConstant
import com.example.marvelapiapp.databinding.FragmentCharactersLayoutBinding
import com.example.marvelapiapp.databinding.canRequestMoreData
import com.example.marvelapiapp.navigation.NavigationManager
import com.example.marvelapiapp.repository.base.ResponseType
import com.example.marvelapiapp.view.base.BaseFragment
import com.example.marvelapiapp.view.characters.adapter.CharactersAdapter
import com.example.marvelapiapp.view.characters.listener.CharacterItemListener
import com.example.marvelapiapp.viewmodel.characters.CharactersViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import java.io.Serializable

@AndroidEntryPoint
class CharactersFragment : BaseFragment(), CharacterItemListener {
    private val viewModel: CharactersViewModel by viewModels()
    private lateinit var binding: FragmentCharactersLayoutBinding
    private lateinit var adapter: CharactersAdapter
    private lateinit var linearManager: LinearLayoutManager
    private var canLoadMoreData = true
    private var hasData = false
    private var linearLayoutData: Parcelable? = null
    private var adapterData: Serializable? = null
    private var adapterDataRestoreFromMemory = false
    private var adapterWasInitialized = false

    override fun getViewModel(): ViewModel = viewModel
    override fun getFragmentName(): String = this::class.java.simpleName

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharactersLayoutBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.errorLayout.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        (activity as? MainActivity)?.setToolbarTitle(
            resources.getString(R.string.title_list_of_characters))
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (!adapterWasInitialized) {
            return
        }

        val linearLayoutSave = linearManager.onSaveInstanceState()
        outState.putParcelable(CharactersConstant.SAVE_CHARACTERS_LAYOUT, linearLayoutSave)

        val adapterSave = adapter.getData()
        outState.putSerializable(CharactersConstant.SAVE_CHARACTERS_DATA,
            adapterSave as Serializable)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        savedInstanceState?.let {
            recoverDataFromSave(it)
        }
        initViews()
    }

    private fun recoverDataFromSave(bundle: Bundle) {
        linearLayoutData = bundle.getParcelable(CharactersConstant.SAVE_CHARACTERS_LAYOUT)
        adapterData = bundle.getSerializable(CharactersConstant.SAVE_CHARACTERS_DATA)
    }

    private fun initViews() {
        initAdapter()
        initCollector()
    }

    private fun initAdapter() {
        adapterWasInitialized = true
        adapter = CharactersAdapter(listOf(), this)
        linearManager = LinearLayoutManager(requireContext())

        if (adapterData != null) {
            (adapterData as? List<*>)?.filterIsInstance<CharacterInfo>()?.let {
                adapterDataRestoreFromMemory = true
                adapter.appendData(it, true)
            }
            adapterData = null
        }

        if (linearLayoutData != null) {
            linearManager.onRestoreInstanceState(linearLayoutData)
            linearLayoutData = null
        }

        binding.charactersRecyclerView.adapter = adapter
        binding.charactersRecyclerView.layoutManager = linearManager
    }

    private fun initInfiniteScroll() {
        binding.charactersRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (canLoadMoreData && recyclerView.canRequestMoreData()) {
                    viewModel.loadMoreCharacters()
                }
            }
        })
    }

    private fun initCollector() {
        if (!adapterDataRestoreFromMemory) {
            viewModel.collectCharacters()
        }

        lifecycleScope.launchWhenStarted {
            viewModel.getCharactersState().collect {
                when (it.type) {
                    ResponseType.OK -> { updateAdapterData(it.data) }
                    ResponseType.ERROR -> { handleServiceError() }
                    else -> {}
                }
            }
        }

        initInfiniteScroll()
    }

    private fun updateAdapterData(data: List<CharacterInfo>?) {
        viewModel.notifyLoadFinished()
        // Skip the initial call when restoring from memory, that data is coming from memory
        if (adapterDataRestoreFromMemory) {
            adapterDataRestoreFromMemory = false
            return
        }

        data?.let {
            if (data.isNotEmpty()) {
                hasData = true
                adapter.appendData(it, viewModel.checkDataResetAndClear())
            } else {
                canLoadMoreData = false
            }
        }
    }

    private fun handleServiceError() {
        viewModel.notifyLoadFinished()
        viewModel.handleServiceError(hasData)
    }

    override fun onCharacterClicked(characterInfo: CharacterInfo) {
        NavigationManager.navigateToCharacterDetail(characterInfo)
    }
}
