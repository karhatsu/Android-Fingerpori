package com.karhatsu.fingerpori;

import android.app.Application;
import android.app.ProgressDialog;

public class FingerporiApplication extends Application {

	private static final String FINGERPORI_URL = "http://www.hs.fi/fingerpori";
	private ImageSource imageSource;
	private LoadTask loadTask;
	private FingerporiActivity activity;

	public FingerporiApplication() {
		imageSource = new ImageSource(FINGERPORI_URL);
	}

	public void startLoading(FingerporiActivity fingerporiActivity, ProgressDialog progressDialog, boolean firstLoad) {
		if (loadTask == null) {
			loadTask = new LoadTask(this, progressDialog, firstLoad);
			loadTask.execute();
		}
	}

	public ImageSource getImageSource() {
		return imageSource;
	}

	public void setImageSource(ImageSource imageSource) {
		this.imageSource = imageSource;
	}

	public void afterImageSourceLoaded(String imageUrl) {
		activity.afterImageSourceLoaded(imageUrl);
		loadTask = null;
	}

	public void setActivity(FingerporiActivity activity) {
		this.activity = activity;
	}
}
