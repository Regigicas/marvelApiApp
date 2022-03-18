package com.example.marvelapiapp.view.characterInfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.character.CharacterInfo
import com.example.domain.model.character.CharacterParamInfo
import com.example.domain.model.response.UseCaseResponseStatus
import com.example.marvelapiapp.view.main.MainActivity
import com.example.marvelapiapp.R
import com.example.marvelapiapp.constant.CharactersConstant
import com.example.marvelapiapp.databinding.FragmentCharacterInfoBinding
import com.example.marvelapiapp.view.base.BaseFragment
import com.example.marvelapiapp.view.characterInfo.adapter.CharacterSectionAdapter
import com.example.marvelapiapp.viewmodel.characterInfo.CharacterInfoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacterInfoFragment : BaseFragment<FragmentCharacterInfoBinding>() {
    private val viewModel: CharacterInfoViewModel by viewModels()
    private val adapterList = mutableListOf<CharacterSectionAdapter?>(null, null, null, null)
    private val args: CharacterInfoFragmentArgs by navArgs()

    override fun getViewModel(): ViewModel = viewModel
    override fun getFragmentName(): String = this::class.java.simpleName

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setBinding(FragmentCharacterInfoBinding.inflate(inflater, container, false))
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as? MainActivity)?.setToolbarBackButtonVisible(true)
        initViews()
        if (args.characterInfo != null) {
            handleServiceData(args.characterInfo)
        } else {
            initCollector(args.characterId)
        }
    }

    private fun initCollector(characterId: Int?) {
        characterId?.let { requestedId ->
            viewModel.requestCharacterData(requestedId)
            lifecycleScope.launchWhenStarted {
                viewModel.getCharacterState().collect {
                    when (it.status) {
                        UseCaseResponseStatus.OK -> { handleServiceData(it.data) }
                        UseCaseResponseStatus.ERROR -> { handleServiceError() }
                        else -> {}
                    }
                }
            }
        } ?: run {
            if (viewModel.getCharacterState().value.data == null) {
                findNavController().navigate(
                    CharacterInfoFragmentDirections
                        .actionCharacterInfoFragmentToFullErrorFragment())
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
            setDataForAdapter(AdapterId.COMICS, charData.comics)
            setDataForAdapter(AdapterId.STORIES, charData.stories)
            setDataForAdapter(AdapterId.EVENTS, charData.events)
            setDataForAdapter(AdapterId.SERIES, charData.series)
        }
    }

    private fun handleServiceError() {
        viewModel.notifyLoadFinished()
        findNavController().popBackStack()
        findNavController().navigate(
            CharacterInfoFragmentDirections
                .actionCharacterInfoFragmentToFullErrorFragment())
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

    private fun setDataForAdapter(adapterId: AdapterId, data: List<CharacterParamInfo>?) {
        data?.let {
            adapterList[adapterId.ordinal]?.setData(it)
        }
    }

    private enum class AdapterId {
        COMICS, STORIES, EVENTS, SERIES
    }
}
