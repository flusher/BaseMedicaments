package com.ambika.basemedicaments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class WebViewActivity extends Activity {

	private WebView webView;
	private ProgressDialog progressDialog;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		/*setContentView(R.layout.webview);

		webView = (WebView) findViewById(R.id.webView1);*/

		// Create WebView with transparent background
		webView = new WebView(this);
		webView.setBackgroundColor(0x00000000);
		setContentView(webView);

		// Our activity
		final Activity activity = this;

		// Set Javascript
		webView.getSettings().setJavaScriptEnabled(true);
		
		// Set scrollbar style
		webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
		
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        //progressDialog.setMax(100);
        progressDialog.setMessage("Veuillez patienter...");
        progressDialog.show();

        // Custom WebView
        webView.setWebViewClient(new WebViewClient() {

			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// Load inside WebView official pages only
				if (Uri.parse(url).getHost()
						.endsWith("m.base-donnees-publique.medicaments.gouv.fr")) {
					return false;
				}

				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
				view.getContext().startActivity(intent);

				return true;
			}
			
            public void onLoadResource (WebView view, String url) {
                if (progressDialog == null) {
                    progressDialog.show();
                }
            }
            public void onPageFinished(WebView view, String url) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }

        });

		// Load URL
		webView.loadUrl("http://m.base-donnees-publique.medicaments.gouv.fr/");

	}
}