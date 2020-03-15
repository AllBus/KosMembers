package com.kos.kosmembers.helpers

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build

/**
  * Created by Lenovo-PC on 15.06.14.
  * Вспомогательный класс для получения информации об устройстве
  */
object DeviceHelper {
	// получить версию Аndroid
	def getAndroidVersion: String = "Android "+Build.VERSION.BASE_OS+ " "+ Build.VERSION.RELEASE

	// получить название устройства
	def getDeviceName: String = {
		val manufacturer = Build.MANUFACTURER
		val model = Build.MODEL
		if (model.startsWith(manufacturer)) model.capitalize
		else manufacturer.capitalize + " " + model
	}

	def getApplicationName(application:Context): String ={
		application.getPackageName
	}

	def getApplicationVersion(application:Context): String ={
		try {
			val pInfo = application.getPackageManager.getPackageInfo(application.getPackageName, 0)
			val version = pInfo.versionName
			version
		} catch {
			case e: PackageManager.NameNotFoundException ⇒
				"[unknown]"
		}
	}


}