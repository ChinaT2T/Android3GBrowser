package browser.xuewenok.com;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;


public class TestAndroidBrowserActivity extends Activity{
    /** Called when the activity is first created. */
	
	public WebView m_webView = null;
	public EditText m_urlText = null;
	public ProgressBar m_progressBar = null;
	
	final private String m_homeUrl = "http://m.3gcoo.net/index.htm#aa";
	final private String m_queryUrl = "http://m.3gcoo.net/i.jsp?wd=";
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //Button button = (Button)findViewById(R.id.buttonGo);
        
        m_webView = (WebView)findViewById(R.id.webViewMain);
        if (m_webView != null) {
        	m_webView.setWebViewClient(new BrowserClient());
        	m_webView.getSettings().setJavaScriptEnabled(true);
        }
        
        m_urlText = (EditText)findViewById(R.id.editTextLocation);
        m_urlText.setOnEditorActionListener(new OnEditorActionListener() {
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if (actionId ==  EditorInfo.IME_ACTION_NEXT) 
					LoadUrl(m_webView);
				return true;
			}
		});
        
        m_progressBar = (ProgressBar)findViewById(R.id.progressBar);

		// load the home page
        load(m_homeUrl);
    }
    
    private class BrowserClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
        @Override
        public void onLoadResource(WebView view, String url) {
        	m_progressBar.setProgress(0);
        	m_progressBar.setIndeterminate(true);
        	m_progressBar.setVisibility(View.VISIBLE);
        }
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
    		m_webView.requestFocus(View.FOCUS_DOWN);
        	m_progressBar.setProgress(0);
        	m_progressBar.setIndeterminate(true);
        	m_progressBar.setVisibility(View.VISIBLE);
    		InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
    		imm.hideSoftInputFromWindow( TestAndroidBrowserActivity.this.getCurrentFocus().getWindowToken(), 0);
        }
        @Override
        public void onPageFinished(WebView view, String url) {
        	m_progressBar.setProgress(m_progressBar.getMax());
        	m_progressBar.setIndeterminate(false);
        	m_progressBar.setVisibility(View.GONE);
        }
        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        	m_progressBar.setIndeterminate(false);
        	m_progressBar.setProgress(m_progressBar.getMax());
        	m_progressBar.setVisibility(View.GONE);
        }
    }

    // The Go button event on click listener
    public void LoadUrl(View v) {
        // do something when the button is clicked
    	String urlOrKeyword = m_urlText.getText().toString();
    	if ( ! urlOrKeyword.contains(".") )
    		urlOrKeyword = m_queryUrl + urlOrKeyword;
    	else if ( ! urlOrKeyword.contains("http://") )
    		urlOrKeyword = "http://" + urlOrKeyword;
    	load(urlOrKeyword);
      }
    
    // Load
    public void load(String url) {
    	if (m_webView != null) {
    		m_webView.stopLoading();
    		m_webView.loadUrl(url);
    	}    	
    }
    // Backward
    public void backward(View v) {
    	m_webView.goBack();
    }
    // Forward
    public void forward(View v) {
    	m_webView.goForward();
    }
    // Stop
    public void stop(View v) {
    	m_webView.stopLoading();
    }
    // Refresh
    public void reload(View v) {
    	m_webView.reload();
    }
    // Go Home
    public void home(View v) {
    	load(m_homeUrl);
    }
}