package com.tutorialsbuzz.fcmdemo

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val title = intent?.extras?.get(MyFirebaseMessagingService.notify_title) as String?
        val url = intent?.extras?.get(MyFirebaseMessagingService.notify_url) as String?

        url?.let { webView.loadUrl(it) };

        webView.settings.apply {
            javaScriptEnabled = true
            builtInZoomControls = true
        }

        setWebViewClient()

        subscribe.setOnClickListener({
            startActivity(
                Intent(
                    this@MainActivity,
                    SubscribeNotificationTopicsActivity::class.java
                )
            )
        })
    }

    private fun setWebViewClient() {
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                return false
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                progressBar.visibility = View.VISIBLE
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                progressBar.visibility = View.GONE
            }

        }
    }

    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }
}