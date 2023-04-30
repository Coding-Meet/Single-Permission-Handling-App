package com.example.singlepermissionhandlingapp

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder

fun checkSinglePermissionAny(
    activity: Activity,
    permissionName : String,
    permissionCode : Int
):Boolean{
    if (ContextCompat.checkSelfPermission(
            activity,
            permissionName
    ) == PackageManager.PERMISSION_DENIED){
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(permissionName),
            permissionCode
        )
    }else{
        return true
    }
    return false
}

fun appSettingOpen(context: Context){
    Toast.makeText(
        context,
        "Go to Settings and Enable Permission",
        Toast.LENGTH_LONG
    ).show()
    val settingIntent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
    settingIntent.data = Uri.parse("package:${context.packageName}")
    context.startActivity(settingIntent)
}

fun warningPermissionDialog(context: Context,listener: DialogInterface.OnClickListener){
    MaterialAlertDialogBuilder(context)
        .setMessage("All Permission are required for this app")
        .setCancelable(false)
        .setPositiveButton("Ok",listener)
        .create()
        .show()
}