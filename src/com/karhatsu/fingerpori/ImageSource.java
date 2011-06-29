package com.karhatsu.fingerpori;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class ImageSource {
	private static final String IMAGE_URL_REGEX = "(http://www.hs.fi/kuvat/iso_webkuva/[0-9]*.(gif|jpeg))";
	private static final String PREV_URL_REGEX = "previous.*(http://www.hs.fi/fingerpori/[0-9]+).*>\\s*Edellinen";

	private String fullHtmlUrl;
	private String imageUrl;
	private ImageSource next;
	private ImageSource prev;

	public ImageSource(String htmlUrl) {
		this.fullHtmlUrl = htmlUrl;
	}

	private ImageSource(String htmlUrl, ImageSource next) {
		this.fullHtmlUrl = htmlUrl;
		this.next = next;
	}

	public String getImageUrl() {
		if (imageUrl == null) {
			readImageUrlAndCreatePrevSources();
		}
		return imageUrl;
	}

	private void readImageUrlAndCreatePrevSources() {
		String fullHtml = loadFullHtml(fullHtmlUrl);
		imageUrl = fullHtmlUrl; // fallback to full html page
		if (fullHtml == null) {
			return;
		}
		try {
			imageUrl = parseImageUrl(fullHtml);
		} catch (Exception e) {
		}
		try {
			prev = new ImageSource(parsePrevUrl(fullHtml), this);
		} catch (Exception e) {
		}
	}

	private String loadFullHtml(String fullHtmlUrl) {
		try {
			HttpClient client = new DefaultHttpClient();
			HttpGet request = new HttpGet(fullHtmlUrl);
			HttpResponse response = client.execute(request);
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent()));
			String line = "";
			StringBuilder sb = new StringBuilder();
			while ((line = rd.readLine()) != null) {
				sb.append(line);
			}
			return sb.toString();
		} catch (Exception e) {
			return null;
		}
	}

	private String parseImageUrl(String html) {
		return parseUrl(html, IMAGE_URL_REGEX);
	}

	private String parsePrevUrl(String html) {
		return parseUrl(html, PREV_URL_REGEX);
	}

	private String parseUrl(String html, String regex) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(html);
		matcher.find();
		return matcher.group(1);
	}

	public ImageSource getPrev() {
		return prev;
	}

	public ImageSource getNext() {
		return next;
	}

	public boolean isLoaded() {
		return imageUrl != null;
	}
}
