package com.dvd.android.xposed.mousepointerchanger;

import android.content.res.XModuleResources;
import android.content.res.XResources;

import de.robv.android.xposed.IXposedHookInitPackageResources;
import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.callbacks.XC_InitPackageResources.InitPackageResourcesParam;

public class XposedMod
		implements IXposedHookZygoteInit, IXposedHookInitPackageResources {

	public static int[] cursors_d = new int[] { R.drawable.windows8_normal,
			R.drawable.windows8_link, R.drawable.windows7_normal,
			R.drawable.windows7_link, R.drawable.ubuntu_normal,
			R.drawable.ubuntu_link, R.drawable.mac_normal,
			R.drawable.mac_link };
	private static String MODULE_PATH = null;

	@Override
	public void initZygote(StartupParam startupParam) throws Throwable {
		MODULE_PATH = startupParam.modulePath;
	}

	@Override
	public void handleInitPackageResources(InitPackageResourcesParam resparam)
			throws Throwable {
		XModuleResources modRes = XModuleResources.createInstance(MODULE_PATH,
				resparam.res);
		XSharedPreferences prefs = new XSharedPreferences(
				getClass().getPackage().getName());
		prefs.makeWorldReadable();

        int cur = Integer.parseInt(prefs.getString("cursor", "0"));

		XResources.setSystemWideReplacement("android", "drawable",
				"pointer_arrow",
				modRes.fwd(cursors_d[cur]));
	}
}