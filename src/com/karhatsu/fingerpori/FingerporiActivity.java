package com.karhatsu.fingerpori;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

public class FingerporiActivity extends Activity {
	private static final String FINGERPORI_URL = "http://www.hs.fi/fingerpori";
	private ImageSource imageSource = new ImageSource(FINGERPORI_URL);

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		definePrevButton();
		defineNextButton();
		loadImageAndDefineButtonsStatus();
	}

	private void definePrevButton() {
		Button button = (Button) findViewById(R.id.prevButton);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (imageSource.getPrev() != null) {
					imageSource = imageSource.getPrev();
					loadImageAndDefineButtonsStatus();
				} else {
					showToast("Edellistä Fingerporia ei ole saatavilla");
				}
			}

		});
	}

	private void defineNextButton() {
		Button button = (Button) findViewById(R.id.nextButton);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (imageSource.getNext() != null) {
					imageSource = imageSource.getNext();
					loadImageAndDefineButtonsStatus();
				} else {
					showToast("Seuraavaa Fingerporia ei ole saatavilla");
				}
			}

		});
	}

	private void showToast(String text) {
		Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
	}

	private void loadImageAndDefineButtonsStatus() {
		WebView webView = (WebView) findViewById(R.id.webView);
		webView.loadUrl(imageSource.getImageUrl());
		disableButtons();
	}

	private void disableButtons() {
		Button prevButton = (Button) findViewById(R.id.prevButton);
		Button nextButton = (Button) findViewById(R.id.nextButton);
		disableEnableButton(prevButton, imageSource.getPrev() != null,
				getString(R.string.prev_button));
		disableEnableButton(nextButton, imageSource.getNext() != null,
				getString(R.string.next_button));
	}

	private void disableEnableButton(Button button, boolean enable, String text) {
		button.setClickable(enable);
		if (enable) {
			button.setText(text);
		} else {
			button.setText("");
		}
	}

}