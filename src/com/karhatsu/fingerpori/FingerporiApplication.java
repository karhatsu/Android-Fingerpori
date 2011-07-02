package com.karhatsu.fingerpori;

import android.app.Application;

public class FingerporiApplication extends Application {

	private static final String FINGERPORI_URL = "http://www.hs.fi/fingerpori";
	private ImageSource imageSource;
	private LoadTask loadTask;

	public FingerporiApplication() {
		imageSource = new ImageSource(FINGERPORI_URL);
	}

	public void startLoading(FingerporiActivity fingerporiActivity) {
		if (loadTask == null) {
			loadTask = new LoadTask(fingerporiActivity);
			loadTask.execute();
		}
	}

	public ImageSource getImageSource() {
		return imageSource;
	}

	public void setImageSource(ImageSource imageSource) {
		this.imageSource = imageSource;
	}

	public void loadingDone() {
		loadTask = null;
	}
}
