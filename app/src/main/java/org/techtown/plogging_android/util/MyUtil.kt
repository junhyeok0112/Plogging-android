package org.techtown.plogging_android.util

import android.Manifest
import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.text.SimpleDateFormat
import java.util.*

fun checkPermission(context:Context){
    myCheckPermission(context)
    myCheckCameraPermission(context)
}
fun myCheckPermission(context: Context) {
    if (ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) !== PackageManager.PERMISSION_GRANTED
    ) {
        ActivityCompat.requestPermissions(
            context as Activity,
            arrayOf<String>(Manifest.permission.READ_EXTERNAL_STORAGE),
            20
        )
    }

}

fun myCheckCameraPermission(context:Context){
    if(ContextCompat.checkSelfPermission(
            context,Manifest.permission.CAMERA
        )!== PackageManager.PERMISSION_GRANTED){
        ActivityCompat.requestPermissions(
            context as Activity,
            arrayOf<String>(Manifest.permission.CAMERA),
            0
        )
    }
}

fun dateToString(date: Date): String {
    val format = SimpleDateFormat("yyyy-MM-dd")
    return format.format(date)
}

fun stringToDate(date: String): Date {
    val format = SimpleDateFormat("yyyy-MM-dd")
    return format.parse(date)
}