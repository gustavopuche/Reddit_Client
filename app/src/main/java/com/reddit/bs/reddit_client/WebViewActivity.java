package com.reddit.bs.reddit_client;

/**
 * Created by Gustavo Puche on 5/08/16.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebView;

public class WebViewActivity extends Activity {

    private WebView webView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webviewlayout);

        webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        Intent intent = getIntent();
        webView.loadUrl(intent.getStringExtra("url"));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        Log.d("WebAct...","<<<<<<Finish>>>>");
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            Log.d("WebAcccct..","Finnnnnnnnnn<<<<<<<<<<<<<<<<");
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

}