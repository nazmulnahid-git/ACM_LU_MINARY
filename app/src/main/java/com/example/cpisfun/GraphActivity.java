package com.example.cpisfun;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class GraphActivity extends AppCompatActivity {

    private WebView webView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        webView = findViewById(R.id.webView);
        progressBar = findViewById(R.id.progressBar);

        // Configure WebView settings
        webView.getSettings().setJavaScriptEnabled(true); // Enable JavaScript if needed

        // Set a WebViewClient to handle events
        webView.setWebViewClient(new WebViewClient() {

            // Show loader and hide WebView when page starts loading
            @Override
            public void onPageStarted(WebView view, String url, android.graphics.Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressBar.setVisibility(android.view.View.VISIBLE);
                webView.setVisibility(android.view.View.GONE);
            }

            // Hide loader and show WebView when page finishes loading
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar.setVisibility(android.view.View.GONE);
                webView.setVisibility(android.view.View.VISIBLE);
            }
        });

        // Load URL
        webView.loadUrl("https://www.geeksforgeeks.org/graph-data-structure-and-algorithms/");
    }
}
