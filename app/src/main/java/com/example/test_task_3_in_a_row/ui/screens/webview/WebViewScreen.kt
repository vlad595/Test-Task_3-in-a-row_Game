package com.example.test_task_3_in_a_row.ui.screens.webview

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.FrameLayout
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

@SuppressLint("SetJavaScriptEnabled", "ClickableViewAccessibility")
@Composable
fun WebViewScreen(url: String, onBackToApp: () -> Unit) {
    var webView by remember { mutableStateOf<WebView?>(null) }

    var fullScreenView by remember { mutableStateOf<View?>(null) }
    var fullScreenCallback by remember { mutableStateOf<WebChromeClient.CustomViewCallback?>(null) }

    BackHandler {
        if (fullScreenView != null) {
            fullScreenCallback?.onCustomViewHidden()
        } else if (webView?.canGoBack() == true) {
            webView?.goBack()
        } else {
            onBackToApp()
        }
    }

    Scaffold { innerPadding ->
        AndroidView(
            modifier = Modifier.fillMaxSize().padding(innerPadding),
            factory = { ctx ->
                WebView(ctx).apply {
                    webView = this

                    isFocusable = true
                    isFocusableInTouchMode = true

                    setOnTouchListener { v, event ->
                        when (event.action) {
                            android.view.MotionEvent.ACTION_DOWN,
                            android.view.MotionEvent.ACTION_UP -> {
                                if (!v.hasFocus()) {
                                    v.requestFocus()
                                }
                            }
                        }
                        false
                    }

                    settings.javaScriptEnabled = true

                    webChromeClient = object : WebChromeClient() {
                        override fun onShowCustomView(customView: View?, callback: CustomViewCallback?) {
                            val window = (context as? Activity)?.window ?: return
                            val decorView = window.decorView as? ViewGroup ?: return

                            fullScreenView = customView
                            fullScreenCallback = callback
                            decorView.addView(
                                customView,
                                FrameLayout.LayoutParams(
                                    FrameLayout.LayoutParams.MATCH_PARENT,
                                    FrameLayout.LayoutParams.MATCH_PARENT
                                )
                            )
                        }

                        override fun onHideCustomView() {
                            val window = (context as? Activity)?.window ?: return
                            val decorView = window.decorView as? ViewGroup ?: return
                            fullScreenView?.let { decorView.removeView(it) }
                            fullScreenView = null

                            fullScreenCallback?.onCustomViewHidden()
                            fullScreenCallback = null
                        }
                    }
                    webViewClient = object : WebViewClient() {
                        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                            val newUrl = request?.url?.toString() ?: return false

                            if (newUrl.startsWith("http://") || newUrl.startsWith("https://")) {
                                if (newUrl.contains("t.me")) {
                                    return try {
                                        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(newUrl)))
                                        true
                                    } catch (e: ActivityNotFoundException) {
                                        false
                                    }
                                }
                                return false
                            }
                            return try {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(newUrl))
                                context.startActivity(intent)
                                true
                            } catch (e: ActivityNotFoundException) {
                                true
                            }
                        }
                    }

                    loadUrl(url)
                }
            }
        )
    }
}