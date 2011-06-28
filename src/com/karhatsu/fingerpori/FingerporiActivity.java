package com.karhatsu.fingerpori;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class FingerporiActivity extends Activity {
	private static final String IMAGE_URL_REGEX = "(http://www.hs.fi/kuvat/iso_webkuva/[0-9]*.gif)";
	private static final String FINGERPORI_URL = "http://www.hs.fi/fingerpori";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		WebView webView = (WebView) findViewById(R.id.webView);
		try {
			StringBuilder sb = loadFullHtml();
			String url = parseImageUrl(sb.toString());
			webView.loadUrl(url);
		} catch (Exception e) {
			webView.loadUrl(FINGERPORI_URL);
		}
	}

	private StringBuilder loadFullHtml() throws IOException,
			ClientProtocolException {
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(FINGERPORI_URL);
		HttpResponse response = client.execute(request);
		BufferedReader rd = new BufferedReader(new InputStreamReader(response
				.getEntity().getContent()));
		String line = "";
		StringBuilder sb = new StringBuilder();
		while ((line = rd.readLine()) != null) {
			sb.append(line);
		}
		return sb;
	}

	private String parseImageUrl(String html) {
		Pattern pattern = Pattern.compile(IMAGE_URL_REGEX);
		Matcher matcher = pattern.matcher(html);
		matcher.find();
		return matcher.group();
	}
}