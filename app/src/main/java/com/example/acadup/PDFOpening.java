package com.example.acadup;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class PDFOpening extends AppCompatActivity {

    private PDFView pdf;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfopening);
        getSupportActionBar().hide();

        pdf=findViewById(R.id.ViewPdf);
        //pdf.setTag("0");
        progressBar=findViewById(R.id.progressBar2);

        String pdfLink=getIntent().getStringExtra("pdf_link");
        //Toast.makeText(this,pdfLink,Toast.LENGTH_SHORT).show();
        /*WebView webView=findViewById(R.id.webview);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        webView.loadUrl("https://docs.google.com/gview?embedded=true&url="+pdfLink+"");
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view,String url){
                progressBar.setVisibility(View.GONE);

            }
            @Override
            public boolean shouldOverrideUrlLoading(
                    WebView view, String url) {
                return(false);
            }
        });*/
        try {
            final Handler handler=new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    progressBar.setVisibility(View.GONE);
                }
            },10000);

            new RetrivePdfStream().execute(pdfLink);

        }
        catch (Exception e)
        {
            Toast.makeText(PDFOpening.this,"Failed to load Url:"+e.toString(),Toast.LENGTH_SHORT).show();
        }
    }
    class RetrivePdfStream extends AsyncTask<String,Void, InputStream> {

        @Override
        protected InputStream doInBackground(String... strings) {
            InputStream inputStream=null;

            try {
                URL url=new URL(strings[0]);
                HttpURLConnection urlConnection=(HttpURLConnection)url.openConnection();
                if(urlConnection.getResponseCode()==200)
                {
                    inputStream=new BufferedInputStream(urlConnection.getInputStream());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return inputStream;
        }

        @Override
        protected void onPostExecute(InputStream inputStream) {
            pdf.fromStream(inputStream).load();
            //pdf.setTag("1");
        }

    }
}