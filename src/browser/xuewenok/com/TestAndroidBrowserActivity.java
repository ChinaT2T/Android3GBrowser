package browser.xuewenok.com;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class TestAndroidBrowserActivity extends Activity{
    /** Called when the activity is first created. */
	
	public WebView webView = null;
	public EditText urlText = null;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //Button button = (Button)findViewById(R.id.buttonGo);
        
        webView = (WebView)findViewById(R.id.webViewMain);
        if (webView != null) {
        	webView.setWebViewClient(new BrowserClient());
        	webView.getSettings().setJavaScriptEnabled(true);
        }
        
        urlText = (EditText)findViewById(R.id.editTextLocation);
        urlText.setOnEditorActionListener(new OnEditorActionListener() {
			
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER) 
					LoadUrl(null);
				return true;
			}
		});

    }
    
    private class BrowserClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    // The Go button event onclick listener
    public void LoadUrl(View v) {
        // do something when the button is clicked
    	if (webView != null) {
    		webView.loadUrl("http://"+urlText.getText().toString());
    		InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
    		imm.hideSoftInputFromWindow( this.getCurrentFocus().getWindowToken(), 0);
    	}
      }
    // Backward
    public void backward(View v) {
    	webView.goBack();
    }
    // Forward
    public void forward(View v) {
    	webView.goForward();
    }
    // Refresh
    public void reload(View v) {
    	webView.reload();
    }
}