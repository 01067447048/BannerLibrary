package com.jaehyeon.compose.bannerlibrary

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jaehyeon.compose.bannerlibrary.databinding.ItemBannerBinding

/**
 * Created by Jaehyeon on 2022/07/20.
 */
class BannerViewPagerAdapter (
    val onUrlBannerClick: (String) -> Unit,
):  RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private val currentList = arrayListOf<BannerItem>()

    inner class BannerViewHolder(private val binding: ItemBannerBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(banner: BannerItem) {
            if (banner.url.isNotEmpty()) {
                Glide.with(binding.root)
                    .load(banner.url)
                    .into(binding.imgBanner)
            }
            else {
                Glide.with(binding.root)
                    .load(banner.drawable)
                    .into(binding.imgBanner)
            }

            binding.imgBanner.setOnClickListener {
                onUrlBannerClick(banner.hyperLink)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        BannerViewHolder(ItemBannerBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is BannerViewHolder) {
            val index = position % currentList.size
            holder.bind(currentList[index])
        }
    }

    override fun getItemCount(): Int = Int.MAX_VALUE

    @SuppressLint("NotifyDataSetChanged")
    fun updateItem(item: List<BannerItem>) {
        currentList.addAll(item)
        notifyDataSetChanged()
    }

    fun getListCount(): Int = currentList.size
}