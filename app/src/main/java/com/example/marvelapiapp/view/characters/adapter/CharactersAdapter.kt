package com.example.marvelapiapp.view.characters.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.common.characters.CharacterInfo
import com.example.marvelapiapp.databinding.ItemListCharacterBinding
import com.example.marvelapiapp.view.characters.listener.CharacterItemListener

class CharactersAdapter(initialItems: List<CharacterInfo>,
                        private val listener: CharacterItemListener)
    : RecyclerView.Adapter<CharactersAdapter.ViewHolder>() {
    private val items: MutableList<CharacterInfo> = mutableListOf<CharacterInfo>().apply {
        addAll(initialItems)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemListCharacterBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], listener)
    }

    override fun getItemCount(): Int = items.size

    fun getData(): List<CharacterInfo> = items

    @SuppressLint("NotifyDataSetChanged")
    fun appendData(newData: List<CharacterInfo>, reset: Boolean) {
        if (reset) {
            items.clear()
        }
        items.addAll(newData)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemListCharacterBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(characterInfo: CharacterInfo, listener: CharacterItemListener) {
            binding.itemData = characterInfo
            binding.listener = listener
        }
    }
}
