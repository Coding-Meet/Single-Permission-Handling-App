package com.example.singlepermissionhandlingapp

import android.Manifest
import android.content.DialogInterface
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat

class MainActivity : AppCompatActivity() {

    private val permissionRequestId = 1
    private val permissionName = Manifest.permission.READ_CONTACTS
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val singlePermissionBtn = findViewById<Button>(R.id.singlePermissionBtn)
        singlePermissionBtn.setOnClickListener {
            if (checkSinglePermissionAny(
                    this,
                    permissionName,
                    permissionRequestId
            )){
                doOperation()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == permissionRequestId){
            if (grantResults.isNotEmpty() && grantResults[0]  == PackageManager.PERMISSION_GRANTED){
                doOperation()
            }else{
               if (!ActivityCompat.shouldShowRequestPermissionRationale(
                       this,
                       permissionName
               )){
                   // here app setting open because permission permanent denied
                   appSettingOpen(this)
               }else{
                   // permission Denied
                   // show warning permission dialog
                   warningPermissionDialog(this) { _: DialogInterface? , which:Int ->
                       when(which){
                           DialogInterface.BUTTON_POSITIVE -> {
                               if(checkSinglePermissionAny(
                                       this,
                                       permissionName,
                                       permissionRequestId
                               )){
                                   doOperation()
                               }
                           }
                       }
                   }
               }
            }
        }
    }
    private fun doOperation() {
        Toast.makeText(
            this,
            "Permission Grant Successfully!",
            Toast.LENGTH_LONG
        ).show()
    }
}