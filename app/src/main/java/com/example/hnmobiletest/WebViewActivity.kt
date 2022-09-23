package com.example.hnmobiletest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView

class WebViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val myWebView = WebView(this)
        setContentView(myWebView)

        myWebView.loadUrl(intent.getStringExtra("url").toString())
    }

}