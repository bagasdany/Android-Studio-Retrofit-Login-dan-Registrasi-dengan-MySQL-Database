package com.example.nayatiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class SpecialRequest extends AppCompatActivity {

    WebView webviewku;
    WebSettings webSettingsku;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_special_request);
        getSupportActionBar().hide();

        webviewku = findViewById(R.id.webview);
        webSettingsku = webviewku.getSettings();
        webSettingsku.setJavaScriptEnabled(true);
        webviewku.setWebViewClient(new WebViewClient());

        webviewku.loadUrl("https://yukcoding.blogspot.com/");
    }

    @Override
    public void onBackPressed() {
        if (webviewku.canGoBack()){
            webviewku.goBack();
        }else{
            super.onBackPressed();
        }
    }
}
