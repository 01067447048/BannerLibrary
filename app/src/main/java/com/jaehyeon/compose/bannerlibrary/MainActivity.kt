package com.jaehyeon.compose.bannerlibrary

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.jaehyeon.compose.bannerlibrary.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_main)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arrayListOf<BannerItem>().apply {
            add(BannerItem(
                drawable = R.drawable.banner_red,
                hyperLink = "https://www.google.co.kr/"
            ))
            add(
                BannerItem(
                    drawable = R.drawable.banner_green,
                    hyperLink = "https://www.naver.com/"
                )
            )
            add(
                BannerItem(
                    drawable = R.drawable.banner_blue,
                    hyperLink = "https://github.com/"
                )
            )
        }.also {
            binding.widgetBanner.apply {
                setBannerItem(it, BannerType.RES)
                this.delay = 2000L
                listener = { url ->
                    try {
                        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                    } catch (t: Throwable) {
                        Toast.makeText(this@MainActivity, "Not URL Form", Toast.LENGTH_SHORT).show()
                        Log.e(javaClass.simpleName, "onCreate: ${t.localizedMessage}")
                    }
                }
                start()
            }
        }
    }
}