package com.karhatsu.fingerpori;

import android.app.Application;

public class FingerporiApplication extends Application {

	private static final String FINGERPORI_URL = "http://www.hs.fi/fingerpori";
	private ImageSource imageSource;

	public FingerporiApplication() {
		imageSource = new ImageSource(FINGERPORI_URL);
	}

	public ImageSource getImageSource() {
		return imageSource;
	}

	public void setImageSource(ImageSource imageSource) {
		this.imageSource = imageSource;
	}
}
