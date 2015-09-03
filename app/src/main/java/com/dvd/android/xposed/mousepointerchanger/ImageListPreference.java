/**
 * Copyright CMW Mobile.com, 2010. 
 */
package com.dvd.android.xposed.mousepointerchanger;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.preference.ListPreference;
import android.util.AttributeSet;
import android.widget.ListAdapter;


/**
 * The ImageListPreference class responsible for displaying an image for each
 * item within the list.
 * 
 * @author Casper Wakkers
 */
public class ImageListPreference extends ListPreference{
	public int[] resourceIds = null;

	/**
	 * Constructor of the ImageListPreference. Initializes the custom images.
	 * 
	 * @param context
	 *            application context.
	 * @param attrs
	 *            custom xml attributes.
	 */
	public ImageListPreference(Context context, AttributeSet attrs) {
		super(context, attrs);


	}

	/**
	 * {@inheritDoc}
	 */
	protected void onPrepareDialogBuilder(Builder builder) {
		int index = findIndexOfValue(
				getSharedPreferences().getString(getKey(), "1"));

		ListAdapter listAdapter = new ImageArrayAdapter(getContext(),
				R.layout.listitem, getEntries(), resourceIds, index);

		// Order matters.
		builder.setAdapter(listAdapter, this);
		super.onPrepareDialogBuilder(builder);
	}
}
