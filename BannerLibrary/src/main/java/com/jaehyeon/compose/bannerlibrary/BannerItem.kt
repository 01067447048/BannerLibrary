package com.jaehyeon.compose.bannerlibrary

import androidx.annotation.DrawableRes

data class BannerItem(
    val url: String = "",
    @DrawableRes val drawable: Int = 0,
    val hyperLink: String = ""
)
