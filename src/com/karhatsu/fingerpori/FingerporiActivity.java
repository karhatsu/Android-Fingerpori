package com.karhatsu.fingerpori;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

public class FingerporiActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		definePrevButton();
		defineNextButton();
		loadImageAndDefineButtonsStatus();
	}

	private ImageSource getCurrentImageSource() {
		return ((FingerporiApplication) getApplication()).getImageSource();
	}

	private void setCurrentImageSource(ImageSource imageSource) {
		((FingerporiApplication) getApplication()).setImageSource(imageSource);
	}

	private void definePrevButton() {
		Button button = (Button) findViewById(R.id.prevButton);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ImageSource imageSource = getCurrentImageSource();
				if (imageSource.getPrev() != null) {
					setCurrentImageSource(imageSource.getPrev());
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
				ImageSource imageSource = getCurrentImageSource();
				if (imageSource.getNext() != null) {
					setCurrentImageSource(imageSource.getNext());
					loadImageAndDefineButtonsStatus();
				} else {
					showToast("Seuraavaa Fingerporia ei ole saatavilla");
				}
			}
		});
		button.setClickable(false);
		button.setText("");
	}

	private void showToast(String text) {
		Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
	}

	private void loadImageAndDefineButtonsStatus() {
		ProgressDialog progressDialog = null;
		if (!getCurrentImageSource().isLoaded()) {
			progressDialog = showProgressDialog();
		}
		new LoadTask(progressDialog).execute();
	}

	private ProgressDialog showProgressDialog() {
		return ProgressDialog.show(FingerporiActivity.this, "Odota hetki",
				"Ladataan sarjakuvaa...", true);
	}

	private void disableButtons() {
		((Button) findViewById(R.id.prevButton)).setClickable(false);
		((Button) findViewById(R.id.nextButton)).setClickable(false);
	}

	private void disableEnableButtons() {
		Button prevButton = (Button) findViewById(R.id.prevButton);
		Button nextButton = (Button) findViewById(R.id.nextButton);
		ImageSource imageSource = getCurrentImageSource();
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

	private class LoadTask extends AsyncTask<Void, Void, String> {
		private final ProgressDialog progressDialog;

		public LoadTask(ProgressDialog progressDialog) {
			this.progressDialog = progressDialog;
		}

		@Override
		protected String doInBackground(Void... params) {
			disableButtons();
			return getCurrentImageSource().getImageUrl();
		}

		@Override
		protected void onPostExecute(String imageUrl) {
			WebView webView = (WebView) findViewById(R.id.webView);
			webView.loadUrl(imageUrl);
			disableEnableButtons();
			if (progressDialog != null) {
				progressDialog.dismiss();
			}
		}
	}

}