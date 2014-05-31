package com.du.codewarriorcontact.settings;

public class SettingData {
	
	String title,overView;

	public SettingData(String title, String overView) {
		
		this.title = title;
		this.overView = overView;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getOverView() {
		return overView;
	}

	public void setOverView(String overView) {
		this.overView = overView;
	}

}
