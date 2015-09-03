package com.dvd.android.xposed.mousepointerchanger;

import android.content.ComponentName;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.widget.Toast;

public class MainActivity extends PreferenceActivity
		implements SharedPreferences.OnSharedPreferenceChangeListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.prefs);

		ImageListPreference cursors = (ImageListPreference) findPreference(
				"cursor");
		cursors.setEntries(R.array.pointers);
		cursors.setEntryValues(R.array.pointers_value);
		cursors.resourceIds = XposedMod.cursors_d;
	}

	@Override
	protected void onResume() {
		super.onResume();
		getPreferenceScreen().getSharedPreferences()
				.registerOnSharedPreferenceChangeListener(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		getPreferenceScreen().getSharedPreferences()
				.unregisterOnSharedPreferenceChangeListener(this);
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {

		if (key.equals("hide_launcher")) {
			int mode = sharedPreferences.getBoolean(key, false)
					? PackageManager.COMPONENT_ENABLED_STATE_DISABLED
					: PackageManager.COMPONENT_ENABLED_STATE_ENABLED;
			getPackageManager().setComponentEnabledSetting(
					new ComponentName(this,
							getClass().getCanonicalName() + "Alias"),
					mode, PackageManager.DONT_KILL_APP);
		} else {
			Toast.makeText(MainActivity.this, "Please reboot to change cursor",
					Toast.LENGTH_SHORT).show();
		}
	}
}
