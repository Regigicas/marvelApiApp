package com.example.marvelapiapp.view.characterInfo.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.character.CharacterParamInfo
import com.example.marvelapiapp.databinding.ItemListCharacterInfoSectionBinding

class CharacterSectionAdapter(private var items: List<CharacterParamInfo>)
    : RecyclerView.Adapter<CharacterSectionAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemListCharacterInfoSectionBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newData: List<CharacterParamInfo>) {
        items = newData
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemListCharacterInfoSectionBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(info: CharacterParamInfo) {
            binding.paramInfo = info
        }
    }
}
