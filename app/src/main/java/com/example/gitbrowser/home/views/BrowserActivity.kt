package com.example.gitbrowser.home.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.gitbrowser.R
import com.example.gitbrowser.databinding.ActivityBrowserBinding

class BrowserActivity: AppCompatActivity() {

    private lateinit var activityBrowserBinding: ActivityBrowserBinding

     var url:String? = null
    private var titile:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityBrowserBinding = DataBindingUtil.setContentView(this, R.layout.activity_browser)
        activityBrowserBinding.browserActivity = this

        url = intent.extras?.getString("url")
        titile = intent.extras?.getString("title")

        activityBrowserBinding.title.text = titile
        activityBrowserBinding.browser.loadUrl(url!!)

    }


    fun back()
    {
        finish()
    }
}