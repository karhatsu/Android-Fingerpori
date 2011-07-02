package com.karhatsu.fingerpori;

import android.os.AsyncTask;

public class LoadTask extends AsyncTask<Void, Void, String> {
	private final FingerporiActivity fingerporiActivity;

	public LoadTask(FingerporiActivity fingerporiActivity) {
		this.fingerporiActivity = fingerporiActivity;
	}

	@Override
	protected String doInBackground(Void... params) {
		return fingerporiActivity.getCurrentImageSource().getImageUrl();
	}

	@Override
	protected void onPostExecute(String imageUrl) {
		fingerporiActivity.afterImageSourceLoaded(imageUrl);
	}
}
