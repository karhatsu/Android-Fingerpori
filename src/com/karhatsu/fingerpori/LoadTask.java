package com.karhatsu.fingerpori;

import android.os.AsyncTask;

public class LoadTask extends AsyncTask<Void, Void, String> {
	private final FingerporiApplication fingerporiApplication;

	public LoadTask(FingerporiApplication fingerporiApplication) {
		this.fingerporiApplication = fingerporiApplication;
	}

	@Override
	protected String doInBackground(Void... params) {
		return fingerporiApplication.getImageSource().getImageUrl();
	}

	@Override
	protected void onPostExecute(String imageUrl) {
		fingerporiApplication.afterImageSourceLoaded(imageUrl);
	}
}
