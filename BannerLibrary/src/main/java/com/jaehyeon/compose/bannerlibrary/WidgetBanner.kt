package com.jaehyeon.compose.bannerlibrary

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager2.widget.ViewPager2
import com.jaehyeon.compose.bannerlibrary.databinding.WidgetBannerBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Created by Jaehyeon on 2022/07/20.
 */
@SuppressLint("SetTextI18n")
class WidgetBanner @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): ConstraintLayout(context, attrs, defStyleAttr){

    private val binding = WidgetBannerBinding.inflate(LayoutInflater.from(context), this, true)

    private val banners = arrayListOf<BannerItem>()

    var delay = 3000L
        set(value) {
            field = value
            if(isRunning) {
                job.cancel()
            }
            job.start()
        }

    var isRunning = false
        private set

    private var job = CoroutineScope(Dispatchers.Main).launch {
        while (isRunning) {
            delay(delay)
            if (banners.size > 0)
                startBanner(count++)
        }
    }
    private val bannerAdapter = BannerViewPagerAdapter(
        onUrlBannerClick = { url ->
            listener?.let {
               it(url)
            }
        }
    )

    private var count = 0

    var listener: ((String) -> Unit)? = null

    init {
        binding.pagerBanner.apply {
            adapter = bannerAdapter
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    isRunning = true
                    count = position
                }
            })
        }
    }


    fun setBannerItem(banner: List<BannerItem>, type: BannerType) {
        if (banners.size < 1) binding.pagerIndicator.tvCurrentPosition.text = "1"
        banners.addAll(banner)
        bannerAdapter.updateItem(banners.toList())
        binding.pagerIndicator.tvAllCount.text = "/${banners.size}"
    }

    private fun startBanner(_count: Int) {
        binding.pagerBanner.currentItem = _count
        if (bannerAdapter.getListCount() != 0)
            binding.pagerIndicator.tvCurrentPosition.text = (_count % bannerAdapter.getListCount() + 1).toString()
    }

    fun start() {
        job.start()
    }

    fun restartBanner() {
        count = 0
    }

}