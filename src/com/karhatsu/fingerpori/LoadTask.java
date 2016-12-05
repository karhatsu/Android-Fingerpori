package com.karhatsu.fingerpori;

import android.app.ProgressDialog;
import android.os.AsyncTask;

public class LoadTask extends AsyncTask<Void, Void, String> {
	private final FingerporiApplication fingerporiApplication;
    private final boolean firstLoad;

    public LoadTask(FingerporiApplication fingerporiApplication, ProgressDialog progressDialog, boolean firstLoad) {
		this.fingerporiApplication = fingerporiApplication;
        this.firstLoad = firstLoad;
		fingerporiApplication.getImageSource()
				.setProgressDialog(progressDialog);
	}

	@Override
	protected String doInBackground(Void... params) {
		return fingerporiApplication.getImageSource().getImageUrl(firstLoad);
	}

	@Override
	protected void onPostExecute(String imageUrl) {
		fingerporiApplication.afterImageSourceLoaded(imageUrl);
	}
}
