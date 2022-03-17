package com.example.marvelapiapp.view.characterInfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.common.characters.BaseCharacterParamInfo
import com.example.common.characters.CharacterInfo
import com.example.marvelapiapp.MainActivity
import com.example.marvelapiapp.R
import com.example.marvelapiapp.constant.CharactersConstant
import com.example.marvelapiapp.databinding.FragmentCharacterInfoBinding
import com.example.marvelapiapp.navigation.NavigationManager
import com.example.marvelapiapp.repository.base.ResponseType
import com.example.marvelapiapp.view.base.BaseFragment
import com.example.marvelapiapp.view.characterInfo.adapter.CharacterSectionAdapter
import com.example.marvelapiapp.viewmodel.characterInfo.CharacterInfoViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class CharacterInfoFragment : BaseFragment() {
    private val viewModel: CharacterInfoViewModel by viewModels()
    private lateinit var binding: FragmentCharacterInfoBinding
    private val adapterList = mutableListOf<CharacterSectionAdapter?>(null, null, null, null)

    override fun getViewModel(): ViewModel = viewModel
    override fun getFragmentName(): String = this::class.java.simpleName

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharacterInfoBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as? MainActivity)?.setToolbarBackButtonVisible(true)
        initViews()
        val argumentData = arguments?.getSerializable(CharactersConstant.CHARACTER_INFO_KEY) as? CharacterInfo
        if (argumentData != null) {
            handleServiceData(argumentData)
        } else {
            val characterId = arguments?.getInt(CharactersConstant.CHARACTER_ID_KEY)
            initCollector(characterId)
        }
    }

    private fun initCollector(characterId: Int?) {
        characterId?.let { requestedId ->
            viewModel.requestCharacterData(requestedId)
            lifecycleScope.launchWhenStarted {
                viewModel.getCharacterState().collect {
                    when (it.type) {
                        ResponseType.OK -> { handleServiceData(it.data) }
                        ResponseType.ERROR -> { handleServiceError() }
                        else -> {}
                    }
                }
            }
        } ?: run {
            if (viewModel.getCharacterState().value.data == null) {
                NavigationManager.showErrorOverlay()
            }
        }
    }

    private fun handleServiceData(charData: CharacterInfo?) {
        if (charData == null) {
            handleServiceError()
        } else {
            viewModel.notifyLoadFinished()
            (activity as? MainActivity)?.setToolbarTitle(
                resources.getString(R.string.title_character_info, charData.name))
            viewModel.setCharacterData(charData)
            setDataForAdapter(AdapterId.COMICS, charData.comics?.items)
            setDataForAdapter(AdapterId.STORIES, charData.stories?.items)
            setDataForAdapter(AdapterId.EVENTS, charData.events?.items)
            setDataForAdapter(AdapterId.SERIES, charData.series?.items)
        }
    }

    private fun handleServiceError() {
        viewModel.notifyLoadFinished()
        NavigationManager.popBack()
        NavigationManager.showErrorOverlay()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as? MainActivity)?.setToolbarBackButtonVisible(false)
    }

    private fun initViews() {
        initAdapter(binding.comicsCardView.sectionRecyclerView, AdapterId.COMICS)
        initAdapter(binding.storiesCardView.sectionRecyclerView, AdapterId.STORIES)
        initAdapter(binding.eventsCardView.sectionRecyclerView, AdapterId.EVENTS)
        initAdapter(binding.seriesCardView.sectionRecyclerView, AdapterId.SERIES)
    }

    private fun initAdapter(recyclerView: RecyclerView, adapterId: AdapterId) {
        val adapter = CharacterSectionAdapter(listOf())
        adapterList[adapterId.ordinal] = adapter
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun setDataForAdapter(adapterId: AdapterId, data: List<BaseCharacterParamInfo>?) {
        data?.let {
            adapterList[adapterId.ordinal]?.setData(it)
        }
    }

    private enum class AdapterId {
        COMICS, STORIES, EVENTS, SERIES
    }
}
